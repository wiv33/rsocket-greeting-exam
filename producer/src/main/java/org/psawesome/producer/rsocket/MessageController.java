package org.psawesome.producer.rsocket;

import org.psawesome.producer.dto.GreetingRequest;
import org.psawesome.producer.dto.GreetingResponse;
import org.reactivestreams.Publisher;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Controller
public class MessageController {

  @MessageMapping("greet")
  public GreetingResponse greetResponse(GreetingRequest request) {
    return new GreetingResponse(request.getName());
  }

  @MessageMapping("greet-stream")
  public Publisher<GreetingResponse> greetingResponsePublisher(GreetingRequest request) {
    return Flux.fromStream(Stream.generate(() -> new GreetingResponse(request.getName())))
            .log()
            .delayElements(Duration.ofMillis(700));
  }
}
