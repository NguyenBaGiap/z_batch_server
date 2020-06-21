package pasteZ.domain.configMedia.models

private[configMedia] case class ConfigMediaImpl(
    identifier:        ConfigMediaId,
    media:             Option[String],
    boxMediaFolder:    Option[String],
    boxDateFolder:     Option[String],
    dailyOnly:         Option[String],
    additionRequester: Option[String],
    settingStatus:     Option[String]
) extends ConfigMedia

