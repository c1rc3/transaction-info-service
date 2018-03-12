package circe.ccp.transaction.controller.http

import circe.ccp.transaction.domain.Response
import com.google.inject.Inject
import com.google.inject.name.Named
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

/**
 * Created by phg on 3/12/18.
 **/
class CategoryController @Inject()(
  @Named("transaction-categories") categories: Array[String]
) extends Controller with Response {
  get("/categories") {
    _: Request => response.ok(categories).toFuture.toCCPSuccessResponse
  }
}
