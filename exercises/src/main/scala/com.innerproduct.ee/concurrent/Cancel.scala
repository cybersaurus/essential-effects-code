package com.innerproduct.ee.concurrent

import cats.effect._
import com.innerproduct.ee.debug._

object Cancel extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    for {
      fiber <- task.start // <2>
      _ <- IO("pre-cancel").debug()
      _ <- fiber.cancel.debug()
      _ <- fiber.join.void.debug() // Validates the cancel was called, otherwise will never join cos IO.never doesn't complete (Maybe ???)
      _ <- IO("canceled").debug()
    } yield ExitCode.Success

  val task: IO[Nothing] =
    IO("task").debug() *>
      IO.never // <1>
}

/*
fiber.cancel cancels the IO.never
 */