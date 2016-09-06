package nous
package gists
package ga

import util.Random

trait Generator {
  def nextInt(n: Int): Int
  def nextPair(n: Int): (Int, Int)
}

object Chromosome {
  type Chromosome[A] = Vector[A]

  def apply[A](genes: A*): Chromosome[A] = {
    Vector(genes: _*)
  }
}

object RandomGenerator {
  implicit val Generator = new Generator {
    def nextInt(n: Int) = Random.nextInt(n)

    def nextPair(n: Int) = (nextInt(n), nextInt(n))
  }
}

object Operators {
  import Chromosome._

  trait MutationOperator {
    def apply[A](c: Chromosome[A])(implicit g: Generator): Chromosome[A]
  }

  class SwapMutation extends MutationOperator {
    def apply[A](c: Chromosome[A])(implicit g: Generator): Chromosome[A] = {
      val (i, j) = g.nextPair(c.size)
      // purely functional swap; for most operators, it will be more efficient to change the Vector
      // to a mutable structure (eg, an array), and then switch back to a vector for the return chromosome
      c.updated(i, c(j)).updated(j, c(i))
    }
  }
}