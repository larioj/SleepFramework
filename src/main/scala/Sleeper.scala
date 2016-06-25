import org.apache.mesos.MesosSchedulerDriver
import org.apache.mesos.Protos.FrameworkInfo

import scala.concurrent.duration._

/**
  * Created by Jesus E. Larios Murillo on 6/24/16.
  */
object Sleeper {
  def usage(): Unit = {
    println("Usage: run <mesosMaster>")
    System.exit(1)
  }

  def main(args: Array[String]): Unit = {
    if (args.length != 1) usage()
    val scheduler = new SleepScheduler
    val framework = FrameworkInfo.newBuilder
      .setName("SleepFramework")
      .setFailoverTimeout(60.seconds.toMillis)
      .setCheckpoint(false)
      .setUser("") // take the default
      .build()
    val mesosMaster = args(0)
    val driver = new MesosSchedulerDriver(scheduler, framework, mesosMaster)
    driver.run()
  }
}
