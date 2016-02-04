# nous
Nous ([meaning](http://en.wikipedia.org/wiki/Nous) "mind", "intellect", or "understanding") is a
reference implementation for common functional programming type classes and instances.
Nous is simply a learning project; its only purpose is to provide a shared space
where I can experiment and gain a better understanding of common FP constructs such as Functors,
Monoids, and various types of Monads by implementing them by working through various examples and tutorials, as well as experimenting with some of the existing FP libraries for Scala, such as [Structures](https://github.com/mpilquist/Strutures)
and [cats](https://github.com/non/cats).

Currently, the code contained in Nous is the primarily the result of working through [Michael Pilquist's](https://github.com/mpilquist) excellent [Functional Structures in Scala](https://www.youtube.com/playlist?list=PLFrwDVdSrYE6dy14XCmUtRAJuhCxuzJp0) lecture series, though neither Michael (creator of Structures and other projects) nor any contributor to the projects he is involved with endorses or is in any way affiliated with Nous.  Nous is simply a space where I can work through various tutorials and examples and (through the Nous wiki) document and share what I have learned.  Every attempt to properly attribute the source of the code and information found in Nous will be made; if any part of Nous is found to lack the proper citations or attribution, please report this as an issue so that it can be rectified immediately.

## Building nous

Nous is built using [SBT](http://www.scala-sbt.org/).  To build
nous and its example programs, run: 

    sbt clean compile test


