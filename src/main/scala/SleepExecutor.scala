import org.apache.mesos.{Executor, ExecutorDriver, MesosExecutorDriver}
import org.apache.mesos.Protos.{TaskState, TaskStatus, _}

/**
  * Created by mesosphere on 6/30/16.
  */
object SleepExecutor extends Executor {
  override def shutdown(driver: ExecutorDriver): Unit = {
    println("Shutdown: starting")
  }

  override def disconnected(driver: ExecutorDriver): Unit = println("Disconnected: ")

  override def killTask(driver: ExecutorDriver, taskId: TaskID): Unit = println("Kill Task: ")

  override def reregistered(driver: ExecutorDriver, slaveInfo: SlaveInfo): Unit = println("Reregistered: ")

  override def error(driver: ExecutorDriver, message: String): Unit = println("Error: in error mode")

  override def frameworkMessage(driver: ExecutorDriver, data: Array[Byte]): Unit = println("Framework Message: ")

  override def registered(driver: ExecutorDriver, executorInfo: ExecutorInfo, frameworkInfo: FrameworkInfo, slaveInfo: SlaveInfo): Unit = println("Registered: ")

  override def launchTask(driver: ExecutorDriver, task: TaskInfo): Unit = {
    print(
      s"""
         |Launch Task: ${task.getTaskId.getValue}
      """.stripMargin)

    val thread = new Thread {
      override def run(): Unit = {
        driver.sendStatusUpdate(TaskStatus.newBuilder
          .setTaskId(task.getTaskId)
          .setState(TaskState.TASK_RUNNING).build())

        println("\t Sleeping for 1 second... zzz")
        //Thread.sleep(1000)

        driver.sendStatusUpdate(TaskStatus.newBuilder
          .setTaskId(task.getTaskId)
          .setState(TaskState.TASK_FINISHED)
          .build())

      }
    }

    thread.start()
  }

  def main(args: Array[String]): Unit = {
    val driver = new MesosExecutorDriver(SleepExecutor)
    driver.run()
  }
}
