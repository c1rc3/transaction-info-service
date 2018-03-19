package circe.ccp.transaction.service

import circe.ccp.transaction.domain.CoinId.CoinId
import circe.ccp.transaction.domain.NotificationType.NotificationType
import circe.ccp.transaction.domain._
import circe.ccp.transaction.repository.{MonitoringAddressRepository, StringKafkaProducer, TransactionRepository}
import circe.ccp.transaction.util.{Jsoning, StringUtil}
import com.google.inject.Inject
import com.google.inject.name.Named
import com.twitter.util.Future

/**
 * Created by phg on 3/12/18.
 **/
trait TransactionService {

  def getTxWithId[A: Manifest](coinId: CoinId, id: String): Future[Option[A]]

  def getTxWithAddress[A: Manifest](coinId: CoinId, address: String, category: Option[String], pageable: Pageable): Future[Page[A]]

  def addMonitoringAddress(address: String, notifyType: NotificationType, receiver: String): Future[String]

  def removeMonitoringAddress(id: String): Future[Boolean]

  def getMonitoringAddress(address: String, pageable: Pageable): Future[Page[MonitoringAddressInfo]]
}

case class TransactionServiceImpl @Inject()(
  monitoringAddressRepository: MonitoringAddressRepository,
  @Named("monitoring-producer") kafkaProducer: StringKafkaProducer,
  @Named("monitoring-kafka-topic") topic: String,
  transactionRepository: TransactionRepository
) extends TransactionService with Jsoning {

  override def getTxWithId[A: Manifest](coinId: CoinId, id: String) = transactionRepository.getTx(coinId.toString, id).map(_.map(_.asJsonObject[A]))

  override def getTxWithAddress[A: Manifest](coinId: CoinId, address: String, category: Option[String], pageable: Pageable) = {
    transactionRepository.search(coinId.toString, Some(address), Some(address), pageable).map(page => {
      PageImpl(page.content.map(_.asJsonObject[A]), pageable, page.totalElement)
    })
  }

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