package com.innerproduct.ee.contexts

import cats.effect._
import com.innerproduct.ee.debug._

object Blocking extends IOApp {

  val b: Resource[IO, Blocker] = Blocker[IO]

  def run(args: List[String]): IO[ExitCode] =
//    withBlocker(Blocker.liftExecutionContext(scala.concurrent.ExecutionContext.global)).as(ExitCode.Success) // <1>
    b.use(withBlocker).as(ExitCode.Success)


  def withBlocker(blocker: Blocker): IO[Unit] =
    for {
      _ <- IO("on default").debug()
      _ <- blocker.blockOn(IO("on blocker").debug()) // <2>
    } yield ()
}
