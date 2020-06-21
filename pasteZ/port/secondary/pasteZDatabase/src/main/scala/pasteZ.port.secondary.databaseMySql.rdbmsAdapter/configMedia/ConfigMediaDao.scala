package pasteZ.port.secondary.databaseMySql.rdbmsAdapter.configMedia

import scalikejdbc._
import pasteZ.domain.configMedia.models._
import pasteZ.port.secondary.databaseMySql.rdbmsAdapter.CRUDMapperWithId

private[configMedia] class ConfigMediaDao extends CRUDMapperWithId[Long, ConfigMediaFields, ConfigMediaRecord] {
  override lazy val tableName = "config_media"
  override lazy val defaultAlias = createAlias("config_media")
  override lazy val primaryKeyFieldName = "config_media_id"

  override def columnNames: Seq[String] = Seq(
    "config_media_id",
    "media",
    "box_media_folder",
    "box_date_folder",
    "daily_only",
    "addition_requester",
    "setting_status"
  )

  override def toNamedValues(record: ConfigMediaRecord): Seq[(Symbol, Any)] = Seq(
    'config_media_id -> record.configMediaId,
    'media -> record.media,
    'box_media_folder -> record.boxMediaFolder,
    'box_date_folder -> record.boxDateFolder,
    'daily_only -> record.dailyOnly,
    'addition_requester -> record.additionRequester,
    'setting_status -> record.settingStatus

  )

  override def idToRawValue(id: Long): Long = id
  override def rawValueToId(value: Any): Long = value.asInstanceOf[Long]

  override def extract(rs: WrappedResultSet, n: ResultName[ConfigMediaRecord]): ConfigMediaRecord = {
    // To prevent "ambiguous implicit values" error, specify a TypeBinder

    ConfigMediaRecord(
      configMediaId = rs.get(n.configMediaId),
      media = rs.get(n.media),
      boxMediaFolder = rs.get(n.boxMediaFolder),
      boxDateFolder = rs.get(n.boxDateFolder),
      dailyOnly = rs.get(n.dailyOnly),
      additionRequester = rs.get(n.additionRequester),
      settingStatus = rs.get(n.settingStatus)
    )
  }
}

