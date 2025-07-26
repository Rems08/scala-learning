package wator

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

  val cellSize: Double = 10.0
  val gridWidth: Int = 150
  val gridHeight: Int = 80
  val nTunas: Int = 500
  val nSharks: Int = 10
  val tBreed: Int = 1  // Cycles avant reproduction des thons
  val sBreed: Int = 10  // Cycles avant reproduction des requins
  val sEnergy: Int = 2 // Énergie initiale des requins

  override def start(): Unit = {

    val windowWidth = gridWidth * cellSize.toInt
    val windowHeight = gridHeight * cellSize.toInt

    val watorSimulation: WatorSimulation = WatorSimulation()
    val initialGrid: Array[Array[WatorCell]] = watorSimulation.spawnWatorWorld(
      windowWidth,
      windowHeight,
      cellSize = cellSize,
      nTunas = nTunas,
      nSharks = nSharks,
      tBreed = tBreed,
      sBreed = sBreed,
      sEnergy = sEnergy
    )

    var watorWorld: WatorWorld = WatorWorld(
      initialGrid,
      windowWidth,
      windowHeight,
      cellSize
    )

    val root = new Group()

    stage = new PrimaryStage {
      title = "Wator Simulation - Requins et Thons"
      width = windowWidth
      height = windowHeight
      scene = new Scene(root, windowWidth, windowHeight) {
        fill = Color.Black
      }
    }

    def updateDisplay(): Unit = {
      watorWorld = watorWorld.update()
      val shapes = watorWorld.drawCells()
      root.children.clear()
      shapes.foreach(shape => root.children.add(shape))
    }

    val timeline = new Timeline {
      keyFrames = List(
        KeyFrame(
          time = Duration(50), // augmenter pour debug
          onFinished = _ => updateDisplay()
        )
      )
      cycleCount = Indefinite
    }

    updateDisplay()
    
    timeline.play()
  }
}

