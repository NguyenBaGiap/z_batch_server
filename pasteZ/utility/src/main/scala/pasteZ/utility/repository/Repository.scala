package pasteZ.utility.repository

import scala.util.Try
import pasteZ.utility.models

trait Repository[Identifier <: models.Identifier[_], Fields <: models.Fields, Entity <: models.Entity[Identifier]]

trait ResolveFeatureRepository[Identifier <: models.Identifier[_], Fields <: models.Fields, Entity <: models.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity] {
  def resolveBy(id: Identifier)(implicit ctx: IOContext = IOContext): Try[Entity]
}

trait ResolveAllFeatureRepository[Identifier <: models.Identifier[_], Fields <: models.Fields, Entity <: models.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity] {
  def resolveAll()(implicit ctx: IOContext = IOContext): Try[Seq[Entity]]
}

trait StoreWithIdFeatureRepository[Identifier <: models.Identifier[_], Fields <: models.Fields, Entity <: models.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity] {
  def store(fields: Fields)(implicit ctx: IOContext = IOContext): Try[Identifier]
}

trait StoreNoIdFeatureRepository[Identifier <: models.Identifier[_], Fields <: models.Fields, Entity <: models.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity] {
  def store(fields: Fields)(implicit ctx: IOContext = IOContext): Try[Unit]
}

trait UpdateFeatureRepository[Identifier <: models.Identifier[_], Fields <: models.Fields, Entity <: models.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity] {
  def update(entity: Entity)(implicit ctx: IOContext = IOContext): Try[Unit]
}

trait DeleteFeatureRepository[Identifier <: models.Identifier[_], Fields <: models.Fields, Entity <: models.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity] {
  def deleteBy(id: Identifier)(implicit ctx: IOContext = IOContext): Try[Unit]
}

trait BasicFeatureRepository[Identifier <: models.Identifier[_], Fields <: models.Fields, Entity <: models.Entity[Identifier]]
  extends Repository[Identifier, Fields, Entity]
  with ResolveAllFeatureRepository[Identifier, Fields, Entity]

trait FeatureWithIdFieldRepository[Identifier <: models.Identifier[_], Fields <: models.Fields, Entity <: models.Entity[Identifier]]
  extends BasicFeatureRepository[Identifier, Fields, Entity]
  with ResolveFeatureRepository[Identifier, Fields, Entity]
  with StoreWithIdFeatureRepository[Identifier, Fields, Entity]
  with UpdateFeatureRepository[Identifier, Fields, Entity]
  with DeleteFeatureRepository[Identifier, Fields, Entity]

trait FeatureNoIdFieldRepository[Identifier <: models.Identifier[_], Fields <: models.Fields, Entity <: models.Entity[Identifier]]
  extends BasicFeatureRepository[Identifier, Fields, Entity]
  with StoreNoIdFeatureRepository[Identifier, Fields, Entity]
