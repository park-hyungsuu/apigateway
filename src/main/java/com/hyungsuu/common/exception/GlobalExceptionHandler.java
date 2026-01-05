package com.hyungsuu.common.exception;


import org.slf4j.MDC;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyungsuu.common.vo.BaseResponseVo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@Slf4j
@Component
    @Order(-1) // Ensure this handler is picked up before default ones
    public class GlobalExceptionHandler  implements ErrorWebExceptionHandler {
	
	  private String mdcName = "SID";
  @Override
        public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
            // Log the exception, or perform other actions
        	log.error("["+exchange.getRequest().getId()+"]" + ex.getMessage());
        	exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        	BaseResponseVo baseResponseVo = new BaseResponseVo();
        	
            if (ex instanceof ResponseStatusException) {
            	baseResponseVo.setFail("22222", "33333");
            } else if (ex instanceof GlobalException) {
            	baseResponseVo.setFail(((GlobalException)ex).getCode(), ((GlobalException)ex).getMessage());
            } else {
             	baseResponseVo.setFail("22222", "33333");
            }
            
            // Build and write the error response
        
            
       

            DataBuffer buffer = null;
			try {
				buffer = exchange.getResponse().bufferFactory().wrap(
				    new ObjectMapper().writeValueAsBytes(baseResponseVo)
				);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   	MDC.remove(mdcName);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }


    }