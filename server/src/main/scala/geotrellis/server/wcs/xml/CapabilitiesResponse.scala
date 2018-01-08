package geotrellis.server.wcs.xml

import scala.xml._


object CapabilitiesResponse {
  def for110(url: String) =
    <wcs:Capabilities xmlns:wcs="http://www.opengis.net/wcs/1.1.0" xmlns:gml="http://www.opengis.net/gml" xmlns:ogc="http://www.opengis.net/ogc" xmlns:ows="http://www.opengis.net/ows/1.1" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1.0">
      <ows:ServiceIdentification>
        <ows:Title>GeoTrellis Web Coverage Service</ows:Title>
        <ows:ServiceType>OGC WCS</ows:ServiceType>
        <ows:ServiceTypeVersion>1.1.0</ows:ServiceTypeVersion>
      </ows:ServiceIdentification>
      <ows:OperationsMetadata>
        <ows:Operation name="GetCapabilities">
          <ows:DCP>
            <ows:HTTP>
              <ows:Get xlink:href="https://mrgeo.geointservices.io:443/mrgeo/wcs?"/>
              <ows:Post xlink:href="https://mrgeo.geointservices.io:443/mrgeo/wcs?"/>
            </ows:HTTP>
          </ows:DCP>
        </ows:Operation>
        <ows:Operation name="DescribeCoverage">
          <ows:DCP>
            <ows:HTTP>
              <ows:Get xlink:href="https://mrgeo.geointservices.io:443/mrgeo/wcs?"/>
              <ows:Post xlink:href="https://mrgeo.geointservices.io:443/mrgeo/wcs?"/>
            </ows:HTTP>
          </ows:DCP>
        </ows:Operation>
        <ows:Operation name="GetCoverage">
          <ows:DCP>
            <ows:HTTP>
              <ows:Get xlink:href="https://mrgeo.geointservices.io:443/mrgeo/wcs?"/>
              <ows:Post xlink:href="https://mrgeo.geointservices.io:443/mrgeo/wcs?"/>
            </ows:HTTP>
          </ows:DCP>
        </ows:Operation>
      </ows:OperationsMetadata>
      <wcs:Contents>
        layers.map({ coverage =>
          <wcs:CoverageSummary>
            <wcs:Identifier>coverage.name</wcs:Identifier>
            <ows:Title>coverage.label</ows:Title>
            <ows:WGS84BoundingBox>
              <ows:LowerCorner>s"${coverage.extent.south} ${coverage.extent.west}"</ows:LowerCorner>
              <ows:UpperCorner>s"${coverage.extent.south} ${coverage.extent.west}"</ows:UpperCorner>
            </ows:WGS84BoundingBox>
          </wcs:CoverageSummary>
        })
      </wcs:Contents>
    </wcs:Capabilities>

  def for100(url: String) =
    <WCS_Capabilities xmlns="http://www.opengis.net/wcs" xmlns:gml="http://www.opengis.net/gml" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="1.0.0" xsi:schemaLocation="http://www.opengis.net/wcs http://schemas.opengeospatial.net/wcs/1.0.0/wcsCapabilities.xsd">
      <Service>
        <name>OGC:WC</name>
        <description>Geotrellis Web Coverage Service</description>
        <label>Geotrellis Web Coverage Service</label>
        <fees>NONE</fees>
        <accessConstraints>NONE</accessConstraints>
      </Service>
      <Capability>
        <Request>
          <GetCapabilities>
            <DCPType>
              <HTTP>
                <Get>
                  <OnlineResource xlink:href="https://mrgeo.geointservices.io:443/mrgeo/wcs?" xlink:type="simple"/>
                </Get>
                <Post>
                  <OnlineResource xlink:href="https://mrgeo.geointservices.io:443/mrgeo/wcs?" xlink:type="simple"/>
                </Post>
              </HTTP>
            </DCPType>
          </GetCapabilities>
          <DescribeCoverage>
            <DCPType>
              <HTTP>
                <Get>
                  <OnlineResource xlink:href="https://mrgeo.geointservices.io:443/mrgeo/wcs?" xlink:type="simple"/>
                </Get>
                <Post>
                  <OnlineResource xlink:href="https://mrgeo.geointservices.io:443/mrgeo/wcs?" xlink:type="simple"/>
                </Post>
              </HTTP>
            </DCPType>
          </DescribeCoverage>
          <GetCoverage>
            <DCPType>
              <HTTP>
                <Get>
                  <OnlineResource xlink:href="https://mrgeo.geointservices.io:443/mrgeo/wcs?" xlink:type="simple"/>
                </Get>
                <Post>
                  <OnlineResource xlink:href="https://mrgeo.geointservices.io:443/mrgeo/wcs?" xlink:type="simple"/>
                </Post>
              </HTTP>
            </DCPType>
          </GetCoverage>
        </Request>
        <Exception>
          <Format>application/vnd.ogc.se_xml</Format>
        </Exception>
      </Capability>
      <ContentMetadata>
        layers.map({ coverage =>
          <CoverageOfferingBrief>
            <name>coverage.name</name>
            <label>coverage.label</label>
            <lonLatEnvelope srsName=s"${coverage.srsName}">
              <gml:pos>s"${coverage.extent.south} ${coverage.extent.west}"</gml:pos>
              <gml:pos>s"${coverage.extent.north} ${coverage.extent.east}"</gml:pos>
            </lonLatEnvelope>
          </CoverageOfferingBrief>
        })
      </ContentMetadata>
    </WCS_Capabilities>
}
