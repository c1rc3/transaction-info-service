package circe.ccp.transaction.consumer

import circe.ccp.transaction.domain.{KafkaCommand, MonitoringAddressInfo}
import circe.ccp.transaction.repository.StringKafkaConsumer
import circe.ccp.transaction.util.Jsoning
import com.typesafe.config.Config
import org.apache.kafka.clients.consumer.ConsumerRecord

/**
 * Created by phg on 3/19/18.
 **/
abstract class MonitoringAddressConsumer(
  config: Config
) extends StringKafkaConsumer(config) with Jsoning {

  override def consume(record: ConsumerRecord[String, String]): Unit = {
    KafkaCommand.forName(record.key) match {
      case Some(cmd) => cmd match {
        case KafkaCommand.CREATE => handleCreate(record.value.asJsonObject[MonitoringAddressInfo])
        case KafkaCommand.UPDATE => handleUpdate(record.value.asJsonObject[MonitoringAddressInfo])
        case KafkaCommand.DELETE => handleDelete(record.value)
      }
      case _ => warn(s"Unsupported command ${record.key}")
    }
  }

  protected def handleCreate(data: MonitoringAddressInfo) = {}

  protected def handleUpdate(data: MonitoringAddressInfo) = {}

  protected def handleDelete(id: String) = {}
}
