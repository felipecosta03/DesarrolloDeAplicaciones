package com.example.desarrollodeaplicaciones.router;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingRouter {


  @GetMapping("/ping")
  public String ping() {
    return "pong";
  }
}
