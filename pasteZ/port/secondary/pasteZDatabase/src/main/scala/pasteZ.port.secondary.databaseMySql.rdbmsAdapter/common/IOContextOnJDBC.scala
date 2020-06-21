package pasteZ.port.secondary.databaseMySql.rdbmsAdapter.common

import scalikejdbc.DBSession
import pasteZ.utility.repository.IOContext

case class IOContextOnJDBC(session: DBSession) extends IOContext
