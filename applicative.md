##Applicative
Applicatives are a [generalization of Functors](https://hseeberger.wordpress.com/2011/01/31/applicatives-are-generalized-functors/) that allow us to apply functions with an arity <= 1 to arguments within a context.  In other words, while Functor allowed us to apply a function `A => B` to a value _in the context of_ a type constructor (`F[A]`), Applicatives generalize this behavior to allow us to apply functions with more than one argument to our _contextualized_ value (`F[_]`).  This is all pretty abstract, so let's look at an example where Functors fall short (the following example is based on the description of Applicatives in the Scala [Typeclassopedia](http://typeclassopedia.bitbucket.org/#slide-46).

Let's say we have a function that attempts to parse a `String` into an `Int`; since this operation could fail, we'll have this function return an `Option[Int]`, where the result will be `Some[Int]` if successful, otherwise `None`:

    def parse(s: String): Option[Int] = ...

We know that we have a `Functor` for `Option`, so we have `map` available: we can apply any single-argument function to the `Int` within the `Option`, but what would happen if we tried to add two parsed integers using `map`?

    parse("3").map((x: Int) => (y: Int) => x + y)

This will evaluate to an `Option[Int => Int]` (a function within the `Option` context), where we wanted an `Option[Int]` containing the sum of two parsed integers.  In this case, `map` can't provide the behavior we need...we need a way to take a function within a context and apply it to a value within the same context.  This is precisely the behavior that the `Applicative` typeclass provides with it's `apply` method:

    @typeclass trait Applicative[F[_]] extends Functor[F] {
      def apply[A, B](fa: F[A])(ff: F[A => B]): F[B]
    }

The `apply` method would make the previous example of adding two parsed `Int`s possible
