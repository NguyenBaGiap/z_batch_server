package port.primary.batch.akkaAdapter.task

import akka.actor.{ ActorContext, Props }
import pasteZ.application.batch.syncGoogleSheet.SyncGoogleSheetService
import port.primary.batch.akkaAdapter.actor.workers.SyncGoogleSheetWorker
import port.primary.batch.akkaAdapter.messages.Message._

case class SyncGoogleSheetTask(
    name:                   String,
    syncGoogleSheetService: SyncGoogleSheetService) extends MessageTask {

  def executeSyncGoogleSheet()(implicit context: ActorContext): Unit = {
    execute(
      name,
      SyncGoogleSheetMessage,
      context.actorOf(Props(classOf[SyncGoogleSheetWorker], syncGoogleSheetService)))
  }
}