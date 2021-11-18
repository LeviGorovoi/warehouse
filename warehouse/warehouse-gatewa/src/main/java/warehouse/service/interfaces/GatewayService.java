package warehouse.service.interfaces;


import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;

import reactor.core.publisher.Mono;

public interface GatewayService {

	Mono<ResponseEntity<byte[]>> proxyRun(ProxyExchange<byte[]> proxy, ServerHttpRequest request, HttpMethod method,
			String username);
}
