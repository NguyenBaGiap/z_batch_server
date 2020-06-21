package pasteZ.utility.repository

import pasteZ.utility.models.{ Entity, Identifier }

import scala.util.Try

trait AbstractRepositoryOnDao[E <: Entity[Identifier[_]], R] extends AbstractRepository[E, R] {

  protected val dao: AbstractDao[R]
  protected def record2Entity(record: R): E
  protected def entity2Record(entity: E): R

  protected def checkInvariant(entity: E): Boolean = true
  protected val CONSTRAINT_VIOLATION_MSG: String = ""
  def getAll()(implicit ctx: IOContext): Try[Seq[E]] = dao.getAll().map(_.map(record2Entity))
}
