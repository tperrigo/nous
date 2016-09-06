package nous
package core

import org.scalacheck._
import Prop._
import Arbitrary.arbitrary
import nous.core.std._
import nous.laws._

abstract class EqualInstanceTest[A](name: String)(implicit E: Equal[A], arbInt: Arbitrary[Int]) extends Properties(s"EqualInstanceTest[$name]") {

  val laws = EqualLaws[A]

  property("equal reflexive") = forAll { xs: E[Int] =>
    laws.equalReflexive(xs).isEqual
  }

}

object IntEqualTest extends EqualInstanceTest[Int]("Int")
