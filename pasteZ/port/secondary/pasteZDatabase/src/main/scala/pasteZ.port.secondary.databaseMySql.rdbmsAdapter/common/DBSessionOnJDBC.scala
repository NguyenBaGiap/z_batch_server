package pasteZ.port.secondary.databaseMySql.rdbmsAdapter.common

import pasteZ.application.logger.Logger
import scalikejdbc.DBSession
import pasteZ.utility.repository.IOContext

import scala.util.{ Failure, Success, Try }

trait DBSessionOnJDBC {

  protected def getDBSession(implicit ctx: IOContext): Try[DBSession] = {
    ctx match {
      case IOContextOnJDBC(session) => Success(session)
      case _ =>
        Failure(new IllegalArgumentException(s"$ctx is type miss match. please set to EntityIOContextOnJDBC."))
    }
  }

  protected def withDBSession[T](func: DBSession => T)(implicit ctx: IOContext): Try[T] = {
    getDBSession.map(func)
  }
}
