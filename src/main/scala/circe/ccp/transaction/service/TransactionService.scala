package circe.ccp.transaction.service

import circe.ccp.transaction.domain.CoinId.CoinId
import circe.ccp.transaction.domain._
import circe.ccp.transaction.repository.{MonitoringAddressRepository, StringKafkaProducer}
import com.twitter.util.Future

/**
 * Created by phg on 3/12/18.
 **/
trait TransactionService[A] {

  def getTxWithId(coinId: CoinId, id: String): Future[Option[A]]

  def getTxWithAddress(address: String, category: Option[String], pageable: Pageable): Future[Page[A]]

  def addMonitoringAddress(monitoringAddressInfo: MonitoringAddressInfo): Future[String]

  def removeMonitoringAddress(id: String): Future[Boolean]

  def getMonitoringAddress(address: String, pageable: Pageable): Future[Page[MonitoringAddressInfo]]
}

case class TransactionServiceImpl[A](
  kafkaProducer: StringKafkaProducer,
  monitoringAddressRepository: MonitoringAddressRepository
) extends TransactionService[A] {

  override def getTxWithId(coinId: CoinId, id: String) = ???

  override def getTxWithAddress(address: String, category: Option[String], pageable: Pageable) = ???

  override def addMonitoringAddress(info: MonitoringAddressInfo) = monitoringAddressRepository.add(info)

  override def removeMonitoringAddress(id: String) = monitoringAddressRepository.remove(id)

  override def getMonitoringAddress(address: String, pageable: Pageable) = monitoringAddressRepository.search(address, pageable)
}