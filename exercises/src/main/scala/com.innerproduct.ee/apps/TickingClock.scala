package com.innerproduct.ee.apps

import cats.effect._
import scala.concurrent.duration._
import com.innerproduct.ee.debug._

object TickingClock extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    tickingClock
      .guaranteeCase(_ => IO("Stopped!").debug().void) // <2>
      .as(ExitCode.Success)

  val tickingClock: IO[Unit] =
    IO {
      while(true) {
        Thread.sleep(1000)
        println(s"Time: ${System.currentTimeMillis()}")
      }
    }

  // Alternative using IO.sleep and recursion.
  val tickingClockR: IO[Unit] =
    for {
      _ <- IO.sleep(1.second)
      _ <- IO(s"Time: ${System.currentTimeMillis()}").debug()
      _ <- tickingClockR
    } yield ()
}
