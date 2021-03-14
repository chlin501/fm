package fm

import org.slf4j.LoggerFactory
import org.slf4j.{Logger => Slf4jLogger}

trait Logging[T] {

  def info(message: => String): String

  def debug(message: => String): String

  def warn(message: => String): String

  def trace(message: => String): String

  def error(message: => String): String

}
object Logging {

  def apply[T: Logging]() = implicitly[Logging[T]]

  implicit val slf4j = new Logging[Slf4jLogger] {

    protected[fm] val log = LoggerFactory.getLogger(getClass)

    override def info(message: => String): String = {
      if (log.isInfoEnabled) log.info(message)
      message
    }

    override def debug(message: => String): String = {
      if (log.isDebugEnabled) log.debug(message)
      message
    }

    override def warn(message: => String): String = {
      if (log.isWarnEnabled) log.warn(message)
      message
    }

    override def trace(message: => String): String = {
      if (log.isTraceEnabled) log.trace(message)
      message
    }

    override def error(message: => String): String = {
      if (log.isErrorEnabled) log.error(message)
      message
    }

  }

}
