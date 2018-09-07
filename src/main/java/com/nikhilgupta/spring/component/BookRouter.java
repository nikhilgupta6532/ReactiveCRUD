package com.nikhilgupta.spring.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BookRouter {

	@Bean
	public RouterFunction<ServerResponse> addRoutes(BookHandler bookHandler) {
		return RouterFunctions
				.route(RequestPredicates.GET("/getBooks"), bookHandler::getBooks)
				.andRoute(RequestPredicates.GET("/getBookByName"), bookHandler::getBookByName)
				.andRoute(RequestPredicates.GET("/getBookChapters"), bookHandler::getBookChapters)
				.andRoute(RequestPredicates.POST("/addBook"), bookHandler::addBook);
	}
	
//	BookHandler bookHandler;
//	RouterFunction<ServerResponse> route = 
//			RouterFunctions.route(RequestPredicates.GET("/getBooks"), bookHandler::getBooks);
}
