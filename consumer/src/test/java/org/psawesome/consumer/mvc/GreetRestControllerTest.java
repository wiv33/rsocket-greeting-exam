package org.psawesome.consumer.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

/**
 * author: ps [https://github.com/wiv33/rsocket-greeting-exam]
 * DATE: 20. 6. 20. Saturday
 */
@WebMvcTest
class GreetingRestControllerTest {
  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void testHelloGreeting() {


  }
}