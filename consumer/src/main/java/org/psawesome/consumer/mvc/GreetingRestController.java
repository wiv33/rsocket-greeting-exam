package org.psawesome.consumer.mvc;

import lombok.RequiredArgsConstructor;
import org.psawesome.consumer.dto.GreetingRequest;
import org.psawesome.consumer.dto.GreetingResponse;
import org.reactivestreams.Publisher;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: ps [https://github.com/wiv33/rsocket-greeting-exam]
 * DATE: 20. 6. 20. Saturday
 */
@RestController
@RequiredArgsConstructor
public class GreetingRestController {

  private final RSocketRequester requester;

  @GetMapping("/greet/{name}")
  public Publisher<GreetingResponse> greetingResponsePublisher(@PathVariable String name) {
//    return Mono.just(new GreetingResponse(name)); // test case success complete
    return requester.route("greet")
            .data(new GreetingRequest(name))
            .retrieveMono(GreetingResponse.class);
  }

}
