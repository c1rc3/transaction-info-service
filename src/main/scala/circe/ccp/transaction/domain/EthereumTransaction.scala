package circe.ccp.transaction.domain

/**
 * Created by phg on 3/19/18.
 **/
case class EthereumTransaction(
  txHash: String,
  blockHash: String,
  blockNumber: Long,
  from: String,
  gas: Long,
  gasPrice: String,
  hash: String,
  input: String,
  nonce: Int,
  to: String,
  transactionIndex: Int,
  value: String
)

