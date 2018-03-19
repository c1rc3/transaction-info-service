package circe.ccp.transaction.module

import circe.ccp.transaction.repository._
import circe.ccp.transaction.service.{TransactionService, TransactionServiceImpl}
import circe.ccp.transaction.util.ZConfig
import com.twitter.inject.TwitterModule
import com.typesafe.config.Config

import scala.collection.JavaConversions._

/**
 * Created by phg on 3/12/18.
 **/
object DependencyModule extends TwitterModule {

  override def configure(): Unit = {
    super.configure()
    bind[Array[String]].annotatedWithName("transaction-categories").toInstance(ZConfig.getStringList("transaction.categories").toArray)

    bind[String].annotatedWithName("monitoring-mapping-file").toInstance(ZConfig.getString("monitoring.es.mapping-file"))
    bind[String].annotatedWithName("monitoring-address-type").toInstance(ZConfig.getString("monitoring.es.type-name"))
    bind[String].annotatedWithName("monitoring-kafka-topic").toInstance(ZConfig.getString("monitoring.kafka.producer.topic"))



    bind[Config].annotatedWithName("tx-writer-config").toInstance(ZConfig.getConf("transaction.kafka.consumers.writer"))
    bind[Config].annotatedWithName("monitoring-address-writer-config").toInstance(ZConfig.getConf("monitoring.kafka.consumers.writer"))

    bind[ElasticsearchRepository].annotatedWithName("tx-es").toInstance(esWithConfig(ZConfig.getConf("transaction.es")))
    bind[ElasticsearchRepository].annotatedWithName("monitoring-address-es").toInstance(esWithConfig(ZConfig.getConf("monitoring.es")))

    bind[StringKafkaProducer].annotatedWithName("monitoring-producer").toInstance(StringKafkaProducer(ZConfig.getConf("monitoring.kafka.producer")))

    bind[MonitoringAddressRepository].to[ESMonitoringAddressRepository]
    bind[TransactionRepository].to[ESTransactionRepository]

    bind[TransactionService].to[TransactionServiceImpl]
  }

  private def esWithConfig(config: Config): ElasticsearchRepository = {
    ElasticsearchRepository(
      config.getStringList("servers").toList,
      config.getString("cluster"),
      config.getBoolean("transport-sniff"),
      config.getString("index-name")
    )
  }

}
