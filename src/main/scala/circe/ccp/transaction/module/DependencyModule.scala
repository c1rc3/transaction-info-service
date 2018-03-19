package circe.ccp.transaction.module

import circe.ccp.transaction.repository.{ElasticsearchRepository, StringKafkaProducer}
import circe.ccp.transaction.util.ZConfig
import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import com.typesafe.config.Config

import scala.collection.JavaConversions._

/**
 * Created by phg on 3/12/18.
 **/
object DependencyModule extends TwitterModule {

  override def configure(): Unit = {
    super.configure()
    bind[Array[String]].annotatedWithName("transaction-categories")
      .toInstance(ZConfig.getStringList("transaction.categories").toArray)

    bind[Config].annotatedWithName("tx-writer-config").toInstance(ZConfig.getConf("transaction.kafka.consumers.writer"))
    bind[Config].annotatedWithName("monitoring-address-writer-config").toInstance(ZConfig.getConf("monitoring.kafka.consumers.writer"))

    bind[ElasticsearchRepository].annotatedWithName("tx-es").toInstance(esWithConfig(ZConfig.getConf("transaction")))
    bind[ElasticsearchRepository].annotatedWithName("monitoring-address-es").toInstance(esWithConfig(ZConfig.getConf("monitoring")))

    bind[StringKafkaProducer].annotatedWithName("monitoring-producer").toInstance(StringKafkaProducer(ZConfig.getConf("monitoring.kafka.producer")))
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
