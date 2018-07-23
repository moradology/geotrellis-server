package geotrellis.server.example.overlay

import geotrellis.server.example.Config
import geotrellis.server.core.maml._

import com.azavea.maml.ast.Expression
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap
import cats.data._
import cats.effect._
import io.circe._
import io.circe.syntax._
import fs2._
import fs2.StreamApp.ExitCode
import org.http4s.circe._
import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.client.blaze.Http1Client
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.{AuthMiddleware, HttpMiddleware}
import org.http4s.server.middleware.{GZip, CORS, CORSConfig}
import org.http4s.headers.{Location, `Content-Type`}
import org.http4s.client.Client
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.collection.mutable
import java.util.UUID


object OverlayServer extends StreamApp[IO] with LazyLogging with Http4sDsl[IO] {

  private val corsConfig = CORSConfig(
    anyOrigin = true,
    anyMethod = false,
    allowedMethods = Some(Set("GET")),
    allowCredentials = true,
    maxAge = 1.day.toSeconds
  )

  private val commonMiddleware: HttpMiddleware[IO] = { (routes: HttpService[IO]) =>
    CORS(routes)
  }

  val onFailure: AuthedService[String, IO] =
    Kleisli(req => OptionT.liftF(Forbidden(req.authInfo)))

  def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, ExitCode] = {
    for {
      config     <- Stream.eval(Config.load())
      _          <- Stream.eval(IO.pure(logger.info(s"Initializing Weighted Overlay at ${config.http.interface}:${config.http.port}/maml/overlay")))
      weightedOverlay = new WeightedOverlayService()
      exitCode   <- BlazeBuilder[IO]
        .enableHttp2(true)
        .bindHttp(config.http.port, config.http.interface)
        .mountService(commonMiddleware(weightedOverlay.routes), "/maml/overlay")
        .serve
    } yield exitCode
  }
}

