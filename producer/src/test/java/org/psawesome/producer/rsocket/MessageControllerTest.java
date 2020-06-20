package org.psawesome.producer.rsocket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psawesome.producer.dto.GreetRequest;
import org.psawesome.producer.dto.GreetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

  @BeforeEach
  void setUp(@Autowired RSocketRequester.Builder builder) {
    requesterBlock = builder.connectTcp("localhost", 7000)
            .block(Duration.ofSeconds(3));

    requester = builder.connectTcp("localhost", 7500);
  }

  @Test
  void testBlockRetrieve() {
    StepVerifier.create(requesterBlock.route("greet")
            .data(new GreetRequest("ps"))
            .retrieveMono(GreetResponse.class)
            .log())
            .assertNext(res -> Assertions.assertAll(
                    () -> assertNotNull(res),
                    () -> assertTrue(res.getName().contains("ps"))
            ))
            .expectComplete()
            .verify();
  }

  @Test
  void testRetrieve() {
    requester.subscribe(requester -> {
      StepVerifier.create(requester.route("greet-non")
              .data(new GreetRequest("ps"))
              .retrieveMono(GreetResponse.class)
              .log())
              .assertNext(res -> Assertions.assertAll(
                      () -> assertNotNull(res),
                      () -> assertTrue(res.getName().contains("ps"))
              ))
              .expectComplete()
              .verify();
    });
  }
}