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
public class GreetResponse {
  String name;

  public GreetResponse(String name) {
    GreetResponse res = withGreeting("Hello " + name + " @ " + Instant.now());
  }

  private GreetResponse withGreeting(String msg) {
    this.name = msg;
    return this;
  }
}
