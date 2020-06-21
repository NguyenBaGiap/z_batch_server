package pasteZ.utility.exceptions

class OptimisticLockException(message: String, cause: Option[Throwable] = None)
  extends BaseException(message, cause)

