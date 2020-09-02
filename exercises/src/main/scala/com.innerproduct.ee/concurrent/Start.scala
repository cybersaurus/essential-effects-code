package com.innerproduct.ee.concurrent

import cats.effect._
import scala.annotation.nowarn
import com.innerproduct.ee.debug._

object Start extends IOApp {
  
  @nowarn
  def run(args: List[String]): IO[ExitCode] =
    for {
      fiber <- task.start // <1>
      _ <- IO("line 2").debug()
    } yield ExitCode.Success

  val task: IO[String] =
    IO("hello world!").debug()
}
