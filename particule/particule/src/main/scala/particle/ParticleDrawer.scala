package particle

import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Shape}

case class ParticleDrawer(particleRadius: Double) {

  def draw(particles: List[Particle]): List[Shape] = {
    particles.map(draw)
  }

  private def draw(particle: Particle): Shape = {
    new Circle {
      radius = particleRadius
      centerX = particle.coordinates.x
      centerY = particle.coordinates.y
      fill = particle.color
    }
  }
}
