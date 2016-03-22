package nous
package laws

import nous.core._
import nous.core.Monad.ops._


trait MonadLaws[F[_]] {

  implicit val F: Monad[F]

  import F.pure

  def flatMapAssociativity[A, B, C](fa: F[A], f: A => F[B], g: B => F[C]): IsEqual[F[C]] =
    fa.flatMap(f).flatMap(g) =?= fa.flatMap { a => f(a).flatMap { b => g(b) } }

  def leftIdentity[A, B](a: A, f: A => F[B]): IsEqual[F[B]] =
    pure(a).flatMap(f) =?= f(a)

  def rightIdentity[A](fa: F[A]): IsEqual[F[A]] =
    fa.flatMap { a => pure(a) } =?= fa

}

object MonadLaws {
  def apply[F[_]](implicit F0: Monad[F]): MonadLaws[F] = new MonadLaws[F] {
    val F = F0
  }

}
