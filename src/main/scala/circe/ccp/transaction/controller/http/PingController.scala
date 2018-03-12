package circe.ccp.transaction.controller.http

import circe.ccp.transaction.domain.Response
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

/**
 * Created by phg on 3/12/18.
 **/
class PingController() extends Controller with Response {
  get("/ping") {
    _: Request => response.ok("pong").toFuture.toCCPSuccessResponse
  }
}
