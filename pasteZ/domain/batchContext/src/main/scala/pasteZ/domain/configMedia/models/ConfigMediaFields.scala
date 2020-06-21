package pasteZ.domain.configMedia.models

import pasteZ.utility.models.Fields

trait ConfigMediaFields extends Fields {
  val media: Option[String]
  val boxMediaFolder: Option[String]
  val boxDateFolder: Option[String]
  val dailyOnly: Option[String]
  val additionRequester: Option[String]
  val settingStatus: Option[String]
}