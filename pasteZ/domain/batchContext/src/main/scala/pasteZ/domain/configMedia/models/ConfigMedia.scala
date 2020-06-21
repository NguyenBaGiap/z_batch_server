package pasteZ.domain.configMedia.models

import pasteZ.utility.models.Entity

trait ConfigMedia extends Entity[ConfigMediaId] with ConfigMediaFields

object ConfigMedia {
  def apply(
    identifier:        ConfigMediaId,
    media:             Option[String],
    boxMediaFolder:    Option[String],
    boxDateFolder:     Option[String],
    dailyOnly:         Option[String],
    additionRequester: Option[String],
    settingStatus:     Option[String]
  ): ConfigMediaImpl = ConfigMediaImpl(
    identifier,
    media,
    boxMediaFolder,
    boxDateFolder,
    dailyOnly,
    additionRequester,
    settingStatus
  )
}