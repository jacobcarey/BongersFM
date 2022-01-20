package fm.bongers.verticle;

import fm.bongers.infrastructure.Config;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() {

    String port = Config.getInstance().getPort();

    HttpServer server = vertx.createHttpServer();

    server
        .requestHandler(
            request -> {
              request.response().putHeader("Content-Type", "text/plain");
              request.response().end("Keep on bonging!");
            })
        .listen(Integer.parseInt(port));
  }
}
