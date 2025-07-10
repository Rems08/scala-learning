package particle

import scalafx.animation.KeyFrame
import scalafx.animation.Timeline
import scalafx.animation.Timeline.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.util.Duration
import scalafx.stage.Screen
import scalafx.scene.Group

object Main extends JFXApp3 {

  val particlesRadius: Int = 5
  val numberOfParticles: Int = 100

  override def start(): Unit = {

    val (screenWidth, screenHeight) = (Screen.primary.visualBounds.width.toInt, Screen.primary.visualBounds.height.toInt)
    
    val particleSpawner: ParticleSpawner = ParticleSpawner()
    val initialParticles: List[Particle] = particleSpawner.spawnParticles(
      numberOfParticles, 
      screenWidth,
      screenHeight
    )

    // État initial du monde des particules
    var particleWorld: ParticleWorld = ParticleWorld(
      initialParticles,
      screenWidth,
      screenHeight
    )

    // Groupe qui contiendra toutes les formes
    val root = new Group()

    stage = new PrimaryStage {
      title = "Particle Simulation"
      width = screenWidth
      height = screenHeight
      scene = new Scene(root, screenWidth, screenHeight) {
        fill = Color.White
      }
    }

    // Fonction pour mettre à jour l'affichage
    def updateDisplay(): Unit = {
      particleWorld = particleWorld.update()
      val shapes = particleWorld.drawParticles()
      root.children.clear()
      shapes.foreach(shape => root.children.add(shape))
    }

    // Timeline pour l'animation
    val timeline = new Timeline {
      keyFrames = List(
        KeyFrame(
          time = Duration(25),
          onFinished = _ => updateDisplay()
        )
      )
      cycleCount = Indefinite
    }

    // Affichage initial
    updateDisplay()
    
    // Démarrage de l'animation
    timeline.play()
  }
}

