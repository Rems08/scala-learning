package wator

import scalafx.scene.paint.Color

sealed trait Species {
  def coordinates: Coordinates
  def age: Int
  def color: Color
  def breed: Int
  def canReproduce: Boolean = age >= breed
}

case class Tuna(
    coordinates: Coordinates,
    age: Int = 0,
    breed: Int = 3
) extends Species {
  val color: Color = Color.Blue
}

case class Shark(
    coordinates: Coordinates,
    age: Int = 0,
    energy: Int = 3,
    breed: Int = 5
) extends Species {
  val color: Color = Color.Red
  def isDead: Boolean = energy <= 0
  def getEnergyAtSpawn: Int = energy
}

// une cellule qui peut contenir une espèce ou être vide
case class WatorCell(coordinates: Coordinates, species: Option[Species] = None) {
  def isEmpty: Boolean = species.isEmpty
  def hasTuna: Boolean = species.exists(_.isInstanceOf[Tuna])
  def hasShark: Boolean = species.exists(_.isInstanceOf[Shark])
  def color: Color = species.map(_.color).getOrElse(Color.LightBlue)
}
