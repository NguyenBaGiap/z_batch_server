package modules

import com.google.inject.AbstractModule
import com.typesafe.config.{ Config, ConfigFactory }
import pasteZ.application.batch.syncGoogleSheet.SyncGoogleSheetService
import pasteZ.domain.configMedia.ConfigMediaRepository
import pasteZ.port.secondary.databaseMySql.rdbmsAdapter.common.IOContextManagerOnJDBC
import pasteZ.port.secondary.databaseMySql.rdbmsAdapter.configMedia.ConfigMediaRepositoryOnMySQLImpl
import pasteZ.utility.repository.IOContextManager

class BatchAllModule extends AbstractModule {
  lazy val config: Config = ConfigFactory.load()

  lazy val ioContextManager: IOContextManagerOnJDBC = new IOContextManagerOnJDBC

  lazy val configMediaRepository: ConfigMediaRepository = new ConfigMediaRepositoryOnMySQLImpl()

  lazy val syncGoogleSheetService: SyncGoogleSheetService = new SyncGoogleSheetService(
    ioContextManager,
    configMediaRepository
  )

  override def configure(): Unit = {
    bind(classOf[Config]).toInstance(config)
    bind(classOf[IOContextManager]).toInstance(ioContextManager)
    bind(classOf[ConfigMediaRepository]).toInstance(configMediaRepository)
  }
}

object BatchAllModule extends BatchAllModule