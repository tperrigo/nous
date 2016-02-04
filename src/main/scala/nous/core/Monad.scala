package nous
package core

import simulacrum._

@typeclass trait Monad[F[_]] extends Applicative[F] { self =>
  def pure[A](a: A): F[A]

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  override def apply[A, B](fa: F[A])(ff: F[A => B]): F[B] =
    flatMap(ff)((f: A => B) => map(fa)(f))

  // DERIVED METHODS
  // although by default we can use the implementation of map provided by Applicative,
  // there is a performance benefit to implementing map in terms of flatMap and pure
  // instead of pure and apply.
  override def map[A, B](fa: F[A])(f: A => B): F[B] =
    flatMap(fa)(a => pure(f(a)))

  // flattening is just flatMap with the identity function
  def flatten[A, B](ffa: F[F[A]]): F[A] =
    flatMap(ffa)(fa => fa)

  // Monad's aren't universally composable (certain Monads can be composed, such as the List monad and Option monad)
  /**
   * def compose[G[_]](implicit G: Monad[G]): Monad[Lambda[X => F[G[X]]]] =
   * new Monad[Lambda[X => F[G[X]]]] {
   * def pure[A](a: A): F[G[A]] = self.pure(G.pure(a))
   *
   * def flatMap[A, B](fga: F[G[A]])(f: A => F[G[B]]): F[G[B]] = {
   * val nested = self.map(fga) { ga => G.map(ga) { a => f(a): F[G[B]] }: G[F[G[B]]] }:  F[G[F[G[B]]]]
   * flatten(nested) // here's the problem-- flatten is defined in terms of flatMap, but here we're implementing flatMap in terms of flatten
   * }
   * }
   */
  // Monad's don't support filter (not enough info to implement this signature)(can be done for Lists and Options, but not for all Monads in general) : 
  /**
   * def filter[A, B](fa: F[A])(f: A => Boolean): F[A] =
   * flatMap(fa)(a => if (f(a)) pure(a)) else ???) // we don't have a generic way to say "remove this A from the context"
   */
}

object Monad {
  implicit val optionMonad: Monad[Option] = new Monad[Option] {
    def pure[A](a: A): Option[A] = Option(a)
    def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)
  }

  implicit val listMonad: Monad[List] = new Monad[List] {
    def pure[A](a: A): List[A] = List(a)
    def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = fa.flatMap(f)
  }
}