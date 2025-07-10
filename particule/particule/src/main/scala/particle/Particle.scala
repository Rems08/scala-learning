package particle

import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import particle.Coordinates
import particle.Direction

final case class Particle(direction: Direction, coordinates: Coordinates, color: Color) {
}
