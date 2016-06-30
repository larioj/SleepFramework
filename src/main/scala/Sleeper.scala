import org.apache.mesos.MesosSchedulerDriver
import org.apache.mesos.Protos._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Jesus E. Larios Murillo on 6/24/16.
  */
object Sleeper {

  def main(args: Array[String]): Unit = {
    val name = "SleepFramework " + System.currentTimeMillis()
    val user = "" // take the default
    val checkpointing = false
    val timeout = 60.0
    val id = FrameworkID.newBuilder.setValue(name).build()

    val executorCommand = CommandInfo.newBuilder
      .setValue("java -cp /home/vagrant/SleepFramework/target/scala-2.11/sleepframework_2.11-1.0.jar SleepExecutor")
      .build()
    val executorId = ExecutorID.newBuilder.setValue("SleepExecutor-" + System.currentTimeMillis())
    val executorName = "Sleep Executor"
    val source = "java"


    val executor = ExecutorInfo.newBuilder
      .setCommand(executorCommand)
      .setExecutorId(executorId)
      .setName(executorName)
      .setSource(source)
      .build()

    val scheduler = new SleepScheduler(executor)
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
