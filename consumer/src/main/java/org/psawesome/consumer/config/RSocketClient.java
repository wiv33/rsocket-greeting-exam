package org.psawesome.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

/**
 * author: ps [https://github.com/wiv33/rsocket-greeting-exam]
 * DATE: 20. 6. 20. Saturday
 */
@Configuration
@Profile("mvc")
public class RSocketClient {

  @Bean
  public RSocketRequester requester(RSocketStrategies strategies) {
    return RSocketRequester.builder()
            .rsocketStrategies(strategies)
            .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
            .connectTcp("localhost", 7000)
            .block();
  }
}
