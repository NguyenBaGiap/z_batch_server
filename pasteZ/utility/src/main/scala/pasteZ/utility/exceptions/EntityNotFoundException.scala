package pasteZ.utility.exceptions

class EntityNotFoundException(message: String, cause: Option[Throwable] = None)
  extends BaseException(message, cause)
