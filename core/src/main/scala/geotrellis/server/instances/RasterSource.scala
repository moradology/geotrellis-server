package geotrellis.server
package instances

import geotrellis.raster._
import geotrellis.vector.Extent
import geotrellis.proj4._
import geotrellis.contrib.vlm.RasterSource
import geotrellis.spark.tiling.ZoomedLayoutScheme

import cats.effect._
import cats.data.{NonEmptyList => NEL}
import com.azavea.maml.ast._
import com.azavea.maml._

package object rasterSource extends RasterSourceInstances {
  private[geotrellis] val tmsLevels: Array[LayoutDefinition] = {
    val scheme = ZoomedLayoutScheme(WebMercator, 256)
    for (zoom <- 0 to 64) yield scheme.levelForZoom(zoom).layout
  }.toArray
}

trait RasterSourceInstances {
  implicit val rasterSourceHasRasterExtents: HasRasterExtents[RasterSource] = new HasRasterExtents[RasterSource] {
    def rasterExtents(self: RasterSource)(implicit contextShift: ContextShift[IO]): IO[NEL[RasterExtent]] = {
    }
    def crs(self: RasterSource)(implicit contextShift: ContextShift[IO]): IO[CRS] = ???
  }

  implicit val rasterSourceTmsReification: TmsReification[RasterSource] = new TmsReification[RasterSource] {
    def kind(self: RasterSource): MamlKind = MamlKind.Tile
    def tmsReification(self: RasterSource, buffer: Int)(implicit contextShift: ContextShift[IO]): (Int, Int, Int) => IO[Literal] =
      (z: Int, x: Int, y: Int) => IO {
        val ld = rasterSource.tmsLevels(z)
        val extent = ld.mapKeyTransform.keyToExtent(x, y)
        val ldTileSource = new LayoutTileSource(self, ld).read(SpatialKey(x, y))
        RasterLit(Raster(t, extent))
      }
  }

  implicit val rasterSourceExtentReification: ExtentReification[RasterSource] = new ExtentReification[RasterSource] {
    def kind(self: RasterSource): MamlKind = MamlKind.Tile
    def extentReification(self: RasterSource)(implicit contextShift: ContextShift[IO]): (Extent, CellSize) => IO[Literal] =
      (extent: Extent, cs: CellSize) => IO {
        val t: Tile = ???
        RasterLit(Raster(t, extent))
      }
  }
}

