package pasteZ.utility.models

import java.time.ZonedDateTime

trait RecordTimestamp {
  val createdAt: Option[ZonedDateTime] = None
  val updatedAt: Option[ZonedDateTime] = None
}

