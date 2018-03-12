package circe.ccp.transaction.controller.http

import circe.ccp.transaction.domain.{GetTransactionRequest, Response, Transaction}
import circe.ccp.transaction.service.TransactionService
import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

/**
 * Created by phg on 3/12/18.
 **/
class TransactionController @Inject()(txService: TransactionService) extends Controller with Response {

  post("/transactions") {
    transaction: Transaction => txService.add(transaction).toCCPSuccessResponse
  }

  get("/transactions/:id") {
    req: Request => txService.getTxWithId(req.params("id")).toCCPSuccessResponse
  }

  get("/transactions") {
    req: GetTransactionRequest => txService.getTxWithAddress(req.address, req.category, req.getPageable, req.getSorts)
  }
}
