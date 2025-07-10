package particle

import scalafx.scene.paint.Color

case class ParticleSpawner() {

    def spawnParticles(numberOfParticles: Int, screenWidth: Int, screenHeight: Int): List[Particle] = {
        (1 to numberOfParticles).toList.map { _ =>
            Particle(
                direction = Direction.random(),
                coordinates = Coordinates(scala.util.Random.nextInt(screenWidth), scala.util.Random.nextInt(screenHeight)),
                color = randomColor()
            )
        }
    }
    private def randomColor(): Color = {
        Color.rgb(
            scala.util.Random.nextInt(256),
            scala.util.Random.nextInt(256),
            scala.util.Random.nextInt(256)
        )
    }

    def moveParticles(particles: List[Particle], screenWidth: Int, screenHeight: Int): List[Particle] = {
        particles.map { particle =>
            val speed = 2 // Vitesse de déplacement
            val newCoordinates = particle.direction match {
                case Direction.UP         => particle.coordinates.copy(y = particle.coordinates.y - speed)
                case Direction.DOWN       => particle.coordinates.copy(y = particle.coordinates.y + speed)
                case Direction.LEFT       => particle.coordinates.copy(x = particle.coordinates.x - speed)
                case Direction.RIGHT      => particle.coordinates.copy(x = particle.coordinates.x + speed)
                case Direction.UP_LEFT    => particle.coordinates.copy(x = particle.coordinates.x - speed, y = particle.coordinates.y - speed)
                case Direction.UP_RIGHT   => particle.coordinates.copy(x = particle.coordinates.x + speed, y = particle.coordinates.y - speed)
                case Direction.DOWN_LEFT  => particle.coordinates.copy(x = particle.coordinates.x - speed, y = particle.coordinates.y + speed)
                case Direction.DOWN_RIGHT => particle.coordinates.copy(x = particle.coordinates.x + speed, y = particle.coordinates.y + speed)
            }
            
            // Gestion des collisions avec les bords
            val bounceDirection = checkBoundaryCollision(newCoordinates, particle.direction, screenWidth, screenHeight)
            val finalCoordinates = constrainToScreen(newCoordinates, screenWidth, screenHeight)
            
            particle.copy(coordinates = finalCoordinates, direction = bounceDirection)
        }
    }
    
    private def checkBoundaryCollision(coordinates: Coordinates, direction: Direction, screenWidth: Int, screenHeight: Int): Direction = {
        val margin = 10 // Marge pour les particules
        
        (coordinates.x <= margin, coordinates.x >= screenWidth - margin, coordinates.y <= margin, coordinates.y >= screenHeight - margin) match {
            case (true, _, true, _)    => Direction.DOWN_RIGHT  // Coin haut-gauche
            case (true, _, _, true)    => Direction.UP_RIGHT    // Coin bas-gauche
            case (_, true, true, _)    => Direction.DOWN_LEFT   // Coin haut-droit
            case (_, true, _, true)    => Direction.UP_LEFT     // Coin bas-droit
            case (true, _, _, _)       => bounceHorizontally(direction) // Bord gauche
            case (_, true, _, _)       => bounceHorizontally(direction) // Bord droit
            case (_, _, true, _)       => bounceVertically(direction)   // Bord haut
            case (_, _, _, true)       => bounceVertically(direction)   // Bord bas
            case _                     => direction // Pas de collision
        }
    }
    
    private def bounceHorizontally(direction: Direction): Direction = direction match {
        case Direction.LEFT       => Direction.RIGHT
        case Direction.RIGHT      => Direction.LEFT
        case Direction.UP_LEFT    => Direction.UP_RIGHT
        case Direction.UP_RIGHT   => Direction.UP_LEFT
        case Direction.DOWN_LEFT  => Direction.DOWN_RIGHT
        case Direction.DOWN_RIGHT => Direction.DOWN_LEFT
        case other                => other
    }
    
    private def bounceVertically(direction: Direction): Direction = direction match {
        case Direction.UP         => Direction.DOWN
        case Direction.DOWN       => Direction.UP
        case Direction.UP_LEFT    => Direction.DOWN_LEFT
        case Direction.UP_RIGHT   => Direction.DOWN_RIGHT
        case Direction.DOWN_LEFT  => Direction.UP_LEFT
        case Direction.DOWN_RIGHT => Direction.UP_RIGHT
        case other                => other
    }
    
    private def constrainToScreen(coordinates: Coordinates, screenWidth: Int, screenHeight: Int): Coordinates = {
        val margin = 10
        Coordinates(
            x = math.max(margin, math.min(coordinates.x, screenWidth - margin)),
            y = math.max(margin, math.min(coordinates.y, screenHeight - margin))
        )
    }
}
