package port.primary.batch.akkaAdapter.messages

sealed abstract class Message

object Message {
  final case object SyncGoogleSheetMessage extends Message

}
