package geotrellis.server.wcs.xml

import scala.xml._


object DescribeCoverageResponse {
  def for 110(url: String) =
    <wcs:CoverageDescriptions xmlns:wcs="http://www.opengis.net/wcs/1.1.0" xmlns="http://www.opengis.net/wcs" xmlns:gml="http://www.opengis.net/gml" xmlns:ogc="http://www.opengis.net/ogc" xmlns:ows="http://www.opengis.net/ows/1.11.1.0" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="1.1.0" xsi:schemaLocation="http://www.opengis.net/wcs http://schemas.opengis.net/wcs/1.1.0/describeCoverage.xsd">
      <wcs:CoverageDescription>
        <ows:Title>tobler-30m</ows:Title>
        <ows:Abstract>Layer generated using MrGeo</ows:Abstract>
        <wcs:Identifier>tobler-30m</wcs:Identifier>
        <wcs:Domain>
          <wcs:SpatialDomain>
            <ows:BoundingBox crs="urn:ogc:def:crs:OGC:1.3:CRS84" dimensions="2">
              <ows:LowerCorner>-180.00000000 -56.07421875</ows:LowerCorner>
              <ows:UpperCorner>180.00000000 60.02929688</ows:UpperCorner>
            </ows:BoundingBox>
            <ows:BoundingBox crs="urn:ogc:def:crs:EPSG::4326" dimensions="2">
              <ows:LowerCorner>-56.07421875 -180.00000000</ows:LowerCorner>
              <ows:UpperCorner>60.02929688 180.00000000</ows:UpperCorner>
            </ows:BoundingBox>
            <wcs:GridCRS>
              <wcs:GridBaseCRS>urn:ogc:def:crs:EPSG::4326</wcs:GridBaseCRS>
              <wcs:GridType>urn:ogc:def:method:WCS:1.1:2dGridIn2dCrs</wcs:GridType>
              <wcs:GridOrigin>-180.00000000 -56.07421875</wcs:GridOrigin>
              <wcs:GridCS>urn:ogc:def:cs:OGC:0.0:Grid2dSquareCS</wcs:GridCS>
              <wcs:GridOffsets>0.00017166 0.0 0.0 -0.00017166</wcs:GridOffsets>
            </wcs:GridCRS>
          </wcs:SpatialDomain>
        </wcs:Domain>
        <wcs:Range>
          <wcs:Field>
            <wcs:Identifier>contents</wcs:Identifier>
            <wcs:Definition>
              <wcs:AnyValue/>
            </wcs:Definition>
            <wcs:InterpolationMethods>
              <wcs:Default>linear</wcs:Default>
            </wcs:InterpolationMethods>
          </wcs:Field>
        </wcs:Range>
        <wcs:SupportedCRS>urn:ogc:def:crs:EPSG::4326</wcs:SupportedCRS>
        <wcs:SupportedCRS>EPSG:4326</wcs:SupportedCRS>
        <wcs:SupportedFormat>image/geotif</wcs:SupportedFormat>
        <wcs:SupportedFormat>image/geotiff</wcs:SupportedFormat>
        <wcs:SupportedFormat>image/jpeg</wcs:SupportedFormat>
        <wcs:SupportedFormat>image/jpg</wcs:SupportedFormat>
        <wcs:SupportedFormat>image/png</wcs:SupportedFormat>
        <wcs:SupportedFormat>image/tif</wcs:SupportedFormat>
        <wcs:SupportedFormat>image/tiff</wcs:SupportedFormat>
      </wcs:CoverageDescription>
    </wcs:CoverageDescriptions>
}
