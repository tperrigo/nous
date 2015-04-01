import simulacrum._

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]

  def lift[A,B](f: A => B): F[A] => F[B] = 
    fa => map(fa)(f)

  def as[A,B](fa: F[A], b: => B): F[B] = 
    map(fa)(_ => b)

  def void[A](fa: F[A]): F[Unit] = 
    as(fa, ())
}

trait FunctorLaws {
  def identity[F[_], A](fa: F[A])(implicit F: Functor[F]) = F.map(fa)(a => a) == fa

  def composition[F[_], A, B, C](fa: F[A], f: A => B, g: B => C)(implicit F: Functor[F]) =
    F.map(F.map(fa)(f))(g) == F.map(fa)(f andThen g)
}

object Functor {
  implicit val listFunctor : Functor[List] = new Functor[List] {
    def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)  
  }

  implicit val optionFunctor : Functor[Option] = new Functor[Option] {
    def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)  
  }

  implicit def function1Functor[X] : Functor[X => ?]  = new Functor[X => ?] {
    def map[A, B](fa: X => A)(f: A => B): X => B = fa andThen f
  }

}