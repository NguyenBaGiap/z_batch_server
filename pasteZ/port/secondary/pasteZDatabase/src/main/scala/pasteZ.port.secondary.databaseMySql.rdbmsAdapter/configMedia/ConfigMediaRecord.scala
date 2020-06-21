package pasteZ.port.secondary.databaseMySql.rdbmsAdapter.configMedia

import pasteZ.utility.models.Record

private[configMedia] case class ConfigMediaRecord(
    configMediaId:     Long,
    media:             Option[String],
    boxMediaFolder:    Option[String],
    boxDateFolder:     Option[String],
    dailyOnly:         Option[String],
    additionRequester: Option[String],
    settingStatus:     Option[String]
) extends Record