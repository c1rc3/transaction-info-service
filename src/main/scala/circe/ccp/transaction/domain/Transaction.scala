package circe.ccp.transaction.domain

/**
 * Created by phg on 3/12/18.
 **/
case class Transaction(
  id: String,
  category: String,
  from: WalletAddress,
  to: WalletAddress,
  amount: CoinAmount,
  symbol: CoinSymbol,
  note: String,
  txHash: String,
  timestamp: Option[Long] = None
)