package nous
package core
package std

trait stream {
  implicit def streamEqual[A](implicit A: Equal[A]): Equal[Stream[A]] = new Equal[Stream[A]] {
    def equal(x: Stream[A], y: Stream[A]) = {
      x.size == y.size && {
        x.zip(y).forall { case (xx, yy) => A.equal(xx, yy) }
      }
    }
  }

}

object stream extends stream