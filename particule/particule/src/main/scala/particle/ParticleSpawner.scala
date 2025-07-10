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
        val speed = 2
        val particleRadius = 5
        
        val movedParticles = particles.map(moveParticle(_, speed))
        
        val wallBouncedParticles = movedParticles.map(handleWallCollision(_, screenWidth, screenHeight, particleRadius))
        
        handleParticleCollisions(wallBouncedParticles, particleRadius)
    }

    private def moveParticle(particle: Particle, speed: Int): Particle = {
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
        particle.copy(coordinates = newCoordinates)
    }
    
    private def handleWallCollision(particle: Particle, screenWidth: Int, screenHeight: Int, particleRadius: Int): Particle = {
        val margin = particleRadius
        val coords = particle.coordinates
        
        val hitLeft = coords.x <= margin
        val hitRight = coords.x >= screenWidth - margin
        val hitTop = coords.y <= margin
        val hitBottom = coords.y >= screenHeight - margin
        
        val newDirection = (hitLeft, hitRight, hitTop, hitBottom) match {
            case (true, _, true, _)    => Direction.DOWN_RIGHT  // Coin haut-gauche
            case (true, _, _, true)    => Direction.UP_RIGHT    // Coin bas-gauche
            case (_, true, true, _)    => Direction.DOWN_LEFT   // Coin haut-droit
            case (_, true, _, true)    => Direction.UP_LEFT     // Coin bas-droit
            case (true, _, _, _)       => reflectDirection(particle.direction, horizontal = true)
            case (_, true, _, _)       => reflectDirection(particle.direction, horizontal = true)
            case (_, _, true, _)       => reflectDirection(particle.direction, vertical = true)
            case (_, _, _, true)       => reflectDirection(particle.direction, vertical = true)
            case _                     => particle.direction
        }
        
        val constrainedCoords = Coordinates(
            x = math.max(margin, math.min(coords.x, screenWidth - margin)),
            y = math.max(margin, math.min(coords.y, screenHeight - margin))
        )
        
        particle.copy(coordinates = constrainedCoords, direction = newDirection)
    }
    
    private def reflectDirection(direction: Direction, horizontal: Boolean = false, vertical: Boolean = false): Direction = {
        if (horizontal && vertical) {
            direction match {
                case Direction.UP_LEFT => Direction.DOWN_RIGHT
                case Direction.UP_RIGHT => Direction.DOWN_LEFT
                case Direction.DOWN_LEFT => Direction.UP_RIGHT
                case Direction.DOWN_RIGHT => Direction.UP_LEFT
                case _ => direction
            }
        } else if (horizontal) {
            direction match {
                case Direction.LEFT => Direction.RIGHT
                case Direction.RIGHT => Direction.LEFT
                case Direction.UP_LEFT => Direction.UP_RIGHT
                case Direction.UP_RIGHT => Direction.UP_LEFT
                case Direction.DOWN_LEFT => Direction.DOWN_RIGHT
                case Direction.DOWN_RIGHT => Direction.DOWN_LEFT
                case _ => direction
            }
        } else if (vertical) {
            direction match {
                case Direction.UP => Direction.DOWN
                case Direction.DOWN => Direction.UP
                case Direction.UP_LEFT => Direction.DOWN_LEFT
                case Direction.UP_RIGHT => Direction.DOWN_RIGHT
                case Direction.DOWN_LEFT => Direction.UP_LEFT
                case Direction.DOWN_RIGHT => Direction.UP_RIGHT
                case _ => direction
            }
        } else {
            direction
        }
    }
    
    private def handleParticleCollisions(particles: List[Particle], particleRadius: Int): List[Particle] = {
        particles.zipWithIndex.map { case (particle, index) =>
            val otherParticles = particles.zipWithIndex.filter(_._2 != index).map(_._1)
            
            findCollision(particle, otherParticles, particleRadius) match {
                case Some(collidedParticle) =>
                    val newDirection = calculateCollisionDirection(particle, collidedParticle)
                    val separatedCoords = separateParticles(particle.coordinates, collidedParticle.coordinates, particleRadius)
                    particle.copy(direction = newDirection, coordinates = separatedCoords)
                case None => particle
            }
        }
    }
    
    private def findCollision(particle: Particle, otherParticles: List[Particle], particleRadius: Int): Option[Particle] = {
        val collisionDistance = particleRadius * 2
        
        otherParticles.find { other =>
            calculateDistance(particle.coordinates, other.coordinates) <= collisionDistance
        }
    }
    
    private def calculateDistance(coord1: Coordinates, coord2: Coordinates): Double = {
        val dx = coord1.x - coord2.x
        val dy = coord1.y - coord2.y
        math.sqrt(dx * dx + dy * dy)
    }
    
    private def calculateCollisionDirection(particle1: Particle, particle2: Particle): Direction = {
        // Fait par copilot
        val dx = particle1.coordinates.x - particle2.coordinates.x
        val dy = particle1.coordinates.y - particle2.coordinates.y
        
        val distance = math.sqrt(dx * dx + dy * dy)
        if (distance == 0) return Direction.random()
        
        val angle = math.atan2(dy, dx)
        val degrees = math.toDegrees(angle)
        val normalizedAngle = if (degrees < 0) degrees + 360 else degrees
        
        angleToDirection(normalizedAngle)
    }
    
    private def angleToDirection(angle: Double): Direction = {
        // Fait par copilot
        angle match {
            case a if a >= 337.5 || a < 22.5  => Direction.RIGHT
            case a if a >= 22.5 && a < 67.5   => Direction.DOWN_RIGHT
            case a if a >= 67.5 && a < 112.5  => Direction.DOWN
            case a if a >= 112.5 && a < 157.5 => Direction.DOWN_LEFT
            case a if a >= 157.5 && a < 202.5 => Direction.LEFT
            case a if a >= 202.5 && a < 247.5 => Direction.UP_LEFT
            case a if a >= 247.5 && a < 292.5 => Direction.UP
            case a if a >= 292.5 && a < 337.5 => Direction.UP_RIGHT
            case _                            => Direction.random()
        }
    }
    
    private def separateParticles(coord1: Coordinates, coord2: Coordinates, particleRadius: Int): Coordinates = {
        val minDistance = particleRadius * 2
        val dx = coord1.x - coord2.x
        val dy = coord1.y - coord2.y
        val distance = math.sqrt(dx * dx + dy * dy)
        
        if (distance < minDistance && distance > 0) {
            val separationFactor = (minDistance - distance) / distance / 2
            val separationX = (dx * separationFactor).toInt
            val separationY = (dy * separationFactor).toInt
            
            Coordinates(coord1.x + separationX, coord1.y + separationY)
        } else {
            coord1
        }
    }
}
