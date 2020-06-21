package port.primary.batch.akkaAdapter

import java.util.concurrent.TimeUnit

import akka.actor.{ ActorSystem, PoisonPill, Props }
import akka.routing.FromConfig
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import pasteZ.application.batch.syncGoogleSheet.SyncGoogleSheetService
import port.primary.batch.akkaAdapter.actor.MasterActor
import pasteZ.application.logger.Logger
import pasteZ.utility.APIExecutionContextService
import port.primary.batch.akkaAdapter.messages.Message.SyncGoogleSheetMessage

import scala.concurrent.duration.Duration

class BatchScheduler(
    actorSystem:            ActorSystem,
    syncGoogleSheetService: SyncGoogleSheetService

) {

  private[this] val NAME_OF_BATCH_ARG = 0
  private[this] val TIME_FOR_START_MANUAL_BATCH: Long = 1000
  private[this] val TIME_SLEEP: Long = 5000

  private[this] val master = actorSystem.actorOf(
    FromConfig.props(
      Props(
        classOf[MasterActor],
        syncGoogleSheetService
      )
    ),
    "master"
  )

  def start(args: Array[String]): Unit = {
    if (!actorSystem.whenTerminated.isCompleted) {
      if (args.length > 0) {
        executeBatchManual(args)
      } else {
        executeScheduler()
        Logger.info("Batch Scheduler has started.")
      }
    }
  }

  private def shutdown(): Unit = {
    actorSystem.terminate()
    APIExecutionContextService.terminate()
    master ! PoisonPill
    Logger.info("Batch Scheduler has stopped.")
  }

  private def executeBatchManual(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global
    args(NAME_OF_BATCH_ARG) match {
      case "SyncGoogleSheet" =>
        actorSystem.scheduler.scheduleOnce(
          Duration.apply(TIME_FOR_START_MANUAL_BATCH, TimeUnit.MILLISECONDS),
          master,
          SyncGoogleSheetMessage
        )
      case _ => Logger.error("Name of the batch doesn't exist")
    }
    Thread.sleep(TIME_SLEEP)
    shutdown()
    ()
  }

  private def executeScheduler(): Unit = {
    QuartzSchedulerExtension(actorSystem).schedule("SyncGoogleSheet", master, SyncGoogleSheetMessage)
    Logger.info("Running batch scheduler")
    ()
  }
}

