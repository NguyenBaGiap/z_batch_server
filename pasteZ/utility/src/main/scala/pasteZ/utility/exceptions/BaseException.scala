package pasteZ.utility.exceptions

class BaseException(val message: String, val cause: Option[Throwable] = None)
  extends Exception(message, cause.orNull) {

  def this(message: String, cause: Throwable) = this(message, Some(cause))
}
