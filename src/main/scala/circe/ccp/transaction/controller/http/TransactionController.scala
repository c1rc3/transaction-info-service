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

  get("/transactions/:id") {
    req: Request => {
      CoinId.forName(req.params("coin_id")) match {
        case Some(coinId) => txService.getTxWithId(coinId, req.params("id")).map({
          case Some(tx) => SuccessResponse(Some(tx))
          case _ => FailureResponse(FailureReason.NotFound)
        })
        case _ => response.ok(FailureResponse(FailureReason.InvalidCoinId))
      }
    }
  }

  get("/transactions") {
    req: GetTransactionRequest => {
      CoinId.forName(req.coinId) match {
        case Some(coinId) => txService.getTxWithAddress(coinId, req.address, req.category, req.getPageable).map(PagingResponse)
        case _ => response.ok(FailureResponse(FailureReason.InvalidCoinId))
      }

    }
  }

  post("/transactions/monitoring") {
    req: AddMonitorAddressRequest =>
      NotificationType.forName(req.notifyType) match {
        case Some(notifyType) => txService.addMonitoringAddress(req.address, notifyType, req.receiver).map(SuccessResponse)
        case _ => response.ok(FailureResponse(FailureReason.InvalidNotifyType))
      }
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
