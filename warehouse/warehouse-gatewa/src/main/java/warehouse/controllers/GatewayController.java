package warehouse.controllers;

import java.security.Principal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import warehouse.service.interfaces.GatewayService;

@RestController
public class GatewayController {
	@Autowired
	GatewayService gatewayService;

	@PostMapping("/**")
	public Mono<ResponseEntity<byte[]>> postRequestsProxy(ProxyExchange<byte[]> proxy, ServerHttpRequest request,
			Principal currentUser) {
		return gatewayService.proxyRun(proxy, request, HttpMethod.POST, currentUser.getName());
	}

	@PutMapping("/**")
	public Mono<ResponseEntity<byte[]>> putRequestsProxy(ProxyExchange<byte[]> proxy, ServerHttpRequest request,
			Principal currentUser) {
		return gatewayService.proxyRun(proxy, request, HttpMethod.PUT, currentUser.getName());
	}

	@GetMapping("/**")
	public Mono<ResponseEntity<byte[]>> getRequestsProxy(ProxyExchange<byte[]> proxy, ServerHttpRequest request) {
		return gatewayService.proxyRun(proxy, request, HttpMethod.GET, "");
	}

	@DeleteMapping("/**")
	public Mono<ResponseEntity<byte[]>> deleteRequestsProxy(ProxyExchange<byte[]> proxy, ServerHttpRequest request,
			Principal currentUser) {
		return gatewayService.proxyRun(proxy, request, HttpMethod.DELETE, currentUser.getName());
	}
}
