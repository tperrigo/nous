import simulacrum._

@typeclass trait Applicative[F[_]] {

  // lift a value into a context (into an "effect system")
  // using type constructors to model an "effect": something visible through all type signatures by the presence of type constructor F
  // (e.g, Option has the effect of having a value or not having a value; List has the effect of having no values or many values)
  def pure[A](a : A): F[A]

  // similar to map, but takes a function which exists in the context (effect system)
  def apply[A, B](fa: F[A])(ff: F[A => B]): F[B]
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

    def apply[A, B](fa: List[A])(ff: List[A => B]): List[B] = 
      (fa zip ff).map { case (a, f) => f(a) }
  }
}
