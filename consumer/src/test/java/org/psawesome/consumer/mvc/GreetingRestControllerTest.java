package org.psawesome.consumer.mvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psawesome.consumer.dto.GreetingRequest;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * author: ps [https://github.com/wiv33/rsocket-greeting-exam]
 * DATE: 20. 6. 20. Saturday
 */
@WebFluxTest
class GreetingRestControllerTest {

  WebTestClient client;

  @BeforeEach
  void setUp() {
    client = WebTestClient.bindToServer()
            .baseUrl("http://localhost:8080/greet")
            .build();
  }

  @Test
  void testGreeting() {
    GreetingRequest expect = new GreetingRequest("ps");
    StepVerifier.create(client.get()
            .uri("/{name}", "ps")
            .exchange()
            .returnResult(GreetingRequest.class)
            .getResponseBody())
            .expectNext(expect)
            .expectComplete()
            .verify();
  }

  @Test
  void testGreetingFail() {
    assertThrows(RuntimeException.class, () -> client.get()
            .uri("/{name}", "ps")
            .exchange()
    );
  }
}