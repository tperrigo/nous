package nous
package gists
package ga

import org.scalatest.{ Matchers, FlatSpecLike }
import Chromosome._
import Operators._

class MutationSpec extends FlatSpecLike with Matchers {
  "A Swap Mutation" should "exchange the position of 2 genes in a chromosome" in {
    implicit val Generator = new Generator {
      def nextInt(n: Int): Int = ??? // not needed
      def nextPair(n: Int): (Int, Int) = (2, 4)
    }

    val c1: Chromosome[Int] = Chromosome(0, 1, 2, 3, 4)
    val c2 = new SwapMutation()(c1)
    println(c2)
    c2(2) shouldBe 4
    c2(4) shouldBe 2
  }
}