package org.psawesome.producer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * author: ps [https://github.com/wiv33/rsocket-greeting-exam]
 * DATE: 20. 6. 20. Saturday
 */
@Data
@NoArgsConstructor
public class GreetingResponse {
  String name;

  public GreetingResponse(String name) {
    GreetingResponse res = withGreeting("Hello " + name + " @ " + Instant.now());
  }

  private GreetingResponse withGreeting(String msg) {
    this.name = msg;
    return this;
  }
}
