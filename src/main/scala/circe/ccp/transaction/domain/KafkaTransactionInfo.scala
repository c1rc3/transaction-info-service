package circe.ccp.transaction.domain

/**
 * Created by phg on 3/19/18.
 **/
case class KafkaTransactionInfo[A](
  id: String,
  coinId: String,
  from: Array[String],
  to: Array[String],
  data: A
)

object CoinId extends Enumeration {
  type CoinId = Value

  val BITCOIN = Value(1, "bitcoin")
  val ETHEREUM = Value(2, "ethereum")

  def forName(s: String) = values.find(_.toString == s)
}