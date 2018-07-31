package geotrellis.server.core.maml

import geotrellis.server.core.maml.metadata._

import geotrellis.server.core.cog.CogUtils

import com.azavea.maml.ast.{Expression, Literal, MamlKind, RasterLit}
import com.azavea.maml.eval.tile._
import io.circe._
import io.circe.generic.semiauto._
import cats.effect._
import cats.syntax.all._
import geotrellis.raster._
import geotrellis.vector.Extent

import java.net.URI


case class CogNode(uri: URI, band: Int, celltype: Option[CellType])

object CogNode {
  implicit val cellTypeEncoder: Encoder[CellType] = Encoder.encodeString.contramap[CellType](CellType.toName)
  implicit val cellTypeDecoder: Decoder[CellType] = Decoder[String].emap { name => Right(CellType.fromName(name)) }

  implicit val uriEncoder: Encoder[URI] = Encoder.encodeString.contramap[URI](_.toString)
  implicit val uriDecoder: Decoder[URI] = Decoder[String].emap { str => Right(URI.create(str)) }

  implicit val cogNodeEncoder: Encoder[CogNode] = deriveEncoder[CogNode]
  implicit val cogNodeDecoder: Decoder[CogNode] = deriveDecoder[CogNode]

  implicit val cogNodeExtension: MamlExtension[CogNode] = new MamlExtension[CogNode] {
    def extent(self: CogNode)(implicit t: Timer[IO]): IO[Extent] = CogUtils.getTiffExtent(self.uri.toString)
  }

  implicit val cogNodeTmsReification: MamlTmsReification[CogNode] = new MamlTmsReification[CogNode] {
    def kind(self: CogNode): MamlKind = MamlKind.Tile
    def tmsReification(self: CogNode, buffer: Int)(implicit t: Timer[IO]): (Int, Int, Int) => IO[Literal] = (z: Int, x: Int, y: Int) => {
      def fetch(xCoord: Int, yCoord: Int) =
        CogUtils.fetch(self.uri.toString, z, xCoord, yCoord)
        .map(_.tile)
        .map(_.band(self.band))
      (fetch(x - 1, y + 1), fetch(x, y + 1), fetch(x + 1, y + 1),
       fetch(x - 1, y),     fetch(x, y),     fetch(x + 1, y),
       fetch(x - 1, y - 1), fetch(x, y - 1), fetch(x + 1, y - 1)).parMapN { (tl, tm, tr, ml, mm, mr, bl, bm, br) =>
        val tile = TileWithNeighbors(mm, Some(NeighboringTiles(tl, tm, tr, ml, mr,bl, bm, br))).withBuffer(buffer)
        val extent = CogUtils.tmsLevels(z).mapTransform.keyToExtent(x, y)
        RasterLit(Raster(tile, extent))
      }
    }
  }

  implicit val cogNodeExtentReification: MamlExtentReification[CogNode] = new MamlExtentReification[CogNode] {
    def kind(self: CogNode): MamlKind = MamlKind.Tile
    def extentReification(self: CogNode)(implicit t: Timer[IO]): Extent => IO[Literal] = (extent: Extent) => {
      CogUtils.fetch(self.uri.toString, extent)
        .map { _.band(self.band) }
        .map { raster => RasterLit(raster) }
    }
  }

  implicit val cogNodeSummary: SummaryZoom[CogNode] = new SummaryZoom[CogNode] {
    def maxAcceptableCellsize(self: CogNode, maxCells: Int)(implicit t: Timer[IO]): IO[(Extent, CellSize)] =
      CogUtils.fromUri(self.uri.toString).map({ tiff =>
        val colRowCount = math.sqrt(maxCells).toInt
        val allTiffs = tiff.overviews :+ tiff
        val minTiff = allTiffs.filter({ tiff =>
          tiff.cellSize.resolution < CellSize(tiff.extent, colRowCount, colRowCount).resolution
        }).maxBy(_.cellSize.resolution)
        (minTiff.extent, minTiff.cellSize)
      }).recoverWith({
        case _: NoSuchElementException => throw new NoSuchElementException("Unable to retrieve minimum summary extent")
      })
  }
}

