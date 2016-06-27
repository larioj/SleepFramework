import org.apache.mesos.MesosSchedulerDriver
import org.apache.mesos.Protos.{FrameworkID, FrameworkInfo}

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

    val name = "SleepFramework"
    val user = "" // take the default
    val checkpointing = false
    val timeout = 60.0
    val id = FrameworkID.newBuilder.setValue(name).build()

    val scheduler = new SleepScheduler
    val framework = FrameworkInfo.newBuilder
      .setName(name)
      .setFailoverTimeout(timeout)
      .setCheckpoint(checkpointing)
      .setUser(user)
      .setId(id)
      .build()
    val mesosMaster = args(0)

    val driver = new MesosSchedulerDriver(scheduler, framework, mesosMaster)

    driver.run()
  }
}
