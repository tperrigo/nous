package nous
package laws

import nous.core._
import nous.core.Equal.ops._

trait EqualLaws[A] {

  implicit val typeClass: Equal[A]

  def equalReflexive(a: A) = typeClass.equal(a, a) =?= true
}

object EqualLaws {
  def apply[A: Equal]: EqualLaws[A] = new EqualLaws[A] {
    val typeClass = Equal[A]
  }
}
