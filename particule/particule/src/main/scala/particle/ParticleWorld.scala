package particle

import scalafx.scene.shape.Shape

case class ParticleWorld(
    particles: List[Particle],
    screenWidth: Int,
    screenHeight: Int
) {

    private val particleSpawner: ParticleSpawner = ParticleSpawner()
    private val particleDrawer: ParticleDrawer = ParticleDrawer(5)

    def update(): ParticleWorld = {
        val updatedParticles = particleSpawner.moveParticles(particles, screenWidth, screenHeight)
        this.copy(particles = updatedParticles)
    }

    def drawParticles(): List[Shape] = {
        particleDrawer.draw(particles)
    }
}