package nous
package core
package std

trait list {
  implicit def listEqual[A](implicit A: Equal[A]): Equal[List[A]] = new Equal[List[A]] {
    def equal(x: List[A], y: List[A]) = {
      x.size == y.size && {
        x.zip(y).forall { case (xx, yy) => A.equal(xx, yy) }
      }
    }
  }

}

object list extends list