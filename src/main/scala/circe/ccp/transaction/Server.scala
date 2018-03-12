package circe.ccp.transaction


import circe.ccp.controller.http.exception.CommonExceptionMapper
import circe.ccp.module.DependencyModule
import circe.ccp.transaction.controller.http.{CategoryController, PingController, TransactionController}
import circe.ccp.transaction.util.ZConfig
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter

/**
 * Created by SangDang on 9/8/
 **/
object MainApp extends Server

class Server extends HttpServer {

  override protected def defaultFinatraHttpPort: String = ZConfig.getString("server.http.port", ":8080")

  override protected def disableAdminHttpServer: Boolean = ZConfig.getBoolean("server.admin.disable", default = true)

  override val modules = Seq(DependencyModule)

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.filter[CommonFilters]
      .exceptionMapper[CommonExceptionMapper]
      .add[PingController]
      .add[CategoryController]
      .add[TransactionController]
  }
}
