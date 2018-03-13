package circe.ccp.transaction.controller.http

import circe.ccp.transaction.domain._
import circe.ccp.transaction.service.TransactionService
import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

/**
 * Created by phg on 3/12/18.
 **/
class TransactionController @Inject()(txService: TransactionService) extends Controller {

  post("/transactions") {
    transaction: Transaction => txService.add(transaction).map(SuccessResponse)
  }

  get("/transactions/:id") {
    req: Request => {
      txService.getTxWithId(req.params("id")).map({
        case Some(tx) => SuccessResponse(Some(tx))
        case _ => FailureResponse(FailureReason.NotFound)
      })
    }
  }

  get("/transactions") {
    req: GetTransactionRequest => {
      txService.getTxWithAddress(req.address, req.category, req.getPageable).map(PagingResponse)
    }
  }

  post("/transactions/monitoring") {
    req: MonitoringAddressInfo => txService.addMonitoringAddress(req).map(SuccessResponse)
  }

  delete("/transactions/:address/monitoring") {
    req: Request => txService.removeMonitoringAddress(req.params("address")).map(SuccessResponse)
  }

  get("/transactions/monitoring") {
    req: GetMonitoringAddressRequest => {
      txService.getMonitoringAddress(req.address, req.getPageable).map(PagingResponse)
    }
  }
}
