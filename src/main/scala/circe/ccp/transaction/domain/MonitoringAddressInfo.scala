package circe.ccp.transaction.domain

/**
 * Created by phg on 3/12/18.
 **/
case class MonitoringAddressInfo(
  address: String,
  notifyType: String,
  receiverInfo: Map[String, String]
)
