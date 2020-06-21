package pasteZ.utility.repository

trait IOContextManager {
  def context: IOContext

  /**
   * Execute the received block in the context with transaction
   * usage:
   * ioContextManager.transactionalContext { implicit ctx =>
   *   doSomethingWithContext
   * }
   */
  def transactionalContext[T](execution: (IOContext) => T): T
}

trait ReadOnlyIOContextManager {
  def context: ReadOnlyIOContext

  /**
   * Execute the received block in the context with transaction
   * usage:
   * ioContextManager.transactionalContext { implicit ctx =>
   *   doSomethingWithContext
   * }
   */
  def transactionalContext[T](execution: (ReadOnlyIOContext) => T): T
}

