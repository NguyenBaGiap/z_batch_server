package pasteZ.utility.repository

import scala.util.Try

trait AbstractDao[T] {

  def getAll()(implicit ctx: IOContext): Try[Seq[T]]
}
