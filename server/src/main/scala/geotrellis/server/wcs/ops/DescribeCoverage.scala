package geotrellis.server.wcs.ops

import geotrellis.server.wcs.params.DescribeCoverageWCSParams

import scala.xml._

object DescribeCoverage {
  def build(params: DescribeCoverageWCSParams): Elem = {
    val elem = <xml></xml>
    params.version match {
      case "1.0.0" => ???

      case "1.1.0" => ???
    }
  }
}

// case class DescribeCoverageWCSParams(version: String, identifiers: Seq[String]) extends WCSParams

//private static void addHttpElement100(Element parent, String requestUrl, Version version)
//{
//  Element http = XmlUtils.createElement(parent, "HTTP");

//  Element get = XmlUtils.createElement(http, "Get");

//  Element onlineResource = XmlUtils.createElement(get, "OnlineResource");
//  onlineResource.setAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");
//  onlineResource.setAttribute("xlink:type", "simple");
//  onlineResource.setAttribute("xlink:href", requestUrl);


//  Element post = XmlUtils.createElement(http, "Post");
//  onlineResource = XmlUtils.createElement(post, "OnlineResource");
//  onlineResource.setAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");
//  onlineResource.setAttribute("xlink:type", "simple");
//  onlineResource.setAttribute("xlink:href", requestUrl);
//}

/*
 * Adds OGC metadata elements to the the parent element
 */
//private static void addHttpElement110(Element parent, String requestUrl, String operation)
//{
//  Element op = XmlUtils.createElement(parent, "ows:Operation");
//  op.setAttribute("name", operation);

//  Element http = XmlUtils.createElement(XmlUtils.createElement(op, "ows:DCP"),
//      "ows:HTTP");
//  Element get = XmlUtils.createElement(http, "ows:Get");
//  get.setAttribute("xlink:href", requestUrl);

//  Element post = XmlUtils.createElement(http, "ows:Post");
//  post.setAttribute("xlink:href", requestUrl);
//}
