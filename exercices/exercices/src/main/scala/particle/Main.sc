def returnLowerInt(listInt: List[Int], lowerNumber: Int): Int =
  listInt match {
    case Nil => lowerNumber
    case head :: tail => 
      if (head > lowerNumber) {
        returnLowerInt(tail, lowerNumber)
      } else {
        returnLowerInt(tail, head)
      }
}

val nombres: List[Int] = List(2, 3, 1, 4, 5, 0, 6, 7, 8, 9)

result = returnLowerInt(nombres, nombres(0))

println(result) // Affiche 1