package wator

import scalafx.scene.paint.Color
import scalafx.scene.shape.{Rectangle, Shape}

case class WatorDrawer(cellSize: Double) {

  def draw(cells: List[WatorCell]): List[Shape] = {
    cells.map(draw)
  }

  private def draw(cell: WatorCell): Shape = {
    new Rectangle {
      width = cellSize
      height = cellSize
      x = cell.coordinates.x * cellSize
      y = cell.coordinates.y * cellSize
      fill = cell.color
    }
  }
}
