package circe.ccp.transaction.module

import javax.inject.Singleton

import circe.ccp.transaction.repository.ElasticsearchRepository
import circe.ccp.transaction.util.ZConfig
import com.google.inject.Provides
import com.google.inject.name.Named
import com.twitter.inject.TwitterModule
import com.typesafe.config.Config
import org.nutz.ssdb4j.SSDBs
import org.nutz.ssdb4j.spi.SSDB

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
    //    bind[TransactionService]
  }

  @Provides
  @Singleton
  @Named("tx-es")
  def providesESClient: ElasticsearchRepository = {
    val config = ZConfig.getConf("transaction")
    ElasticsearchRepository(
      config.getStringList("es.servers").toList,
      config.getString("es.cluster"),
      config.getBoolean("es.transport-sniff"),
      config.getString("es.index-name")
    )
  }

  @Provides
  @Singleton
  def providesSSDBClient:SSDB = {
    val config = ZConfig.getConf("monitor-address.ssdb")
    SSDBs.pool(
      config.getString("host"),
      config.getInt("port"),
      config.getInt("timeoutInMs"), null)
  }
}
