package particle

import scalafx.animation.KeyFrame
import scalafx.animation.Timeline
import scalafx.animation.Timeline.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.util.Duration
import scalafx.beans.property.IntegerProperty
import javafx.scene.input.{KeyCode, KeyEvent}
import javafx.scene.input.KeyCode.*
import scalafx.stage.Screen

object Main extends JFXApp3 {

  val particlesRadius: Int           = 5

  val particle: Particle = Particle(
    Direction.UP,
    Coordinates(250, 250),
    Color.Black
  )

  override def start(): Unit = {

    val (screenWidth, screenHeight) = (Screen.primary.visualBounds.width.toInt, Screen.primary.visualBounds.height.toInt)

    stage = new PrimaryStage {
      title = "Particle Simulation"
      width = screenWidth
      height = screenHeight
      scene = new Scene {
        fill = Color.White
        content = particle.draw(particlesRadius)
      }
    }

    new Timeline {
      keyFrames = List(
        KeyFrame(
          time = Duration(25),
          onFinished = _ => {
            particle.draw(particlesRadius)
          }
        )
      )
      cycleCount = Indefinite
    }.play()
  }
}

