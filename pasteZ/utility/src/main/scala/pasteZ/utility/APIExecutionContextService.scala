package pasteZ.utility

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext
import scala.language.reflectiveCalls

object APIExecutionContextService {

  implicit lazy val actorSystem = ActorSystem("pasteZ-application")

  implicit lazy val actorMaterializer = ActorMaterializer()

  val THREAD_POOL_DEFAULT_SIZE = 5

  val httpExecutionContext = new ExecutionContext {

    val threadPool = Executors.newFixedThreadPool(THREAD_POOL_DEFAULT_SIZE)
    //    override def reportFailure(e: Throwable): Unit = Logger.error(e.getMessage)
    override def reportFailure(e: Throwable): Unit = printf(e.getMessage)
    override def execute(runnable: Runnable): Unit = {
      threadPool.submit(runnable)
      ()
    }
    def shutdown(): Unit = threadPool.shutdown()
  }

  def terminate(): Unit = {
    actorSystem.terminate()
    // In order to avoid warning, wait a moment until async http request finished
    Thread.sleep(3000L)
    httpExecutionContext.shutdown()
  }
}

