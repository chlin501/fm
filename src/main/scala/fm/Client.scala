package fm

import zio._
import zio.console._
import zio.nio.channels._
import zio.nio.core._

import java.net.UnknownHostException

object Client {

  def removeSlash(value: String): String =
    value.substring(1, value.length)

  def main(args: Array[String]): Unit = {
    val runtime = Runtime.default
    val result = for {
      _ <- putStrLn("telnet>")
      content <- getStrLn
      task <- client()
        .use(_.writeChunk(Chunk.fromArray(content.map(_.toByte).toArray)))
        .fork
      _ <- task.join
    } yield ()
    runtime.unsafeRun(result)
  }
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
