package pasteZ.port.secondary.databaseMySql.rdbmsAdapter

import pasteZ.utility.repository.IOContext
import scalikejdbc.DBSession

case class IOContextOnJDBC(session: DBSession) extends IOContext
