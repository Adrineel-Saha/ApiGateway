package com.cognizant.apigateway.main;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

@SpringBootApplication
@EnableDiscoveryClient(autoRegister=true)
@OpenAPIDefinition(
		info=@Info(
				title="API Gateway REST API Documentation",
				description="REST API Documentation for API Gateway",
				version="v1.0"
		)
)
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<Filter> corsPreflightFilter() {
		FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();

		bean.setFilter((request, response, chain) -> {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;

			if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
				res.setStatus(HttpServletResponse.SC_OK);
				res.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
				res.setHeader("Access-Control-Allow-Methods",
						"GET,POST,PUT,DELETE,OPTIONS");
				res.setHeader("Access-Control-Allow-Headers", "*");
				res.setHeader("Access-Control-Allow-Credentials", "true");
				return;
			}

			chain.doFilter(request, response);
		});

		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}
