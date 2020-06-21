package pasteZ.utility.models

trait Identifier[+A] extends Serializable {

  def value: A
}

