package nous
package core
package std

trait anyvals {
  implicit val byteEqual: Equal[Byte] = Equal.natural
  implicit val shortEqual: Equal[Short] = Equal.natural
  implicit val intEqual: Equal[Int] = Equal.natural
  implicit val longEqual: Equal[Long] = Equal.natural
  implicit val stringEqual: Equal[String] = Equal.natural
}