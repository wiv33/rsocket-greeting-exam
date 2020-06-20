package org.psawesome.producer.rsocket;

import org.psawesome.producer.dto.GreetRequest;
import org.psawesome.producer.dto.GreetResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

  @MessageMapping("greet")
  public GreetResponse greetResponse(GreetRequest request) {
    return new GreetResponse(request.getName());
  }
}
