package circe.ccp.transaction.consumer

import circe.ccp.transaction.domain.KafkaTransactionInfo
import circe.ccp.transaction.repository.StringKafkaConsumer
import circe.ccp.transaction.util.Jsoning
import com.twitter.inject.Logging
import com.typesafe.config.Config
import org.apache.kafka.clients.consumer.ConsumerRecord

/**
 * Created by phg on 3/19/18.
 **/
abstract class TransactionConsumer[A](
  consumerConfig: Config
) extends StringKafkaConsumer(consumerConfig) with Jsoning with Logging {

  override def consume(record: ConsumerRecord[String, String]): Unit = {
    handle(record.value.asJsonObject[KafkaTransactionInfo[Map[String, String]]])
  }

  protected def handle(data: KafkaTransactionInfo[Map[String, String]])
}
