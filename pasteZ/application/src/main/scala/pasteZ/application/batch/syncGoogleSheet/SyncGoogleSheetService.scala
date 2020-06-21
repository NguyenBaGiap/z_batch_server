package pasteZ.application.batch.syncGoogleSheet

import javax.inject.Inject
import pasteZ.application.logger.Logger
import pasteZ.domain.configMedia.ConfigMediaRepository
import pasteZ.domain.configMedia.models.{ ConfigMedia, ConfigMediaId }
import pasteZ.utility.repository.IOContextManager

import scala.util.Try

class SyncGoogleSheetService @Inject() (
    ioContextManager:      IOContextManager,
    configMediaRepository: ConfigMediaRepository
) {
  def syncGoogleSheetService(): Try[Unit] = Try {
    ioContextManager.transactionalContext {
      implicit ctx =>
        {
          configMediaRepository.store(
            ConfigMedia(
              ConfigMediaId(0L),
              Some("media"),
              Some("boxMediaFolder"),
              Some("boxDateFolder"),
              Some("dailyOnly"),
              Some("additionRequester"),
              Some("settingStatus")
            )
          )
            .map(id => Logger.debug(s"id add = ${id.value}"))
            .recover {
              case exception: Exception => Logger.debug(s"exception = ${exception.getMessage}")
            }
        }
    }

    Logger.debug(">>>>>>>>>>>>>>>>>>>>>> test 11111")
  }
}
