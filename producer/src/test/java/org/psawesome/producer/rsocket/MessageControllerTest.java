package org.psawesome.producer.rsocket;

import io.rsocket.exceptions.ApplicationErrorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.psawesome.producer.dto.GreetingRequest;
import org.psawesome.producer.dto.GreetingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * author: ps [https://github.com/wiv33/rsocket-greeting-exam]
 * DATE: 20. 6. 20. Saturday
 */
@SpringBootTest
class MessageControllerTest {

  public RSocketRequester requesterBlock;
  public Mono<RSocketRequester> requester;

  GreetingRequest greetRequest;

  @BeforeEach
  void setUp(@Autowired RSocketRequester.Builder builder) {
    requesterBlock = builder.connectTcp("localhost", 7000)
            .block(Duration.ofSeconds(3));
    requester = builder.connectTcp("localhost", 7500);

    greetRequest = null;
  }

  @Test
  @DisplayName("should be request and response by requesterBlock")
  void testBlockRetrieve() {
    greetRequest = new GreetingRequest("ps");
    StepVerifier.create(requesterBlock.route("greet")
            .data(greetRequest)
            .retrieveMono(GreetingResponse.class)
            .log())
            .assertNext(res -> Assertions.assertAll(
                    () -> assertNotNull(res),
                    () -> assertTrue(res.getName().contains("ps"))
            ))
            .expectComplete()
            .verify();
  }

  @Test
  @DisplayName("should be req res by requester.subscribe()")
  void testRetrieve() {
    requester.subscribe(requester -> {
      greetRequest = new GreetingRequest("ps");
      StepVerifier.create(requester.route("greet-non")
              .data(greetRequest)
              .retrieveMono(GreetingResponse.class)
              .log())
              .assertNext(res -> Assertions.assertAll(
                      () -> assertNotNull(res),
                      () -> assertTrue(res.getName().contains("ps"))
              ))
              .expectComplete()
              .verify();
    });
  }

  @Test
  @DisplayName("should be not found route [] greet-fail")
  @MessageExceptionHandler(ApplicationErrorException.class)
  void testFail() {
    greetRequest = new GreetingRequest("ps");
    requesterBlock.route("greet-fail")
            .data(greetRequest)
            .send();
  }

}