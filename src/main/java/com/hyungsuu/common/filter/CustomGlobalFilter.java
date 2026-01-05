//package com.hyungsuu;
//
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import reactor.core.publisher.Mono;
//
//
//@Slf4j
//@Component
//public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
//
//    public GlobalFilter() {
//        super(Config.class);
//    }
//
//    @Data
//    public static class Config {
//        //Configuration 정보
//        private String baseMessage;
//        private boolean preLogger;
//        private boolean postLogger;
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        //Global PRE Filter
//    	 log.info("Global Filter Base Message :  ");
//        GatewayFilter filter = ((exchange, chain) -> {
//            ServerHttpRequest request =  exchange.getRequest();
//            ServerHttpResponse response =  exchange.getResponse();
//
//  
//
//       
//                log.info("Global Filter Start : Request Id -> {}", request.getId());
//
//           
//
//            //Custom POST Filter
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> { //Mono : WebFlux 비동기 방식 서버 단일값 전달
//                
//                            log.info("Global Filter End : Response Code -> {}", request.getId());
//      
//            })
//            );
//
//        });
//        return filter;
//    }
//}
package com.hyungsuu.common.filter;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.hyungsuu.common.util.TimeUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;



@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

	
	  private String mdcName = "SID";
	  
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
  
    	MDC.put(mdcName, exchange.getRequest().getId());
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();log.info("custom global filter");
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("Custom POST filter: response code -> {}", response.getStatusCode());
           	MDC.remove(mdcName);
        }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}