package warehouse.loadbalancer;

import org.reactivestreams.Publisher;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoadBalancer {
	ReactiveLoadBalancer.Factory<ServiceInstance> loadBalancerFactory;

	public LoadBalancer(ReactiveLoadBalancer.Factory<ServiceInstance> loadBalancerFactory) {
		this.loadBalancerFactory = loadBalancerFactory;
	}

	public String getBaseUrl(String serviceName) {
		ReactiveLoadBalancer<ServiceInstance> rlb = null;
		Publisher<Response<ServiceInstance>> publisher = null;
		Mono<Response<ServiceInstance>> chosen = null;
		String BaseUri = null;
		try {
			rlb = loadBalancerFactory.getInstance(serviceName);
			log.debug("LoadBalancer rlb: {}", rlb.toString());
			publisher = rlb.choose();
			log.debug("LoadBalancer publisher: {}", publisher.toString());
			chosen = Mono.from(publisher);
			ServiceInstance instance = chosen.block().getServer();
			BaseUri = instance.getUri().toString();
			log.debug("LoadBalancer BaseUri: {}", BaseUri);
		} catch (Exception e) {
			log.debug("LoadBalancer: error", e.getMessage());
		}

		return BaseUri;
	}

}
