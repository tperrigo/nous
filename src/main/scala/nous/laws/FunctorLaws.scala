package nous
package laws

import nous.core._
import nous.core.Functor.ops._

trait FunctorLaws[F[_]] {

  implicit def F: Functor[F]

  def identity[A](fa: F[A]) = F.map(fa)(a => a) =?= fa

  def composition[A, B, C](fa: F[A])(f: A => B)(g: B => C) =
    F.map(F.map(fa)(f))(g) =?= F.map(fa)(f andThen g)
}

object FunctorLaws {
  def apply[F[_]](implicit F0: Functor[F]): FunctorLaws[F] = new FunctorLaws[F] {
    def F = F0
  }
}