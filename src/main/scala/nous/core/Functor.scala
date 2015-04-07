package nous.core 

import simulacrum._

@typeclass trait Functor[F[_]] { self => 
  def map[A, B](fa: F[A])(f: A => B): F[B]

  def lift[A,B](f: A => B): F[A] => F[B] = 
    fa => map(fa)(f)

  def as[A,B](fa: F[A], b: => B): F[B] = 
    map(fa)(_ => b)

  def void[A](fa: F[A]): F[Unit] = 
    as(fa, ())

  def compose[G[_]](implicit G : Functor[G]): Functor[Lambda[X => F[G[X]]]] = 
    new Functor[Lambda[X => F[G[X]]]] {
      def map[A, B](fga: F[G[A]])(f: A => B): F[G[B]] =
        self.map(fga)(ga => G.map(ga)(a => f(a)))
    }
}

object Functor {
  implicit val listFunctor : Functor[List] = new Functor[List] {
    def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)  
  }

  implicit val optionFunctor : Functor[Option] = new Functor[Option] {
    def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)  
  }

  // Functors are type constructors of 1 argument, but Function1 is a type constructor
  // with 2 type parameters.  We need to partially apply 1 type to the Function1 type constructor.

  implicit def function1Functor[X] : Functor[X => ?]  = new Functor[X => ?] {
    def map[A, B](fa: X => A)(f: A => B): X => B = fa andThen f
  }

}

trait FunctorLaws[F[_]] {
  import Functor.ops._
//  import IsEq._

  implicit def F: Functor[F]

  def identity[A](fa: F[A]) = F.map(fa)(a => a) == fa

  def composition[A, B, C](fa: F[A], f: A => B, g: B => C) =
    F.map(F.map(fa)(f))(g) == F.map(fa)(f andThen g)
}

object FunctorLaws {
  def apply[F[_]](implicit F0: Functor[F]): FunctorLaws[F] = new FunctorLaws[F] {
    def F = F0
  }
}
