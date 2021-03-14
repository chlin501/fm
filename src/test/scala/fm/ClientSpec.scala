package fm

import org.scalatest._
import flatspec._
import matchers._

class ClientSpec extends AnyFlatSpec with should.Matchers {
  "Client removeSlash" should "remove the start '/'" in {
    import Client._
    val host1 = removeSlash("/127.0.0.1")
    assertResult("127.0.0.1")(host1)
    val host2 = removeSlash("127.0.0.53")
    assertResult("127.0.0.53")(host2)
  }
}
