package fm

import org.scalatest._
import flatspec._
import matchers._

class LoggingSpec extends AnyFlatSpec with should.Matchers {
  "Logging message" should "matches" in {
    import Logging._
    val log = Logging()
    val infoExpected = "test info level!"
    val infoResult = log.info(infoExpected)
    assertResult(infoExpected)(infoResult)
    val debugExpected = "test debug level!"
    val debugResult = log.debug(debugExpected)
    assertResult(debugExpected)(debugResult)
    val warnExpected = "test warn level!"
    val warnResult = log.debug(warnExpected)
    assertResult(warnExpected)(warnResult)
    val errorExpected = "test error level!"
    val errorResult = log.debug(errorExpected)
    assertResult(errorExpected)(errorResult)
  }

}
