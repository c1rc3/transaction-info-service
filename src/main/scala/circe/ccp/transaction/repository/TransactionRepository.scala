package circe.ccp.transaction.repository

import circe.ccp.transaction.domain.{Page, PageImpl, Pageable}
import com.google.inject.name.Named
import com.twitter.util.Future
import org.elasticsearch.index.query.QueryBuilders

/**
 * Created by phg on 3/13/18.
 **/
trait TransactionRepository {

  def add(coinId: String, id: String, data: Map[String, String]): Future[Boolean]

  def search(
    coinId: String,
    sender: Option[String] = None,
    receiver: Option[String] = None,
    pageable: Pageable
  ): Future[Page[Map[String, String]]]
}

case class ESTransactionRepository(
  @Named("tx-es") es: ElasticsearchRepository
) extends TransactionRepository with Elasticsearchable {

  override def add(coinId: String, id: String, data: Map[String, String]) = {
    es.index(coinId, id, data.toJsonString).map(_ => true)
  }

  override def search(coinId: String, sender: Option[String], receiver: Option[String], pageable: Pageable) = {
    es.search(coinId, QueryBuilders.boolQuery
      .mustTerms("senders", sender)
      .mustTerms("receivers", receiver),
      pageable
    ).map(res => PageImpl(res, pageable, res.getHits.getTotalHits))
  }
}