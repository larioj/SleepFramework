import mesosphere.mesos.util.FrameworkInfo
import org.apache.mesos.MesosSchedulerDriver

/**
  * Created by Jesus E. Larios Murillo on 6/24/16.
  */
class Sleeper {

  def printUsage(): Unit =
    println(
      """
        |Usage:
        |  run  <mesos-master>
      """.stripMargin)


  def main(args: Array[String]): Unit = {
    if (args.length != 1) {
      printUsage()
      System.exit(1)
    }

    val mesosMaster = args(0)
    val framework = FrameworkInfo("SleepFramework")
    val scheduler = new SleepScheduler
    val driver = new MesosSchedulerDriver(scheduler, framework.toProto, mesosMaster)
    driver.run()
  }
}
