package circe.ccp.transaction.module

import circe.ccp.transaction.service.{FakedTransactionService, TransactionService}
import circe.ccp.transaction.util.ZConfig
import com.twitter.inject.TwitterModule

/**
 * Created by phg on 3/12/18.
 **/
object DependencyModule extends TwitterModule {

  override def configure(): Unit = {
    super.configure()
    bind[Array[String]].annotatedWithName("transaction-categories")
      .toInstance(ZConfig.getStringList("transaction.categories").toArray)

    bind[TransactionService].to[FakedTransactionService]
  }

}
