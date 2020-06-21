package pasteZ.port.secondary.databaseMySql.rdbmsAdapter

import pasteZ.application.logger.Logger
import scalikejdbc._
import scalikejdbc.jodatime.JodaParameterBinderFactory
import pasteZ.utility.exceptions.{ EntityNotFoundException, OptimisticLockException }
import pasteZ.utility.models.{ Entity => EntityBase, Fields => FieldsBase, Identifier => IdentifierBase, Record => RecordBase }
import pasteZ.utility.repository._

import scala.util.{ Success, Try }

trait BaseRepositoryOnJDBC[Identifier <: IdentifierBase[_], Fields <: FieldsBase, Entity <: EntityBase[Identifier], Record <: RecordBase] extends Repository[Identifier, Fields, Entity] {

  protected def getDBSession(implicit ctx: IOContext): Try[DBSession] = {
    ctx match {
      case IOContextOnJDBC(session) =>
        Success(session)
      case _ =>
        Success(AutoSession)
    }
  }

  protected def withDBSession[T](func: DBSession => T)(implicit ctx: IOContext = IOContext): Try[T] = {
    getDBSession.map(func)
  }
}

trait RepositoryWithIdOnJDBC[Identifier <: IdentifierBase[_], Fields <: FieldsBase, Entity <: EntityBase[Identifier], RecordId, Record <: RecordBase]
  extends BaseRepositoryOnJDBC[Identifier, Fields, Entity, Record] {

  type DAO <: CRUDMapperWithId[RecordId, Fields, Record]

  protected[this] val dao: DAO

  protected def convertToRecordId(identifier: Identifier): RecordId
}

trait ResolveWithIdFeatureRepositoryOnJDBC[Identifier <: IdentifierBase[_], Fields <: FieldsBase, Entity <: EntityBase[Identifier], RecordId, Record <: RecordBase]
  extends RepositoryWithIdOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with ResolveFeatureRepository[Identifier, Fields, Entity] {

  protected def convertToEntity(record: Record): Entity

  def resolveBy(identifier: Identifier)(implicit ctx: IOContext = IOContext): Try[Entity] = withDBSession { implicit session =>
    val entity = dao.findById(convertToRecordId(identifier)).map(convertToEntity)
    entity.getOrElse(throw new EntityNotFoundException(s"model = ${dao.tableName}, id = $identifier"))
  }
}

trait ResolveAllWithIdFeatureRepositoryOnJDBC[Identifier <: IdentifierBase[_], Fields <: FieldsBase, Entity <: EntityBase[Identifier], RecordId, Record <: RecordBase]
  extends RepositoryWithIdOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with ResolveAllFeatureRepository[Identifier, Fields, Entity] {

  protected def convertToEntity(record: Record): Entity

  def resolveAll()(implicit ctx: IOContext = IOContext): Try[Seq[Entity]] = withDBSession { implicit session =>
    dao.findAll().map(convertToEntity)
  }
}

trait StoreWithIdFeatureRepositoryOnJDBC[Identifier <: IdentifierBase[_], Fields <: FieldsBase, Entity <: EntityBase[Identifier], RecordId, Record <: RecordBase]
  extends RepositoryWithIdOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with StoreWithIdFeatureRepository[Identifier, Fields, Entity] {

  protected def fieldsFromNamedValues(fields: Fields): Seq[(Symbol, Any)]

  def convertToIdentifier(id: RecordId): Identifier

  def store(fields: Fields)(implicit ctx: IOContext = IOContext): Try[Identifier] = withDBSession { implicit session =>
    val createAttributes = fieldsFromNamedValues(fields)
    val id = dao.createWithAttributes(createAttributes: _*)
    convertToIdentifier(id)
  }
}

trait UpdateWithIdFeatureRepositoryOnJDBC[Identifier <: IdentifierBase[_], Fields <: FieldsBase, Entity <: EntityBase[Identifier], RecordId, Record <: RecordBase]
  extends RepositoryWithIdOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with UpdateFeatureRepository[Identifier, Fields, Entity] {

  protected def fieldsFromNamedValues(fields: Fields): Seq[(Symbol, Any)]

  override def update(entity: Entity)(implicit ctx: IOContext = IOContext): Try[Unit] = withDBSession { implicit session =>
    val createAttributes = fieldsFromNamedValues(entity.asInstanceOf[Fields])
    implicit val parameterBinderFactory = ParameterBinderFactory.asisParameterBinderFactory
    implicit val updatedAtParameterBinderFactory = JodaParameterBinderFactory.jodaDateTimeParameterBinderFactory

    val updatedCount = dao.updateBy(sqls.eq(dao.column.id, entity.identifier.value.asInstanceOf[Any]))
      .withAttributes(createAttributes: _*)
    if (updatedCount == 1) {
      ()
    } else {
      throw new OptimisticLockException(s"model = ${dao.tableName}, id = $entity.identifier")
    }
  }
}

trait DeleteWithIdFeatureRepositoryOnJDBC[Identifier <: IdentifierBase[_], Fields <: FieldsBase, Entity <: EntityBase[Identifier], RecordId, Record <: RecordBase]
  extends RepositoryWithIdOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with DeleteFeatureRepository[Identifier, Fields, Entity] {

  def deleteBy(identifier: Identifier)(implicit ctx: IOContext = IOContext): Try[Unit] = withDBSession { implicit session =>
    val deletedCount = dao.deleteById(convertToRecordId(identifier))

    if (deletedCount == 1) {
      ()
    } else {
      throw new EntityNotFoundException(s"model = ${dao.tableName}, id = $identifier")
    }
  }
}

trait BasicWithIdFeatureRepositoryOnJDBC[Identifier <: IdentifierBase[_], Fields <: FieldsBase, Entity <: EntityBase[Identifier], RecordId, Record <: RecordBase]
  extends RepositoryWithIdOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with ResolveWithIdFeatureRepositoryOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with ResolveAllWithIdFeatureRepositoryOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with StoreWithIdFeatureRepositoryOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with UpdateWithIdFeatureRepositoryOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with DeleteWithIdFeatureRepositoryOnJDBC[Identifier, Fields, Entity, RecordId, Record]
  with FeatureWithIdFieldRepository[Identifier, Fields, Entity]
