package geotrellis.server

package object vlm extends VlmRasterSourceInstances {
  private[geotrellis] val tmsLevels: Array[LayoutDefinition] = {
    val scheme = ZoomedLayoutScheme(WebMercator, 256)
    for (zoom <- 0 to 64) yield scheme.levelForZoom(zoom).layout
  }.toArray
}

