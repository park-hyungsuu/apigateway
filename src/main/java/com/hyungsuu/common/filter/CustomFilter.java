package com.hyungsuu.common.filter;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import com.hyungsuu.gateway.dao.CommonDAO;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
	
	@Autowired
	private CommonDAO commonDAO;
	
	public CustomFilter(){
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {

         GatewayFilter filter =new OrderedGatewayFilter((exchange, chain) -> {
 
        	 HashMap<String, Object> reqMap = new HashMap<String, Object>();
        	HashMap<String, Object> rtnMap = commonDAO.selectOne("Common.selectCommon", reqMap);
    		log.info("ProcessBusiness. Start() " + rtnMap.toString()+"AAAAAA");
        	log.info("== PreFilter ==");
            log.info("Request id = {}",exchange.getRequest().getId());
            log.info("Request Path = {}",exchange.getRequest().getPath());
            log.info("Request Header = {}", exchange.getRequest().getHeaders());
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                log.info("== PostFilter ==");
                log.info("ResponseStatus = {}", exchange.getResponse().getStatusCode());
                log.info("responseHeader = {}", exchange.getResponse().getHeaders());
            }));
      
 
        },1);
         return filter;
    }

    static class Config{
        //설정값이 필요하면 추가
    }
}