package org.psawesome.producer.rsocket;

import org.psawesome.producer.dto.GreetingRequest;
import org.psawesome.producer.dto.GreetingResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

  @MessageMapping("greet")
  public GreetingResponse greetResponse(GreetingRequest request) {
    return new GreetingResponse(request.getName());
  }
}
