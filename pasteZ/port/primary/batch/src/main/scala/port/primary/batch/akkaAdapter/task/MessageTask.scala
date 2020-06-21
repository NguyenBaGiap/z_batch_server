package port.primary.batch.akkaAdapter.task

import akka.actor.{ ActorRef, InvalidActorNameException, PoisonPill }
import port.primary.batch.akkaAdapter.messages.Message
import pasteZ.application.logger.Logger

trait MessageTask {
  def execute(name: String, message: Message, actorOfWorker: ActorRef): Unit = {
    try {
      // Start an actor worker with a message
      actorOfWorker ! message
      // Kill actor worker after finishing
      actorOfWorker ! PoisonPill
    } catch {
      case _: InvalidActorNameException =>
        Logger.warn(s"It has already run $name")
      case e: Exception =>
        Logger.error(s"$name Error:", e)
    }
  }
}
