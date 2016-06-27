import org.apache.mesos.MesosSchedulerDriver
import org.apache.mesos.Protos.{FrameworkID, FrameworkInfo}

import scala.concurrent.Future

/**
  * Created by Jesus E. Larios Murillo on 6/24/16.
  */
object Sleeper {

  def main(args: Array[String]): Unit = {
    val name = "SleepFramework 3"
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
    val mesosMaster = "192.168.65.90:5050"

    val driver = new MesosSchedulerDriver(scheduler, framework, mesosMaster)

    Future {
      driver.run()
    }

    while (System.in.read() != '\n'.toInt) {
      Thread.sleep(1000)
    }

    scheduler.shutdown {
      driver.stop()
    }

    sys.exit(0)
  }
}
