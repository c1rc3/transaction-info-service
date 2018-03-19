package circe.ccp.transaction.repository

import circe.ccp.transaction.domain.{MonitoringAddressInfo, Page, PageImpl, Pageable}
import circe.ccp.transaction.util.Jsoning
import com.google.inject.Inject
import com.google.inject.name.Named
import com.twitter.util.Future
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.sort.{SortBuilders, SortOrder}
import ESClient._
/**
 * Created by phg on 3/13/18.
 **/
trait MonitoringAddressRepository {
  def add(monitoringAddressInfo: MonitoringAddressInfo): Future[String]

  def remove(id: String): Future[Boolean]

  def search(address: String, pageable: Pageable): Future[Page[MonitoringAddressInfo]]
}

case class ESMonitoringAddressRepository @Inject()(
  @Named("monitoring-address-es") es: ElasticsearchRepository,
  @Named("monitoring-address-type") typeName: String
) extends MonitoringAddressRepository with Jsoning {

  override def add(info: MonitoringAddressInfo): Future[String] = {
    val id = s"${info.address}-${info.receiver}"
    val currentMillis = System.currentTimeMillis()
    es.index(typeName, id, info.copy(
      id = Some(id),
      createdTime = Some(currentMillis),
      updatedTime = Some(currentMillis),
      isActive = Some(true)
    ).toJsonString).map(_ => id)
  }

  override def remove(id: String): Future[Boolean] = es.delete(typeName, id).map(_ => true)

  override def search(address: String, pageable: Pageable): Future[Page[MonitoringAddressInfo]] = {
    es.search(typeName, QueryBuilders.termQuery("address", address), pageable).map(res => {
      PageImpl(res.getHits.getHits.map(hit => hit.getSourceAsString.asJsonObject[MonitoringAddressInfo]), pageable, res.getHits.totalHits)
    })
  }
}