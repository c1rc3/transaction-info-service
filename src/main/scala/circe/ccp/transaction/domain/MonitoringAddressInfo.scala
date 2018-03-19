package circe.ccp.transaction.domain

/**
 * Created by phg on 3/12/18.
 **/
case class MonitoringAddressInfo(
  id: String,
  address: String,
  notifyType: String,
  receiver: String,
  createdTime: Option[Long] = None,
  updatedTime: Option[Long] = None,
  isActive: Option[Boolean] = None
)