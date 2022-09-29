package br.com.confitec.teste.config;


import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MDCFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Instant inicio = Instant.now();
		String requestUri = "";
		String metodo = "";
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			requestUri = httpRequest.getRequestURI();
			metodo = httpRequest.getMethod();
			
			MDC.put("idTransaction", UUID.randomUUID().toString());
			log.info("requestUri :: {} :: {} - inicio", metodo, requestUri);
			chain.doFilter(request, response);
		} finally {
			Instant fim = Instant.now();
			long milis = Duration.between(inicio, fim).toMillis();
			log.info("requestUri :: {} :: {} - final {} ms", metodo, requestUri, milis);
			MDC.clear();
		}
	}
}