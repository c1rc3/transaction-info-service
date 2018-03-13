package circe.ccp.transaction.repository

import circe.ccp.transaction.domain.{Page, Pageable, Transaction}
import com.twitter.util.Future

/**
 * Created by phg on 3/13/18.
 **/
trait TransactionRepository {

  def add(transaction: Transaction): Future[Boolean]

  def search(
    coinId: String,
    sender: Option[String] = None,
    receiver: Option[String] = None,
    pageable: Pageable
  ): Future[Page[Transaction]]
}
