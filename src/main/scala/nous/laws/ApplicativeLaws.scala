package nous
package laws

import nous.core._
import nous.core.Applicative.ops._


trait ApplicativeLaws[F[_]] {

  implicit def F: Applicative[F]

  def applicativeIdentity[A](fa: F[A]) =
    fa.apply(F.pure((a: A) => a)) =?= fa

  // function application distributes over apply and pure
  def applicativeHomomorphism[A, B](a: A, f: A => B) = 
    F.pure(a).apply(F.pure(f)) =?= F.pure(f(a))

  // rhs: create an anonymous function f, apply it to return a b, and then lift the result into the F type constructor
  // so we can apply it to ff
  // NOTE: code example from the video did not have to specify f's type explicitly (possible scalac parameter difference?)
  def applicativeInterchange[A, B](a: A, ff: F[A => B]) =
    F.pure(a).apply(ff) =?= ff.apply(F.pure((f: (A => B)) => f(a)))

  // map must be consistent with apply and pure
  // (essentially a re-implementation of map)
  def applicativeMap[A, B](fa: F[A], f: A => B) = 
    fa.map(f) =?= fa.apply(F.pure(f))
}

object ApplicativeLaws {
  def apply[F[_]](implicit F0: Applicative[F]): ApplicativeLaws[F] = new ApplicativeLaws[F] {
    def F = F0
  }

}
