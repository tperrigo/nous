package nous
package core

import org.scalacheck._
import Prop._
import Arbitrary.arbitrary
import nous.core.std._
import nous.laws._

abstract class MonadInstanceTest[F[_]](name: String)(implicit
  F: Monad[F],
  arbFInt: Arbitrary[F[Int]],
  eqFInt: Equal[F[Int]],
  eqFString: Equal[F[String]],
  eqFLong: Equal[F[Long]],
  arbFLong: Arbitrary[F[Long]],
  arbFString: Arbitrary[F[String]]
) extends Properties(s"Monad[$name]") {
  
  val laws = MonadLaws[F]

  property("flatMap associativity") = forAll { (fa: F[Int], f: Int => F[String], g: String => F[Long]) =>    
    laws.flatMapAssociativity(fa, f, g).isEqual
  }

  property("monad left identity") = forAll { (a: Int, f: Int => F[String]) =>
    laws.leftIdentity(a, f).isEqual
  }

  property("monad right identity") = forAll { (fa: F[Int]) =>
    laws.rightIdentity(fa).isEqual
  }
}

object ListMonadTest extends MonadInstanceTest[List]("List")
object OptionMonadTest extends MonadInstanceTest[Option]("Option")
// since we don't have an implicit Monad[List[Option]] available, we will need to pass it explicitly...
/** 
object ListOptionMonadTest extends MonadInstanceTest[Lambda[X => List[Option[X]]]]("List[Option]")(
  Monad[List] compose Monad[Option], implicitly, implicitly, implicitly, implicitly, implicitly, implicitly
)
**/