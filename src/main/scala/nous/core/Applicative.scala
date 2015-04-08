package nous
package core

import simulacrum._

@typeclass trait Applicative[F[_]] extends Functor[F] { self =>

  // lift a value into a context (into an "effect system")
  // using type constructors to model an "effect": something visible through all type signatures by the presence of type constructor F
  // (e.g, Option has the effect of having a value or not having a value; List has the effect of having no values or many values)
  def pure[A](a : A): F[A]

  // similar to map, but takes a function which exists in the context (effect system)
  // a more general version of map (Applicatives are generalized Functors)
  def apply[A, B](fa: F[A])(ff: F[A => B]): F[B]

  // DERIVED METHODS.....
  // By substitution, a => f(a, b) is a function A => Z
  // b => a => f(a, b) is a function B => (A => Z)
  def apply2[A, B, Z](fa: F[A], fb: F[B])(ff: F[(A, B) => Z]): F[Z] =
    apply(fa)(apply(fb)(map(ff)(f => b => a => f(a, b))))

  // since we have defined map in terms of pure and apply, we can declare that Applicative is a Functor
  // (add "extends Functor[F]" to the trait definition, and declare that map is overridden)
  override def map[A, B](fa: F[A])(f: A => B): F[B] =
    apply(fa)(pure(f))

  // This description of map2 works from the inside out...
  // partially applying f with b (f(_, b)) yields a z, so f(_, b) = A => Z
  // by mapping over an F[B], the result is an F[A => Z];
  // when we apply this to an F[A] (fa), we get back an F[Z]
  def map2[A, B, Z](fa: F[A], fb: F[B])(f: (A, B) => Z): F[Z] = 
    apply(fa)(map(fb)(b => f(_, b)))

  // map2 over fb, fc (takes a function (B, C), returns a function from a => z (since f(a,b,c) => z)), in the context F[A => Z],
  // which when applied to F[A] yields F[Z]
  def map3[A, B, C, Z](fa: F[A], fb: F[B], fc: F[C])(f: (A, B, C) => Z): F[Z] =
    apply(fa)(map2(fb, fc)((b, c) => a => f(a, b, c)))

  // map4 could be implemented following the same pattern; e.g:
  // apply(fa)(map3(fb, fc, fd)((b, c, d) => a => f(a, b, c, d)))
  // but now that we have the tuple methods available, we can use a "divide and conquer approach",
  // where we tuple-up the arguments, and then map2 twice and combine the results:
  def map4[A, B, C, D, Z](fa: F[A], fb: F[B], fc: F[C], fd: F[D])(f: (A, B, C, D) => Z): F[Z] =
    map2(tuple2(fa, fb), tuple2(fc, fd)) { case ((a, b), (c, d)) => f(a, b, c, d) }

  def tuple2[A, B](fa: F[A], fb: F[B]): F[(A, B)] =
    map2(fa, fb)((a, b) => (a, b))

  def tuple3[A, B, C](fa: F[A], fb: F[B], fc: F[C]): F[(A, B, C)] =
    map3(fa, fb, fc)((a, b, c) => (a, b, c))

  // To be consistent with the Scala libraries, we would really want to define tuple and map arities up to 22
  // (since Function22 and Tuple22 are the highest-arity functions and tuples defined by Scala).
  // These could all be defined using the same divide-and-conquer approach as map4; e.g,
  // map5 could be implemented using tuple2 and tuple3; map6 could be a map2 of 2 tuple3's, etc.

  // similar to Functor.lift (which takes a function A => B and returns an F[A => B]),
  // flip takes an F[A => B] and returns a function F[A] => F[B]
  // Curry the fa parameter to apply (just like lift)
  def flip[A, B](ff: F[A => B]): F[A] => F[B] =
    fa => apply(fa)(ff)

  // Like Functors, Applicatives are composable:
  def compose[G[_]](implicit G: Applicative[G]): Applicative[Lambda[X => F[G[X]]]] =
    new Applicative[Lambda[X => F[G[X]]]] {
      def pure[A](a : A): F[G[A]] = self.pure(G.pure(a))

      def apply[A, B](fga: F[G[A]])(ff: F[G[A => B]]): F[G[B]] = {
        // Distribute G over the function A => B (convert G[A => B] to G[A] => G[B])
        // gab has the type G[A => B], so we can use flip to distribute the G type constructor
        // over the function A=>B
        val x: F[G[A] => G[B]] = self.map(ff)(gab => G.flip(gab))
        self.apply(fga)(x)
      }
    }
}


object Applicative {
  implicit val optionApplicative: Applicative[Option] = new Applicative[Option] {
    def pure[A](a: A): Option[A] = Some(a)

    def apply[A, B](fa: Option[A])(ff: Option[A => B]): Option[B] = (fa, ff) match {
      case (None, _) => None
      case (Some(a), None) => None
      case (Some(a), Some(f)) => Some(f(a))
    }
  }

  implicit val listApplicative: Applicative[List] = new Applicative[List] {
    def pure[A](a: A): List[A] = List(a)

    def apply[A, B](fa: List[A])(ff: List[A => B]): List[B] = for {
      a <- fa
      f <- ff
    } yield f(a)
  }

// Doesn't work-- the iteration using zip stops at the smaller of the 2 lists, so in cases such as:
// Applicative[List].map(List(1, 2, 3))(_ + 1)
// we only have 1 function (the increment function), so we would only get a List with 1 element back:
// Applicative[List].map(List(1, 2, 3))(_ + 1) --> List[Int] = List(2)
// This is an example of the importance for defining and adhering to the laws of the typeclass
//    def apply[A, B](fa: List[A])(ff: List[A => B]): List[B] = 
//      (fa zip ff).map { case (a, f) => f(a) }
}
