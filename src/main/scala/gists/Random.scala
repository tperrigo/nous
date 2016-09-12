package nous
package gists

import util.Random

object RandomStuff extends App {
  println("Randomly permuting a list")
  val xs = List(1, 2, 3, 4, 5)
  println(s"Original List: ${xs}")
  println(Random.shuffle(xs))
  println(Random.shuffle(xs))

  println("Generating a random-length range")
  // will generate a range from the start value (in this case, 0) with a length up to the end value (10)
  var range = 0 to Random.nextInt(10)
  println(range)
  range = 0 to Random.nextInt(10)
  println(range)

  println("Creating a fixed sized sequence of random values")
  val ys = for (i <- 0 to 9) yield Random.nextInt(10)
  println(ys)

  // generating a random int within an exclusive range [min max]
  def nextInt(min: Int, max: Int): Int = Random.nextInt((max - min) + 1) + min
  println(s"random int within range [1 5] = ${nextInt(1, 5)}")
  println(s"random int within range [1 5] = ${nextInt(1, 5)}")
  println(s"random int within range [1 5] = ${nextInt(1, 5)}")

  // Testing that an event which should occur randomly x% of the time in fact does occur that often.
  // From: http://blog.jessitron.com/2013/08/a-trick-for-deterministic-testing-of.html
  // This could be useful for testing various selection strategies.

  // a simple class whose method returns true 40% of the time
  class Sometimes(rand: => Double = Random.nextDouble) {
    def should: Boolean = rand < 0.4
  }

  // create a sequence that contains numbers evenly distributed from 0 to 1, in a random order
  def evenDistribution(n: Int) = Random.shuffle(Range(0, n).map(_.toDouble).map(_ / n))

  // get an iterator over that sequence and use its next() method to produce the random number
  val notSoRandom = evenDistribution(100).iterator
  val sometimes = new Sometimes(notSoRandom.next())

  // exhaust the sequence to check that the random behavior occurs the expected number of times
  def results = Stream.continually(sometimes.should).take(100)
  println(s"results count == 40 ? ${results.count(t => t) == 40}")

}

