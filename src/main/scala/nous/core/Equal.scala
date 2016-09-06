/**
 * Equals type class (code is directly from structures.Equal, but without the contravariantInstance.
 * Added to nous to create a minimal set of type classes for educational purposes).
 */
package nous
package core

import simulacrum.{ typeclass, op }

@typeclass trait Equal[A] {

  @op("===")
  def equal(x: A, y: A): Boolean

  @op("=!=")
  final def notEqual(x: A, y: A): Boolean = !equal(x, y)
}

object Equal {

  def natural[A]: Equal[A] = new Equal[A] {
    def equal(x: A, y: A) = x == y
  }

  def instance[A](f: (A, A) => Boolean): Equal[A] = new Equal[A] {
    def equal(x: A, y: A) = f(x, y)
  }

}
