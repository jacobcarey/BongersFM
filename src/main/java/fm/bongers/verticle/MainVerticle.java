package fm.bongers.verticle;

import fm.bongers.infrastructure.Config;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) {

    String port = Config.getInstance().getPort();

    HttpServer server = vertx.createHttpServer();

    server.requestHandler(
        req -> {
          req.response().end("OK");
        });

    server.listen(
        Integer.parseInt(port), ar -> startFuture.completer().handle(ar.map((Void) null)));

    Router router = Router.router(vertx);

    router
        .route("/")
        .handler(
            routingContext -> {
              routingContext
                  .response()
                  .putHeader("content-type", "text/html")
                  .end("Keep on bonging!");
            });
  }
}
