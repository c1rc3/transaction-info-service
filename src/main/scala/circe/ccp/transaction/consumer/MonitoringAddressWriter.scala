package circe.ccp.transaction.consumer

import circe.ccp.transaction.domain.MonitoringAddressInfo
import circe.ccp.transaction.repository.MonitoringAddressRepository
import com.google.inject.Inject
import com.google.inject.name.Named
import com.typesafe.config.Config

/**
 * Created by phg on 3/19/18.
 **/
case class MonitoringAddressWriter @Inject()(
  @Named("monitoring-address-writer-config") config: Config,
  repo: MonitoringAddressRepository
) extends MonitoringAddressConsumer(config) {

  override def handleCreate(data: MonitoringAddressInfo): Unit = repo.upsert(data)

  override def handleUpdate(data: MonitoringAddressInfo): Unit = repo.upsert(data)

  override def handleDelete(id: String): Unit = repo.remove(id)
}
