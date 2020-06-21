package port.primary.batch.akkaAdapter.actor

import akka.actor.SupervisorStrategy.Escalate
import akka.actor.{ Actor, OneForOneStrategy, SupervisorStrategy }
import pasteZ.application.batch.syncGoogleSheet.SyncGoogleSheetService
import port.primary.batch.akkaAdapter.task._
import pasteZ.application.logger.Logger
import port.primary.batch.akkaAdapter.messages.Message.SyncGoogleSheetMessage

class MasterActor(
    syncGoogleSheetService: SyncGoogleSheetService
)
  extends Actor {

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(maxNrOfRetries = 3) {
    case t: Throwable =>
      Logger.error("Unexpected Error occurred.", t)
      super.supervisorStrategy.decider.applyOrElse(t, (_: Any) => Escalate)
  }

  override def receive: Receive = {
    case SyncGoogleSheetMessage =>
      SyncGoogleSheetTask("SyncGoogleSheet", syncGoogleSheetService)
        .executeSyncGoogleSheet()

    case _ =>
      Logger.warn(s"Message information is incorrect")
  }
}
