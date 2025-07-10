package particle

enum Direction {
  case UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
}

object Direction {
  def random(): Direction = {
    scala.util.Random.nextInt(8) match {
      case 0 => UP
      case 1 => DOWN
      case 2 => LEFT
      case 3 => RIGHT
      case 4 => UP_LEFT
      case 5 => UP_RIGHT
      case 6 => DOWN_LEFT
      case 7 => DOWN_RIGHT
    }
  }
}