package pasteZ.utility.repository

import pasteZ.utility.models.Entity

import scala.util.Try

trait ReadOnlyAbstractRepositoryOnDao[E <: Entity[_], R] extends AbstractRepository[E, R] {

  protected val dao: ReadOnlyAbstractDao[R]
  protected def record2Entity(record: R): E
  protected def entity2Record(entity: E): R

  protected def checkInvariant(entity: E): Boolean = true
  protected val CONSTRAINT_VIOLATION_MSG: String = ""
  def getAll()(implicit ctx: ReadOnlyIOContext): Try[Seq[E]] = dao.getAll().map(_.map(record2Entity))
}
