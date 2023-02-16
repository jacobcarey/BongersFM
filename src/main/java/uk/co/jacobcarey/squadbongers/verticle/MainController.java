package uk.co.jacobcarey.squadbongers.verticle;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  @RequestMapping(value = "/")
  public String index() {
    return "Welcome to Squad Bongers!";
  }
}
