package fm

import zio._
import zio.clock._
import zio.console._
import zio.nio.channels._
import zio.nio.core._
import org.rogach.scallop._

import java.io.IOException
import java.net.UnknownHostException

object Server {

  import Logging._
  protected[fm] val log = Logging()

  /**
    * Command line options
    * @param arguments that contains host port values
    */
  protected[fm] case class Options(arguments: Seq[String])
      extends ScallopConf(arguments) {
    val host = opt[String](
      required = true,
      default = Option("127.0.0.1")
    )
    val port = opt[Int](default = Option(8080))
    verify()
  }

  /**
    * Entry point for starting server process
    * @param args contains host port values
    */
  def main(args: Array[String]): Unit = {
    val options = Options(args)
    val host = options.host()
    val port = options.port()
    log.info(s"Starting server @$host:$port ...")
    val runtime = Runtime.default
    runtime.unsafeRun(
      server(hostValue = InetAddress.byName(host), port = port).fold(
        failure => log.error(s"Failure message: ${failure.getMessage}"),
        success => log.info(s"Successful result: $success")
      )
    )
  }

  /**
    * Server process that start asynchronous server socket channel
    * @param hostValue that contains host information
    * @param port value for starting server
    * @return ZIO object that may fail with IOException
    */
  def server(
      hostValue: IO[UnknownHostException, InetAddress] =
        InetAddress.byName("127.0.0.1"),
      port: Int = 8080
  ): ZIO[Console with Clock, IOException, Unit] =
    AsynchronousServerSocketChannel().mapM { socket =>
      for {
        hostname <- hostValue.map(_.hostname)
        _ <- SocketAddress.inetSocketAddress(
          hostname = hostname,
          port = port
        ) >>= socket.bind
        _ <-
          socket.accept.preallocate
            .flatMap(
              _.use(channel =>
                handle(channel).catchAll(ex =>
                  Task(log.error(ex.getMessage)).fork
                )
              ).fork
            )
            .forever
            .fork
      } yield ()
    }.useForever

  /**
    * Handle asynchrous socket channel content
    * @param channel is asynchronous socket channel
    * @return unit encapsulated in ZIO object
    */
  def handle(
      channel: AsynchronousSocketChannel
  ): ZIO[Console with Clock, Throwable, Unit] = {
    val process =
      for {
        chunk <- channel.readChunk(1024)
        str = chunk.map(_.toChar).mkString
        _ <- Task(log.info(s"Received: $str")).fork
      } yield ()

    process.whenM(channel.isOpen).forever
  }
}
