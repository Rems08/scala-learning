package particle

import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import particle.Coordinates
import particle.Direction

final case class Particle(direction: Direction, coordinates: Coordinates, color: Color) {

    

    def draw(particleRadius: Int): Circle = {
        new Circle {
            centerX = coordinates.x
            centerY = coordinates.y
            radius = particleRadius
            fill = color
        }
    }
}
