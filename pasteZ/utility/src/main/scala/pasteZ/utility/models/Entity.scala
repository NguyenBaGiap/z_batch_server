package pasteZ.utility.models

trait Entity[ID <: Identifier[_]]
  extends RecordTimestamp {
  val identifier: ID
}

