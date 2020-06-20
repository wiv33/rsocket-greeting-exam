package org.psawesome.producer.rsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

  @MessageMapping("greet")
  public GreetResponse greetResponse(GreetRequest request) {
    return new GreetResponse(request.getName());
  }
}
