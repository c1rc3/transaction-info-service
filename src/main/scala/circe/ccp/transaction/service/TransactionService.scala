package circe.ccp.transaction.service

import circe.ccp.transaction.domain._
import circe.ccp.transaction.repository.{MonitoringAddressRepository, StringKafkaProducer}
import com.twitter.util.Future

/**
 * Created by phg on 3/12/18.
 **/
trait TransactionService {

  def add(transaction: Transaction): Future[Boolean]

  /**
   * Get a transaction with id
   *
   * @return None if not exist
   **/
  def getTxWithId(id: String): Future[Option[Transaction]]

  /**
   * Get transactions that sent from/to ${address}
   *
   * @param address  :
   * @param category : filter by category
   * @param pageable : paging
   * @return Page of Transaction
   *
   **/
  def getTxWithAddress(address: String, category: Option[String], pageable: Pageable): Future[Page[Transaction]]

  def addMonitoringAddress(monitoringAddressInfo: MonitoringAddressInfo): Future[String]

  def removeMonitoringAddress(id: String): Future[Boolean]

  def getMonitoringAddress(address: String, pageable: Pageable): Future[Page[MonitoringAddressInfo]]
}

case class FakedTransactionService() extends TransactionService {

  override def add(transaction: Transaction) = Future(true)

  override def getTxWithId(id: String) = Future(Some(Transaction(
    id = id,
    category = "Food",
    from = "0x087ad24e25a24abf04657112e8eee6e365d698e7",
    to = "0x6f88c11fdd4fa004e5baf03d9372a9dc7ae6ec97",
    amount = 1.0,
    symbol = "ETC",
    note = "2 cup of coffee at Shin",
    txHash = "0x6a99c3a1de4e8404b128a2c8148c4ba7c4e26ecea61a00dcdb5a4b380faf5107",
    timestamp = Some(1520838364704L)
  )))

  override def getTxWithAddress(address: String, category: Option[String], pageable: Pageable) = Future(
    PageImpl(
      Array(Transaction(
        id = "id",
        category = "Food",
        from = "0x087ad24e25a24abf04657112e8eee6e365d698e7",
        to = "0x6f88c11fdd4fa004e5baf03d9372a9dc7ae6ec97",
        amount = 1.0,
        symbol = "ETC",
        note = "2 cup of coffee at Shin",
        txHash = "0x6a99c3a1de4e8404b128a2c8148c4ba7c4e26ecea61a00dcdb5a4b380faf5107",
        timestamp = Some(1520838364704L)
      )), pageable, total = 100
    )
  )

  override def addMonitoringAddress(monitorAddressInfo: MonitoringAddressInfo) = Future("0x087ad24e25a24abf04657112e8eee6e365d698e7-abc-def-ghi-jkl")

  override def removeMonitoringAddress(id: String) = Future(true)

  override def getMonitoringAddress(address: String, pageable: Pageable) = Future(
    PageImpl(
      Array(MonitoringAddressInfo(
        address = "0x087ad24e25a24abf04657112e8eee6e365d698e7",
        notifyType = "push",
        receiverInfo = Map(
          "device-id" -> "abc-def-ghi-jkl",
          "device-name" -> "Alex's iPhone X"
        )
      )), pageable, total = 100
    )
  )
}

case class TransactionServiceImpl(
  kafkaProducer: StringKafkaProducer,
  monitoringAddressRepository: MonitoringAddressRepository
) extends TransactionService {

  override def add(transaction: Transaction) = ???

  override def getTxWithId(id: String) = ???

  override def getTxWithAddress(address: String, category: Option[String], pageable: Pageable) = ???

  override def addMonitoringAddress(info: MonitoringAddressInfo) = monitoringAddressRepository.add(info)

  override def removeMonitoringAddress(id: String) = monitoringAddressRepository.remove(id)

  override def getMonitoringAddress(address: String, pageable: Pageable) = monitoringAddressRepository.search(address, pageable)
}