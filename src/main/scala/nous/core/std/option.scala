package nous
package core
package std

trait option {
  implicit def optionEqual[A](implicit A: Equal[A]): Equal[Option[A]] = new Equal[Option[A]] {
    def equal(x: Option[A], y: Option[A]) = (x, y) match {
      case (Some(x), Some(y)) => A.equal(x, y)
      case (None, None) => true
      case _ => false
    }
  }  
}

object option extends option