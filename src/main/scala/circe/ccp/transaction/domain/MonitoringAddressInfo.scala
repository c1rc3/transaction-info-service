package circe.ccp.transaction.domain

/**
 * Created by phg on 3/12/18.
 **/
case class MonitoringAddressInfo(
  address: String,
  notifyType: String,
  receiver: String,
  id: Option[String] = None,
  createdTime: Option[Long] = None,
  updatedTime: Option[Long] = None,
  isActive: Option[Boolean] = None
)
