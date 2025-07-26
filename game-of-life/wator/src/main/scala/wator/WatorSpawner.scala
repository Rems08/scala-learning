package wator

import scalafx.scene.paint.Color

case class WatorSpawner() {

    def spawnWatorWorld(
        width: Int,
        height: Int,
        cellSize: Double,
        nTunas: Int,
        nSharks: Int,
        tBreed: Int = 3,
        sBreed: Int = 5,
        sEnergy: Int = 3
    ): List[WatorCell] = {
        val gridWidth = (width / cellSize).toInt
        val gridHeight = (height / cellSize).toInt
        
        // création de l'ocean sans thon ni requins
        val emptyCells = (0 until gridWidth).flatMap { x =>
            (0 until gridHeight).map { y =>
                WatorCell(Coordinates(x, y), None)
            }
        }.toList
        
        val cellsWithTunas = addTunas(emptyCells, nTunas, tBreed)
        
        addSharks(cellsWithTunas, nSharks, sBreed, sEnergy)
    }
    
    private def addTunas(cells: List[WatorCell], nTunas: Int, tBreed: Int): List[WatorCell] = {
        val emptyCells = cells.filter(_.isEmpty)
        val selectedCells = scala.util.Random.shuffle(emptyCells).take(nTunas)
        
        cells.map { cell =>
            if (selectedCells.contains(cell)) {
                cell.copy(species = Some(Tuna(cell.coordinates, breed = tBreed)))
            } else {
                cell
            }
        }
    }
    
    private def addSharks(cells: List[WatorCell], nSharks: Int, sBreed: Int, sEnergy: Int): List[WatorCell] = {
        val emptyCells = cells.filter(_.isEmpty)
        val selectedCells = scala.util.Random.shuffle(emptyCells).take(nSharks)
        
        cells.map { cell =>
            if (selectedCells.contains(cell)) {
                cell.copy(species = Some(Shark(cell.coordinates, breed = sBreed, energy = sEnergy)))
            } else {
                cell
            }
        }
    }

    def moveCells(
        cells: List[WatorCell],
        screenWidth: Int,
        screenHeight: Int,
        cellSize: Double
    ): List[WatorCell] = {
        val gridWidth = (screenWidth / cellSize).toInt
        val gridHeight = (screenHeight / cellSize).toInt
        
        // Version simplifiée - juste bouger aléatoirement
        cells.map { cell =>
            cell.species match {
                case Some(shark: Shark) =>
                    val neighbors = getNeighbors(shark.coordinates, gridWidth, gridHeight)
                    val targetCoord = if (neighbors.nonEmpty) neighbors(scala.util.Random.nextInt(neighbors.length)) else shark.coordinates
                    
                    val newShark = shark.copy(
                        coordinates = targetCoord,
                        age = shark.age + 1,
                        energy = shark.energy - 1
                    )
                    
                    if (newShark.isDead) {
                        WatorCell(targetCoord, None)
                    } else {
                        WatorCell(targetCoord, Some(newShark))
                    }
                    
                case Some(tuna: Tuna) =>
                    val neighbors = getNeighbors(tuna.coordinates, gridWidth, gridHeight)
                    val targetCoord = if (neighbors.nonEmpty) neighbors(scala.util.Random.nextInt(neighbors.length)) else tuna.coordinates
                    
                    val newTuna = tuna.copy(
                        coordinates = targetCoord,
                        age = tuna.age + 1
                    )
                    
                    WatorCell(targetCoord, Some(newTuna))
                    
                case None =>
                    cell
            }
        }
    }
    
    private def getNeighbors(coords: Coordinates, gridWidth: Int, gridHeight: Int): List[Coordinates] = {
        val directions = List(
            (-1, -1), (-1, 0), (-1, 1),
            (0, -1),           (0, 1),
            (1, -1),  (1, 0),  (1, 1)
        )
        
        directions.map { case (dx, dy) =>
            Coordinates(
                (coords.x + dx + gridWidth) % gridWidth,
                (coords.y + dy + gridHeight) % gridHeight
            )
        }
    }
}
