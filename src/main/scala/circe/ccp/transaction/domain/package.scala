package circe.ccp.transaction

/**
 * Created by phg on 3/12/18.
 **/
package object domain {
  type WalletAddress = String
  type CoinSymbol = String
  type CoinAmount = Double
  type CoinPrice = Double

  type OptTransaction = Option[Transaction]
}
