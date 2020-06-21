package pasteZ.domain.configMedia

import pasteZ.domain.configMedia.models._
import pasteZ.utility.repository.{ FeatureWithIdFieldRepository, IOContext }

import scala.util.Try

trait ConfigMediaRepository extends FeatureWithIdFieldRepository[ConfigMediaId, ConfigMediaFields, ConfigMedia] {
  def bulkCreateOrUpdate(fields: Seq[ConfigMediaFields])(implicit ctx: IOContext): Try[Int]
}