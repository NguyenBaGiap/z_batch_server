package pasteZ.port.secondary.databaseMySql.rdbmsAdapter.configMedia

import pasteZ.domain.configMedia.ConfigMediaRepository
import pasteZ.domain.configMedia.models._
import pasteZ.port.secondary.databaseMySql.rdbmsAdapter.{ BasicWithIdFeatureRepositoryOnJDBC, IOContextOnJDBC }
import pasteZ.utility.repository.IOContext
import scalikejdbc.AutoSession

import scala.util.Try

class ConfigMediaRepositoryOnMySQLImpl(override protected[this] val dao: ConfigMediaDao = new ConfigMediaDao)
  extends ConfigMediaRepository with BasicWithIdFeatureRepositoryOnJDBC[ConfigMediaId, ConfigMediaFields, ConfigMedia, Long, ConfigMediaRecord] {

  override type DAO = ConfigMediaDao

  override protected def convertToEntity(record: ConfigMediaRecord): ConfigMedia = {
    ConfigMedia(
      ConfigMediaId(record.configMediaId),
      record.media,
      record.boxMediaFolder,
      record.boxDateFolder,
      record.dailyOnly,
      record.additionRequester,
      record.settingStatus
    )
  }

  override protected def fieldsFromNamedValues(fields: ConfigMediaFields): Seq[(Symbol, Any)] = Seq(
    'media -> fields.media,
    'box_media_folder -> fields.boxMediaFolder,
    'box_date_folder -> fields.boxDateFolder,
    'daily_only -> fields.dailyOnly,
    'addition_requester -> fields.additionRequester,
    'setting_status -> fields.settingStatus
  )

  override def convertToIdentifier(id: Long): ConfigMediaId = ConfigMediaId(id)

  override protected def convertToRecordId(id: ConfigMediaId): Long = id.value

  protected def toValues(fields: ConfigMediaFields): Seq[Any] = fieldsFromNamedValues(fields).map(v => v._2)

  override def resolveAll()(implicit ctx: IOContext): Try[Seq[ConfigMedia]] = withDBSession { implicit session =>
    dao.findAll(Seq(dao.column.configMediaId)).map(convertToEntity)
  }

  override def bulkCreateOrUpdate(fields: Seq[ConfigMediaFields])(implicit ctx: IOContext = IOContextOnJDBC(AutoSession)): Try[Int] = withDBSession { implicit session =>
    dao.bulkCreateOrUpdate(
      Seq(
        dao.column.media,
        dao.column.boxMediaFolder,
        dao.column.boxDateFolder,
        dao.column.dailyOnly,
        dao.column.additionRequester,
        dao.column.settingStatus
      ),
      fields,
      Seq(
        dao.column.media,
        dao.column.boxMediaFolder,
        dao.column.boxDateFolder,
        dao.column.dailyOnly,
        dao.column.additionRequester,
        dao.column.settingStatus
      ),
      toValues
    )
  }
}

