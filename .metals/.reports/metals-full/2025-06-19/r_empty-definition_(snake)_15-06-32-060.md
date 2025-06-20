error id: file://<WORKSPACE>/snake/src/main/scala/snake/ScalaFXHelloWorld.scala:
file://<WORKSPACE>/snake/src/main/scala/snake/ScalaFXHelloWorld.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 592
uri: file://<WORKSPACE>/snake/src/main/scala/snake/ScalaFXHelloWorld.scala
text:
```scala
package snake

import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint._
import scalafx.scene.text.Text

object ScalaFXHelloWorld extends JFXApp3 {

  val initialSnake: Snake = Snake(
    body = List((200, 200), (250, 200), (300, 200)), 
    direction = Direction.EAST
  )

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      //    initStyle(StageStyle.Unified)
      title = "ScalaFX Hello Worl@@d"
      width = 600
      height = 600
      scene = new Scene {
        fill = Color.rgb(38, 38, 38)
        content = initialSnake.draw
      }
    }
  }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 