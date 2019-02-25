package geotrellis.server.ogc.wms.layer

import geotrellis.server._
import geotrellis.server.ogc.wms._

import geotrellis.contrib.vlm._
import geotrellis.raster._
import geotrellis.raster.reproject._
import geotrellis.vector._
import geotrellis.proj4._
import com.azavea.maml.ast._
import cats.data.{NonEmptyList => NEL}
import cats.effect._
import cats.implicits._

case class SimpleWmsLayer(
  name: String,
  title: String,
  crs: CRS,
  source: RasterSource,
  style: Option[StyleModel]
) extends WmsLayer

object SimpleWmsLayer {
  implicit val mapAlgebraWmsLayerReification = new ExtentReification[SimpleWmsLayer] {
    def kind(self: SimpleWmsLayer): MamlKind = MamlKind.Image
    def extentReification(self: SimpleWmsLayer)(implicit contextShift: ContextShift[IO]): (Extent, CellSize) => IO[Literal] =
      (extent: Extent, cs: CellSize) =>  IO {
        self
          .source
          .reprojectToGrid(self.crs, RasterExtent(extent, cs))
          .read(extent)
          .map(RasterLit(_))
          .get
      }
  }

  implicit val cogNodeRasterExtents: HasRasterExtents[SimpleWmsLayer] = new HasRasterExtents[SimpleWmsLayer] {
    def rasterExtents(self: SimpleWmsLayer)(implicit contextShift: ContextShift[IO]): IO[NEL[RasterExtent]] =
      IO {
        val resolutions = self.source.resolutions.map { ge =>
          ReprojectRasterExtent(ge.toRasterExtent, self.source.crs, self.crs)
        }
        NEL.fromList(resolutions)
          .getOrElse(NEL(ReprojectRasterExtent(self.source.gridExtent.toRasterExtent, self.source.crs, self.crs), Nil))
      }
  }
}
