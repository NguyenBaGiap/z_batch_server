package pasteZ.application.logger

/**
 * Logger
 */
object Logger {

  /**
   * Logger for all logs
   * Output for
   *  - Console
   *  - application.log
   */
  private lazy val logger = org.slf4j.LoggerFactory.getLogger("uploader.logger")

  /**
   * Logger for unexpected error logs
   * Output for
   *  - Console
   *  - application.log
   *  - error.log
   */
  private lazy val unexpectedErrorLogger = org.slf4j.LoggerFactory.getLogger("uploader.unexpectedError")

  /**
   * write debug log
   */
  def debug(msg: => String): Unit = logger.debug(msg)

  def debug(msg: => String, t: => Throwable): Unit = logger.debug(msg, t)

  /**
   * write info log
   */
  def info(msg: => String): Unit = logger.info(msg)

  def info(msg: => String, t: => Throwable): Unit = logger.info(msg, t)

  /**
   * write warn log
   */
  def warn(msg: => String): Unit = logger.warn(msg)

  def warn(msg: => String, t: => Throwable): Unit = logger.warn(msg, t)

  /**
   * write error log
   */
  def error(msg: => String): Unit = unexpectedErrorLogger.error(msg)

  def error(msg: => String, t: => Throwable): Unit = unexpectedErrorLogger.error(msg, t)
}
