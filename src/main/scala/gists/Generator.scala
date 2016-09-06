package nous
package gists

import util.Random

// Way of making a function deterministic or random
trait Generator {
  def nextInt(n: Int): Int
  def nextPair(n: Int): (Int, Int)
}

object Nondeterministic {

  implicit val Generator = new Generator {
    def nextInt(n: Int) = Random.nextInt(n)

    def nextPair(n: Int) = (nextInt(n), nextInt(n))
  }

  def generatePair(n: Int)(implicit g: Generator): (Int, Int) = g.nextPair(n)
}

object Deterministic {
  implicit val Generator = new Generator {
    def nextInt(n: Int) = 5

    def nextPair(n: Int) = (5, n - 1)
  }

  def generatePair(n: Int)(implicit g: Generator): (Int, Int) = g.nextPair(n)
}

