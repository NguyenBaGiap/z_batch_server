package pasteZ.port.secondary.databaseMySql.rdbmsAdapter.common

import scalikejdbc._
import pasteZ.utility.repository.{ IOContext, IOContextManager }
import scalikejdbc.config.DBsWithEnv

class IOContextManagerOnJDBC extends IOContextManager {

  private val database = 'default

  def context: IOContext = IOContextOnJDBC(AutoSession)

  def transactionalContext[T](execution: (IOContext) => T): T = {
    DBsWithEnv("test_mode").setupAll()
    NamedDB(database) localTx { session =>
      execution(IOContextOnJDBC(session))
    }
  }
}
