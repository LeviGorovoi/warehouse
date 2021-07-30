package warehouse.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import warehouse.service.interfaces.GatewayService;
@Service
@Slf4j
public class GatewayServiceImpl implements GatewayService {
	@Value("${app-services-allowed:  warehouse-state-back:9091}")
	List<String> allowedServices;
	HashMap<String, String> mapServices; // key - service name, value - base URL
	@Value("${app-localhost:false}")
	boolean isLocalhost;

	@Override
	public Mono<ResponseEntity<byte[]>> proxyRun(ProxyExchange<byte[]> proxy, ServerHttpRequest request,
			HttpMethod method, String username) {
		String proxiedUri = getProxiedUri(request, username);
		if (proxiedUri == null) {
			return Mono.just(ResponseEntity.status(404).body("Service not found".getBytes()));
		}
		return proxyPerform(proxy, proxiedUri, method);
	}

	private String getProxiedUri(ServerHttpRequest request, String username) {
		String uri = request.getURI().toString();
		log.debug("received request: {}", uri);
		String serviceName = uri.split("/+")[2];
		log.debug("service name is {}", serviceName);
		String res = mapServices.get(serviceName);
		if (res != null) {
			int indService = uri.indexOf(serviceName) + serviceName.length();
			res += uri.substring(indService);
				if(res.contains("?")) {
					res +="&username="+username;
				}else {
					res +="?username="+username;
				}			
			log.debug("resulted uri+: {}", res);

		}
		return res;
	}
	private Mono<ResponseEntity<byte[]>> proxyPerform(ProxyExchange<byte[]> proxy, String proxiedUri, HttpMethod method) {
		ProxyExchange<byte[]> proxyExchange = proxy.uri(proxiedUri);
		switch(method) {
		case POST: return proxyExchange.post();
		case GET: return proxyExchange.get();
		case PUT: return proxyExchange.put();
		case DELETE: return proxyExchange.delete();
		
		default: return Mono.just(ResponseEntity.status(500).body("unsupported proxy operation".getBytes()));
		}
	}
	
	@PostConstruct
	void fillMapServices() {
		mapServices = new HashMap<>();
		allowedServices.forEach(s -> {
			String[] serviceTokens = s.split(":");
			String serviceName = serviceTokens[0];
			String port = serviceTokens[1];
			String baseUrl = String.format("http://%s:%s", isLocalhost ?
					"localhost" : serviceName, port);
			mapServices.put(serviceName, baseUrl);
		});
		log.debug("isLocalhost+: {}, mapServices: {}",isLocalhost, mapServices);	
}
}
