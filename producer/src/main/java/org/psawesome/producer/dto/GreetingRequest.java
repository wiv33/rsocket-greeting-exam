package org.psawesome.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author: ps [https://github.com/wiv33/rsocket-greeting-exam]
 * DATE: 20. 6. 20. Saturday
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GreetingRequest {
  String name;
}
