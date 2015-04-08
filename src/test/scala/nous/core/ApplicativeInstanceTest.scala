package nous
package core

import org.scalacheck._
import Prop._
import Arbitrary.arbitrary
import nous.core.std._
import nous.laws._

abstract class ApplicativeInstanceTest[F[_]](name: String)(implicit
  F: Applicative[F],
  arbFInt: Arbitrary[F[Int]],
  eqFInt: Equal[F[Int]],
  eqFString: Equal[F[String]]
) extends Properties(s"Applicative[$name]") {
  
  val laws = ApplicativeLaws[F]

  // for some arbitrary F[Int], call the applicativeIdentity law
  property("applicative identity") = forAll { (xs: F[Int]) =>
    laws.applicativeIdentity(xs).isEqual
  }

  property("applicative homomorphism") = forAll { (a: Int, f: Int => String) =>
    laws.applicativeHomomorphism(a, f).isEqual
  }

}

object ListApplicativeTest extends ApplicativeInstanceTest[List]("List")
object OptionApplicativeTest extends ApplicativeInstanceTest[Option]("Option")