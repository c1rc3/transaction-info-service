package circe.ccp.transaction.domain

/**
 * Created by phg on 3/19/18.
 **/
object NotificationType extends Enumeration {
  type NotificationType = Value

  val PUSH_IOS = Value(1, "PUSH_IOS")
  val PUSH_ANDROID = Value(2, "PUSH_ANDROID")
  val EMAIL = Value(3, "EMAIL")
  val SMS = Value(4, "SMS")
  val IN_APP = Value(5, "IN_APP")

  def forName(s: String): Option[Value] = values.find(_.toString == Option(s).getOrElse("").toUpperCase())
}
