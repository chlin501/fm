package fm

import zio._
import zio.console._
import zio.nio.channels._
import zio.nio.core._
import org.rogach.scallop._

import java.net.UnknownHostException

object Client {

  import Logging._
  val log = Logging()

  /**
    * Command line options
    * @param arguments to be collected from command line.
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
    * If the value string starts with '/', then remove it; otherwise remains as is.
    * @param value to be checked
    * @return string object that doesn't have '/' at the beginning of string
    */
  def removeSlash(value: String): String =
    value match {
      case v if v.startsWith("/") => value.substring(1, value.length)
      case _                      => value
    }

  /**
    * The entry point of client when connecting to the remote server
    * @param args contains host and port values
    */
  def main(args: Array[String]): Unit = {
    val options = Options(args)
    val host = options.host()
    val port = options.port()
    def run(): ZIO[Console, Throwable, Any] =
      for {
        _ <- putStrLn("telnet>")
        content <- getStrLn
        task <- client(hostValue = InetAddress.byName(host), port = port)
          .use(
            _.writeChunk(
              Chunk.fromArray(content.replace("\n", "").map(_.toByte).toArray)
            )
          )
          .fork
        _ <- task.join
        _ <- run()
      } yield ()
    val runtime = Runtime.default
    runtime.unsafeRun(
      run.fold(
        failure => log.error(s"Failure message: ${failure.getMessage}"),
        success => log.info(s"Successful result: $success")
      )
    )
  }

  /**
    * With Async socket channel in connecting to the remote server
    * @param hostValue is the remote host
    * @param port of the remote server
    * @return Managed Asynchronous socket channel object
    */
  def client(
      hostValue: IO[UnknownHostException, InetAddress] =
        InetAddress.byName("127.0.0.1"),
      port: Int = 8080
  ): Managed[Throwable, AsynchronousSocketChannel] =
    AsynchronousSocketChannel()
      .mapM { client =>
        for {
          host <- hostValue.map { v => removeSlash(v.toString) }
          address <-
            SocketAddress.inetSocketAddress(hostname = host, port = port)
          _ <- client.connect(address)
        } yield client
      }
}
