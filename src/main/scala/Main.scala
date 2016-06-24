import mesosphere.mesos.util.FrameworkInfo
import org.apache.mesos.MesosSchedulerDriver


/**
 * @author Tobi Knaup
 */

object Main extends App {

  val framework = FrameworkInfo("SleepFramework")

  val scheduler = new SleepScheduler

  val driver = new MesosSchedulerDriver(scheduler, framework.toProto, "127.0.1.1:5050")
  driver.run()
}
