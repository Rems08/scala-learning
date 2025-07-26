package wator

import scalafx.scene.shape.Shape

case class WatorWorld(
    grid: Array[Array[WatorCell]],
    screenWidth: Int,
    screenHeight: Int,
    cellSize: Double = 5.0
) {

    private val watorSimulation: WatorSimulation = WatorSimulation()
    private val watorDrawer: WatorDrawer = WatorDrawer(cellSize)

    def update(): WatorWorld = {
        val updatedGrid = watorSimulation.updateGrid(grid)
        this.copy(grid = updatedGrid)
    }

    def drawCells(): List[Shape] = {
        val cells = watorSimulation.gridToList(grid)
        watorDrawer.draw(cells)
    }
}