import java.util

import org.apache.mesos.Protos._
import org.apache.mesos._

import scala.collection.JavaConverters._

/**
  * Created by Jesus E. Larios Murillo on 6/24/16.
  */
class SleepScheduler extends Scheduler {
  override def offerRescinded(driver: SchedulerDriver, offerId: OfferID): Unit =
    println(s"offerRecinded: Offer ${offerId.getValue} has been rescinded")

  override def disconnected(driver: SchedulerDriver): Unit =
    println("disconnected: Disconnected from the mesos master")

  override def reregistered(driver: SchedulerDriver, masterInfo: MasterInfo): Unit =
    println("reregistered: Reregistered with the mesos master")

  override def slaveLost(driver: SchedulerDriver, slaveId: SlaveID): Unit =
    println(s"slaveLost: Slave ${slaveId.getValue} lost :(")

  override def error(driver: SchedulerDriver, message: String): Unit =
    println(s"error: Error: $message")

  override def statusUpdate(driver: SchedulerDriver, status: TaskStatus): Unit =
    println(s"statusUpdate: Status update: Task ${status.getTaskId.getValue} is in state ${status.getState}")

  override def frameworkMessage(driver: SchedulerDriver, executorId: ExecutorID, slaveId: SlaveID, data: Array[Byte]): Unit =
    println(s"frameworkMessage: Received message from executor ${executorId.getValue} at slave ${slaveId.getValue} with contents ${data}")

  override def resourceOffers(driver: SchedulerDriver, offers: util.List[Offer]): Unit = {
    println(s"ResourceOffers: got some offers!")
    for (offer <- offers.asScala) {
      println(s"\tresource offer $offer")

      // Setup
      val command = CommandInfo.newBuilder.setValue("sleep 10 && echo awake")
      val id = TaskID.newBuilder.setValue("task" + System.currentTimeMillis())
      val name = s"SleepTask-${id.getValue}"
      val slaveId = offer.getSlaveId
      val cpu = Resource.newBuilder.setName("cpus").setType(Value.Type.SCALAR).setScalar(Value.Scalar.newBuilder.setValue(1.0))
      val mem = Resource.newBuilder.setName("mem").setType(Value.Type.SCALAR).setScalar(Value.Scalar.newBuilder.setValue(32))

      // Fill taskInfo
      val task = TaskInfo.newBuilder
        .setCommand(command)
        .setName(name)
        .setTaskId(id)
        .setSlaveId(slaveId)
        .addResources(cpu)
        .addResources(mem)

      // Launch the task
      driver.launchTasks(List(offer.getId).asJava, List(task.build).asJava)
    }
  }

  override def registered(driver: SchedulerDriver, frameworkId: FrameworkID, masterInfo: MasterInfo): Unit =
    println(s"registered: Registered with mesos master ${masterInfo.getId} at ip ${masterInfo.getIp} with port ${masterInfo.getPort}")

  override def executorLost(driver: SchedulerDriver, executorId: ExecutorID, slaveId: SlaveID, status: Int): Unit =
    println(s"executorLost: We lost the executor ${executorId.getValue} at slave ${slaveId.getValue}!!!")
}
