// package geotrellis.server.vlm.gdal

// import geotrellis.server._
// import geotrellis.server.vlm._
// import geotrellis.contrib.vlm.gdal.{GDALBaseRasterSource, GDALRasterSource}
// import geotrellis.raster._
// import geotrellis.raster.io.geotiff.AutoHigherResolution
// import geotrellis.proj4.WebMercator
// import geotrellis.contrib.vlm.TargetRegion
// import geotrellis.raster.resample.NearestNeighbor
// import geotrellis.vector.Extent

// import _root_.io.circe._
// import _root_.io.circe.generic.semiauto._
// import cats.effect._
// import cats.data.{NonEmptyList => NEL}

// import java.net.URI

// case class GDALNode(uri: URI, band: Int, celltype: Option[CellType])

// object GDALNode extends RasterSourceUtils {
//   def getRasterSource(uri: String): GDALBaseRasterSource = GDALRasterSource(uri)

//   implicit val gdalNodeEncoder: Encoder[GDALNode] = deriveEncoder[GDALNode]
//   implicit val gdalNodeDecoder: Decoder[GDALNode] = deriveDecoder[GDALNode]

//   implicit val gdalNodeRasterExtents: HasRasterExtents[GDALNode] = new HasRasterExtents[GDALNode] {
//     def rasterExtents(self: GDALNode)(implicit contextShift: ContextShift[IO]): IO[NEL[RasterExtent]] =
//       getRasterExtents(self.uri.toString)
//   }

//   implicit val gdalNodeTmsReification: TmsReification[GDALNode] = new TmsReification[GDALNode] {
//     def tmsReification(self: GDALNode, buffer: Int)(implicit contextShift: ContextShift[IO]): (Int, Int, Int) => IO[ProjectedRaster[MultibandTile]] = (z: Int, x: Int, y: Int) => {
//       def fetch(xCoord: Int, yCoord: Int) =
//         fetchTile(self.uri.toString, z, xCoord, yCoord, WebMercator)
//           .map(_.tile)
//           .map(_.band(self.band))

//       fetch(x, y).map { tile =>
//         val extent = tmsLevels(z).mapTransform.keyToExtent(x, y)
//         ProjectedRaster(MultibandTile(tile), extent, WebMercator)
//       }
//     }
//   }

//   implicit val gdalNodeExtentReification: ExtentReification[GDALNode] = new ExtentReification[GDALNode] {
//     def extentReification(self: GDALNode)(implicit contextShift: ContextShift[IO]): (Extent, CellSize) => IO[ProjectedRaster[MultibandTile]] = (extent: Extent, cs: CellSize) => {
//       getRasterSource(self.uri.toString)
//         .resample(TargetRegion(RasterExtent(extent, cs)), NearestNeighbor, AutoHigherResolution)
//         .read(extent, self.band :: Nil)
//         .map { ProjectedRaster(_, WebMercator) }
//         .toIO { new Exception(s"No tile avail for RasterExtent: ${RasterExtent(extent, cs)}") }
//     }
//   }
// }