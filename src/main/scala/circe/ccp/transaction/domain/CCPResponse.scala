package circe.ccp.transaction.domain

import circe.ccp.transaction.domain.CCPResponseFailReason.CCPResponseFailReason
import com.twitter.util.Future

/**
 * Created by phg on 3/8/18.
 **/
trait CCPResponse {
  val code: Int
  val msg: String
  val data: Option[Any]
}

case class StandardResponse(code: Int, msg: String, data: Option[Any] = None) extends CCPResponse

case class SuccessCCPResponse(data: Option[Any] = None) extends CCPResponse {
  override val code: Int = 1
  override val msg: String = "OK"
}

case class FailureCCPResponse(reason: CCPResponseFailReason) extends CCPResponse {
  override val code: Int = reason.id
  override val msg: String = reason.toString
  override val data: Option[Any] = None
}

object CCPResponseFailReason extends Enumeration {
  type CCPResponseFailReason = Value

  val NotFound = Value(-404, "Not found")
}

trait Response {

  implicit class FutureLike(any: Future[Any]) {

    def toCCPSuccessResponse: Future[CCPResponse] = any.map {
      case Some(data) => SuccessCCPResponse(Option(data))
      case _ => FailureCCPResponse(CCPResponseFailReason.NotFound)
    }

    def toCCPFailureResponse(reason: CCPResponseFailReason): Future[FailureCCPResponse] = any.map(_ => FailureCCPResponse(reason))
  }

}