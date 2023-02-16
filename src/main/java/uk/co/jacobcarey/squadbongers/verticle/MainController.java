package uk.co.jacobcarey.squadbongers.verticle;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  @GetMapping(value = "/")
  public String index() {
    return "Welcome to Squad Bongers!";
  }
}
