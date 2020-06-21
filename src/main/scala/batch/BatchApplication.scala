package batch

import java.io.{ File, FileOutputStream }
import java.lang.management.ManagementFactory

import akka.actor.ActorSystem
import modules.BatchAllModule
import port.primary.batch.akkaAdapter.BatchScheduler

object BatchApplication extends BatchAllModule
  with App {

  protected final val actorSystem: ActorSystem = ActorSystem(
    config.getString("akka.actor-system"),
    config
  )

  private def createPidFile(): Option[File] = {
    val pidFile: Option[File] = System.getProperty("pidfile.path") match {
      case path: String => Option(new File(path).getAbsoluteFile)
      case _            => None
    }
    pidFile.foreach {
      pidFile =>
        ManagementFactory.getRuntimeMXBean.getName.split('@').headOption.foreach {
          pid =>
            val out = new FileOutputStream(pidFile)
            try out.write(pid.getBytes) finally out.close()
        }
    }
    pidFile
  }

  createPidFile()

  val batchScheduler = new BatchScheduler(
    actorSystem,
    syncGoogleSheetService
  )
  batchScheduler.start(args)
}
