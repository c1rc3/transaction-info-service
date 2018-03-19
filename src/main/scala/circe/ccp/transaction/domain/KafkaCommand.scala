package circe.ccp.transaction.domain

/**
 * Created by phg on 3/19/18.
 **/
object KafkaCommand extends Enumeration {
  type kafkaCommand = Value

  val CREATE = Value(1, "create")
  val UPDATE = Value(2, "update")
  val DELETE = Value(3, "delete")

  def forName(s: String) = values.find(_.toString == Option(s).getOrElse("").toLowerCase)
}
