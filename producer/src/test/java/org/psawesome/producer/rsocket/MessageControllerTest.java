package org.psawesome.producer.rsocket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * author: ps [https://github.com/wiv33/rsocket-greeting-exam]
 * DATE: 20. 6. 20. Saturday
 */
@SpringBootTest
class MessageControllerTest {

  public static RSocketRequester requesterBlock;
  public static Mono<RSocketRequester> requester;
  
  @BeforeEach
  static void setUp(@Autowired RSocketRequester.Builder builder) {
    requesterBlock = builder.connectTcp("localhost", 7000)
            .block(Duration.ofSeconds(3));
    
    requester = builder.connectTcp("localhost", 7500);
  }

  @Test
  void testBlockRetrieve() {

    StepVerifier.create(requesterBlock.route("greet")
            .data(new GreetRequest("ps"))
            .retrieveMono(GreetResponse.class))
            .assertNext(res -> Assertions.assertAll(
                    ()-> assertNotNull(res)
            ))
    .expectComplete()
    .verify();
  }

  @Test
  void testRetrieve() {
    requester.subscribe(requester -> {
      requester.route("greet-non")
              .data(new GreetRequest("ps"))
              .retrieveMono(GreetResponse.class);
    });
  }
}