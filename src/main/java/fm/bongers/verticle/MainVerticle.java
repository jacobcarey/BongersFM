package fm.bongers.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) {

    HttpServer server = vertx.createHttpServer();

    server.requestHandler(
        req -> {
          req.response().end("OK");
        });

    server.listen(
        8080,
        ar -> startFuture.completer().handle(ar.map((Void) null)));
  }
}
