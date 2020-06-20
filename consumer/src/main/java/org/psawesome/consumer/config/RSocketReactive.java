package org.psawesome.consumer.config;

import io.rsocket.transport.netty.client.TcpClientTransport;
import org.psawesome.consumer.dto.GreetingRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

/**
 * author: ps [https://github.com/wiv33/rsocket-greeting-exam]
 * DATE: 20. 6. 20. Saturday
 */
@Configuration
@Profile("reactive")
public class RSocketReactive {
  @Bean
  Mono<RSocketRequester> rScRequester(RSocketRequester.Builder builder, RSocketStrategies strategies) {
    return builder.dataMimeType(MimeTypeUtils.APPLICATION_JSON)
            .rsocketStrategies(strategies)
            .connectTcp("localhost", 7000)
            .log()
            ;
  }

  @Bean
  Disposable request(RSocketStrategies strategies) {
    return RSocketRequester.builder()
            .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
            .metadataMimeType(MimeTypeUtils.APPLICATION_JSON)
            .rsocketStrategies(strategies)
            .connect(TcpClientTransport.create(7000))
            .subscribe(req -> {
              req.route("greet", new GreetingRequest("hello"));
            })
            ;
  }
}
