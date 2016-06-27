import org.apache.mesos.MesosSchedulerDriver
import org.apache.mesos.Protos.{FrameworkID, FrameworkInfo}

/**
  * Created by Jesus E. Larios Murillo on 6/24/16.
  */
object Sleeper {

  def main(args: Array[String]): Unit = {
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
    val mesosMaster = "127.0.1.1:5050"

    val driver = new MesosSchedulerDriver(scheduler, framework, mesosMaster)

    driver.run()
  }
}
