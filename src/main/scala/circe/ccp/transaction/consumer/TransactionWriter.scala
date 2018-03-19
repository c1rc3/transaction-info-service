package circe.ccp.transaction.consumer

import circe.ccp.transaction.domain.{CoinId, KafkaTransactionInfo}
import circe.ccp.transaction.repository.TransactionRepository
import com.google.inject.Inject
import com.google.inject.name.Named
import com.typesafe.config.Config

/**
 * Created by phg on 3/19/18.
 **/
case class TransactionWriter @Inject()(
  @Named("tx-writer-config") consumeConfig: Config,
  txRepo: TransactionRepository
) extends TransactionConsumer(consumeConfig) {

  override protected def handle(data: KafkaTransactionInfo[Map[String, String]]): Unit = {
    CoinId.forName(data.coinId) match {
      case Some(coinId) => txRepo.add(coinId.toString, data.id, data.data)
      case _ => warn(s"Unsupported CoinId ${data.coinId}")
    }
  }
}
