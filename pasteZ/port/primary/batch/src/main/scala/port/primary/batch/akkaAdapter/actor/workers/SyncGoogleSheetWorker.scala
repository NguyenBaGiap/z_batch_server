package port.primary.batch.akkaAdapter.actor.workers

import akka.actor.Actor
import com.google.inject.Inject
import pasteZ.application.batch.syncGoogleSheet.SyncGoogleSheetService
import pasteZ.application.logger.Logger
import port.primary.batch.akkaAdapter.messages.Message.SyncGoogleSheetMessage

class SyncGoogleSheetWorker @Inject() (syncGoogleSheetService: SyncGoogleSheetService) extends Actor {
  def receive: Receive = {
    case SyncGoogleSheetMessage =>
      Logger.info(s"Start synchronizing google sheet")
      syncGoogleSheetService.syncGoogleSheetService().map(_ => {
        Logger.info("Finished synchronizing google sheet")
      }).recover {
        case e => Logger.error(e.getMessage + "\n" + e.getStackTrace.map(_.toString).mkString("\n"))
      }.get
  }
}