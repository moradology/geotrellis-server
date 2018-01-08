package geotrellis.server.wcs

import scala.xml._

package object xml {
  //implicit class XmlMethods(e: Elem) {
  //  def addChild(newChild: Node) =
  //    Elem(e.prefix, e.label, e.attributes, e.scope, e.minimizeEmpty, e.child ++ newChild : _*)

  //  def addAttribute(attr: Node) =
  //    Elem(e.prefix, e.label, e.attributes ++, e.scope, e.minimizeEmpty, e.child)

  //  def addOgcMeta100(requestUrl: String) = {
  //    val http = <HTTP></HTTP>
  //    val get = <Get></Get>
  //    val resources = <OnlineResource></OnlineResource>
  //      .add
  //    .addChild(<HTTP></HTTP>)
  //    .addChild("Get")
  //    .addChild(<OnlineResource xmlns:xlink="http://www.w3.org/1999/xlink" xlink:type="simple" xlink:href={ requestUrl }></OnlineResource>)
  //  }


  //    Element post = XmlUtils.createElement(http, "Post");
  //    onlineResource = XmlUtils.createElement(post, "OnlineResource");
  //    onlineResource.setAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");
  //    onlineResource.setAttribute("xlink:type", "simple");
  //    onlineResource.setAttribute("xlink:href", requestUrl);
  //  }

  //  def addOgcMeta110(requestUrl: String, operation: String) = {
  //    Element op = XmlUtils.createElement(parent, "ows:Operation");
  //    op.setAttribute("name", operation);

  //    Element http = XmlUtils.createElement(XmlUtils.createElement(op, "ows:DCP"),
  //        "ows:HTTP");
  //    Element get = XmlUtils.createElement(http, "ows:Get");
  //    get.setAttribute("xlink:href", requestUrl);

  //    Element post = XmlUtils.createElement(http, "ows:Post");
  //    post.setAttribute("xlink:href", requestUrl);
  //  }
  //}
}
// private static void addHttpElement100(Element parent, String requestUrl, Version version)
// private static void addHttpElement110(Element parent, String requestUrl, String operation)
