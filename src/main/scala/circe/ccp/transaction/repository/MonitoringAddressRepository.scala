package circe.ccp.transaction.repository

import circe.ccp.transaction.domain.{MonitoringAddressInfo, Page, PageImpl, Pageable}
import circe.ccp.transaction.util.Jsoning
import com.google.inject.Inject
import com.google.inject.name.Named
import com.twitter.util.Future
import org.elasticsearch.index.query.QueryBuilders

/**
 * Created by phg on 3/13/18.
 **/
trait MonitoringAddressRepository {
  def upsert(info: MonitoringAddressInfo): Future[String]

  def remove(id: String): Future[Boolean]

  def search(address: String, pageable: Pageable): Future[Page[MonitoringAddressInfo]]
}

case class ESMonitoringAddressRepository @Inject()(
  @Named("monitoring-address-es") es: ElasticsearchRepository,
  @Named("monitoring-address-type") typeName: String,
  @Named("monitoring-mapping-file") filePath: String
) extends MonitoringAddressRepository with Elasticsearchable {

  initIndexFromJsonFile(filePath)

  override def upsert(info: MonitoringAddressInfo): Future[String] = {
    es.index(typeName, info.id, info.toJsonString).map(_ => info.id)
  }

  override def remove(id: String): Future[Boolean] = es.delete(typeName, id).map(_ => true)

  override def search(address: String, pageable: Pageable): Future[Page[MonitoringAddressInfo]] = {
    es.search(typeName, QueryBuilders.termQuery("address", address), pageable).map(res => {
      PageImpl(res.getHits.getHits.map(hit => hit.getSourceAsString.asJsonObject[MonitoringAddressInfo]), pageable, res.getHits.totalHits)
    })
  }
}