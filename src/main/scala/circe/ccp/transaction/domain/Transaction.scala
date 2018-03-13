package circe.ccp.transaction.domain

/**
 * Created by phg on 3/12/18.
 **/
case class Transaction(
  id: String,
  symbol: String,
  txHash: String,
  detail: Array[TransactionDetail],
  timestamp: Option[Long] = None
)

case class TransactionDetail(
  from: String,
  to: String,
  amount: Double
)