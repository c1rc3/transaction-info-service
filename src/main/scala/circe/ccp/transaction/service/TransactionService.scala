package circe.ccp.transaction.service

import circe.ccp.transaction.domain.CoinId.CoinId
import circe.ccp.transaction.domain.NotificationType.NotificationType
import circe.ccp.transaction.domain._
import circe.ccp.transaction.repository.{MonitoringAddressRepository, StringKafkaProducer}
import circe.ccp.transaction.util.{Jsoning, StringUtil}
import com.google.inject.name.Named
import com.twitter.util.Future

/**
 * Created by phg on 3/12/18.
 **/
trait TransactionService[A] {

  def getTxWithId(coinId: CoinId, id: String): Future[Option[A]]

  def getTxWithAddress(address: String, category: Option[String], pageable: Pageable): Future[Page[A]]

  def addMonitoringAddress(address: String, notifyType: NotificationType, receiver: String): Future[String]

  def removeMonitoringAddress(id: String): Future[Boolean]

  def getMonitoringAddress(address: String, pageable: Pageable): Future[Page[MonitoringAddressInfo]]
}

case class TransactionServiceImpl[A](
  kafkaProducer: StringKafkaProducer,
  monitoringAddressRepository: MonitoringAddressRepository,
  @Named("monitoring-topic") topic: String
) extends TransactionService[A] with Jsoning {

  override def getTxWithId(coinId: CoinId, id: String) = ???

  override def getTxWithAddress(address: String, category: Option[String], pageable: Pageable) = ???

  override def addMonitoringAddress(address: String, notifyType: NotificationType, receiver: String) = {
    val currentMillis = System.currentTimeMillis()
    val id = StringUtil.genUniqueId
    kafkaProducer.send(topic, KafkaCommand.CREATE.toString, MonitoringAddressInfo(
      id = id,
      address = address,
      notifyType = notifyType.toString,
      receiver = receiver,
      createdTime = Some(currentMillis),
      updatedTime = Some(currentMillis),
      isActive = Some(true)
    ).toJsonString).map(_ => id)
  }

  override def removeMonitoringAddress(id: String) = monitoringAddressRepository.remove(id)

  override def getMonitoringAddress(address: String, pageable: Pageable) = monitoringAddressRepository.search(address, pageable)
}