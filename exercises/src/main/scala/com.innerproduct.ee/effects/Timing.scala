package com.innerproduct.ee.effects

import scala.concurrent.duration._

object Timing extends App {
  val clock: MyIO[Long] =
    MyIO(() => System.currentTimeMillis())

  def time[A](action: MyIO[A]): MyIO[(FiniteDuration, A)] =
    for {
      start <- clock
      result <- action
      end <- clock
    } yield ((end-start).millis, result)

  val timedHello = Timing.time(Printing.hello)

  timedHello.unsafeRun() match {
    case (duration, _) => println(s"'hello' took $duration")
  }
}
