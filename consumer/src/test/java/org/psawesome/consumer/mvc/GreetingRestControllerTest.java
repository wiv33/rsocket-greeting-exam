package org.psawesome.consumer.mvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.psawesome.consumer.dto.GreetingResponse;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * author: ps [https://github.com/wiv33/rsocket-greeting-exam]
 * DATE: 20. 6. 20. Saturday
 */
@WebFluxTest
class GreetingRestControllerTest {

  WebTestClient client;

  @BeforeEach
  void setUp() {

    client = WebTestClient.bindToController(GreetingRestController.class)
            .build();
  }

  @Test
  void testGreeting() {
    StepVerifier.create(client.get()
            .uri("/greet/{name}", "ps")
            .exchange()
            .returnResult(GreetingResponse.class)
            .getResponseBody()
    )
            .consumeNextWith(greetingResponse ->
                    assertTrue(greetingResponse.getName().contains("ps"))
            )
            .verifyComplete();
  }

  @Test
  @DisplayName("should be ResponseBody NullPointException")
  void testGreetingNotEquals() {
    client.get()
            .uri("/greet-fail/{name}", "ps")
            .exchange()
            .expectBody(ParameterizedTypeReference.forType(GreetingResponse.class))
            .value(o ->
                    assertThrows(NullPointerException.class, () ->
                            assertFalse(o.toString().contains("ps"))))

    ;
  }
}