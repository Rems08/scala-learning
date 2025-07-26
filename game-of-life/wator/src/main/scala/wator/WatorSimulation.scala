package wator

case class WatorSimulation() {

    def spawnWatorWorld(
        width: Int,
        height: Int,
        cellSize: Double,
        nTunas: Int,
        nSharks: Int,
        tBreed: Int = 3,
        sBreed: Int = 5,
        sEnergy: Int = 3
    ): Array[Array[WatorCell]] = {
        val gridWidth = (width / cellSize).toInt
        val gridHeight = (height / cellSize).toInt
        
        // https://www.geeksforgeeks.org/scala/multidimensional-arrays-in-scala/
        val grid = Array.ofDim[WatorCell](gridWidth, gridHeight)
        
        for {
            x <- 0 until gridWidth
            y <- 0 until gridHeight
        } {
            grid(x)(y) = WatorCell(Coordinates(x, y), None)
        }
    
        addTunasToGrid(grid, nTunas, tBreed)
        
        addSharksToGrid(grid, nSharks, sBreed, sEnergy)
        
        grid
    }
    
    private def addTunasToGrid(grid: Array[Array[WatorCell]], nTunas: Int, tBreed: Int): Unit = {
        val emptyCells = for {
            x <- grid.indices
            y <- grid(x).indices
            if grid(x)(y).isEmpty
        } yield (x, y)
        
        val selectedCells = scala.util.Random.shuffle(emptyCells.toList).take(nTunas)
        
        selectedCells.foreach { case (x, y) =>
            grid(x)(y) = WatorCell(Coordinates(x, y), Some(Tuna(Coordinates(x, y), breed = tBreed)))
        }
    }
    
    private def addSharksToGrid(grid: Array[Array[WatorCell]], nSharks: Int, sBreed: Int, sEnergy: Int): Unit = {
        val emptyCells = for {
            x <- grid.indices
            y <- grid(x).indices
            if grid(x)(y).isEmpty
        } yield (x, y)
        
        val selectedCells = scala.util.Random.shuffle(emptyCells.toList).take(nSharks)
        
        selectedCells.foreach { case (x, y) =>
            grid(x)(y) = WatorCell(Coordinates(x, y), Some(Shark(Coordinates(x, y), breed = sBreed, energy = sEnergy)))
        }
    }

    def updateGrid(grid: Array[Array[WatorCell]]): Array[Array[WatorCell]] = {
        val newGrid = Array.ofDim[WatorCell](grid.length, grid(0).length)
        val moved = Array.ofDim[Boolean](grid.length, grid(0).length)
        
        // Initialiser la nouvelle grille avec des cellules vides
        for {
            x <- grid.indices
            y <- grid(x).indices
        } {
            newGrid(x)(y) = WatorCell(Coordinates(x, y), None)
        }
        
        // Déplacer les requins en premier
        for {
            x <- grid.indices
            y <- grid(x).indices
            if grid(x)(y).hasShark && !moved(x)(y)
        } {
            moveSharks(grid, newGrid, moved, x, y)
        }
        
        // Déplacer les thons
        for {
            x <- grid.indices
            y <- grid(x).indices
            if grid(x)(y).hasTuna && !moved(x)(y)
        } {
            moveTunas(grid, newGrid, moved, x, y)
        }
        
        newGrid
    }
    
    private def moveSharks(
        oldGrid: Array[Array[WatorCell]], 
        newGrid: Array[Array[WatorCell]], 
        moved: Array[Array[Boolean]], 
        x: Int, 
        y: Int
    ): Unit = {
        oldGrid(x)(y).species match {
            case Some(shark: Shark) =>

                val neighbors = getNeighbors(x, y, oldGrid.length, oldGrid(0).length)
                
                // Chercher des thons à manger
                val tunasInNeighbors = neighbors.filter { case (nx, ny) =>
                    oldGrid(nx)(ny).hasTuna && !moved(nx)(ny)
                }
                
                if (tunasInNeighbors.nonEmpty) {
                    // Choisi un index aléatoire parmi les thons disponibles et pareil pour les requins
                    val randomIndex = scala.util.Random.nextInt(tunasInNeighbors.length)
                    val (targetX, targetY) = tunasInNeighbors(randomIndex)
                    val newShark = shark.copy(
                        coordinates = Coordinates(targetX, targetY),
                        age = shark.age + 1,
                        energy = shark.getEnergyAtSpawn
                    )
                    
                    newGrid(targetX)(targetY) = WatorCell(Coordinates(targetX, targetY), Some(newShark))
                    moved(targetX)(targetY) = true
                    
                    // Reproduction
                    if (newShark.canReproduce) {
                        val babyShark = Shark(Coordinates(x, y), breed = shark.breed, energy = shark.energy)
                        newGrid(x)(y) = WatorCell(Coordinates(x, y), Some(babyShark))
                    }
                } else {
                    // Chercher des cases vides
                    val emptyInNeighbors = neighbors.filter { case (nx, ny) =>
                        oldGrid(nx)(ny).isEmpty
                    }
                    
                    if (emptyInNeighbors.nonEmpty) {
                        val randomIndex = scala.util.Random.nextInt(emptyInNeighbors.length)
                        val (targetX, targetY) = emptyInNeighbors(randomIndex)
                        val newShark = shark.copy(
                            coordinates = Coordinates(targetX, targetY),
                            age = shark.age + 1,
                            energy = shark.energy - 1
                        )
                        
                        if (!newShark.isDead) {
                            newGrid(targetX)(targetY) = WatorCell(Coordinates(targetX, targetY), Some(newShark))
                            moved(targetX)(targetY) = true
                            
                            // Reproduction
                            if (newShark.canReproduce) {
                                val babyShark = Shark(Coordinates(x, y), breed = shark.breed, energy = shark.energy)
                                newGrid(x)(y) = WatorCell(Coordinates(x, y), Some(babyShark))
                            }
                        }
                    } else {
                        // Pas de mouvement possible
                        val newShark = shark.copy(age = shark.age + 1, energy = shark.energy - 1)
                        if (!newShark.isDead) {
                            newGrid(x)(y) = WatorCell(Coordinates(x, y), Some(newShark))
                        }
                    }
                }
            case _ =>
        }
    }
    
    private def moveTunas(
        oldGrid: Array[Array[WatorCell]], 
        newGrid: Array[Array[WatorCell]], 
        moved: Array[Array[Boolean]], 
        x: Int, 
        y: Int
    ): Unit = {
        oldGrid(x)(y).species match {
            case Some(tuna: Tuna) =>
                val neighbors = getNeighbors(x, y, oldGrid.length, oldGrid(0).length)
                
                val emptyInNeighbors = neighbors.filter { case (nx, ny) =>
                    oldGrid(nx)(ny).isEmpty && newGrid(nx)(ny).isEmpty
                }
                
                if (emptyInNeighbors.nonEmpty) {
                    val randomIndex = scala.util.Random.nextInt(emptyInNeighbors.length)
                    val (targetX, targetY) = emptyInNeighbors(randomIndex)
                    val newTuna = tuna.copy(
                        coordinates = Coordinates(targetX, targetY),
                        age = tuna.age + 1
                    )
                    
                    newGrid(targetX)(targetY) = WatorCell(Coordinates(targetX, targetY), Some(newTuna))
                    moved(targetX)(targetY) = true
                    
                    // Reproduction
                    if (newTuna.canReproduce && newGrid(x)(y).isEmpty) {
                        val babyTuna = Tuna(Coordinates(x, y), breed = tuna.breed)
                        newGrid(x)(y) = WatorCell(Coordinates(x, y), Some(babyTuna))
                    }
                } else {
                    // Pas de mouvement possible
                    val newTuna = tuna.copy(age = tuna.age + 1)
                    if (newGrid(x)(y).isEmpty) {
                        newGrid(x)(y) = WatorCell(Coordinates(x, y), Some(newTuna))
                    }
                }
            case _ =>
        }
    }
    
    private def getNeighbors(x: Int, y: Int, maxX: Int, maxY: Int): List[(Int, Int)] = {
        val directions = List(
            (-1, -1), (-1, 0), (-1, 1),
            (0, -1),           (0, 1),
            (1, -1),  (1, 0),  (1, 1)
        )
        
        directions.map { case (dx, dy) =>
            ((x + dx + maxX) % maxX, (y + dy + maxY) % maxY)
        }
    }
    
    def gridToList(grid: Array[Array[WatorCell]]): List[WatorCell] = {
        (for {
            x <- grid.indices
            y <- grid(x).indices
        } yield grid(x)(y)).toList
    }
}
