package com.nikhilgupta.spring.component;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.nikhilgupta.spring.book.Book;
import com.nikhilgupta.spring.service.BookService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class BookHandler {
	
	@Autowired
	BookService service;

	public Mono<ServerResponse> getBooks(ServerRequest request){
		System.out.println("handler called");
		Flux<Book> books = service.getBooks();
		return ServerResponse.ok()
				.body(books,Book.class);
	}
	
	public Mono<ServerResponse> getBookByName(ServerRequest request) {
		Optional<String> bookName = request.queryParam("name");
		System.out.println(bookName.get());
		Mono<String> book = service.getBookByName(bookName.get());
		return ServerResponse.ok()
				.body(book,String.class);
	}
	
	public Mono<ServerResponse> getBookChapters(ServerRequest request) {
		Optional<String> bookName = request.queryParam("name");
		Flux<String> book = service.getBookChapters(bookName.get());
		return ServerResponse.ok()
				.body(book,String.class);
		
	}
	
	public Mono<ServerResponse> addBook(ServerRequest request) {
		Mono<Book> bodyToMono = request.bodyToMono(Book.class);
		Mono<Book> book = service.addBook(bodyToMono);
		return ServerResponse.ok()
				.body(book,Book.class);
	}
	
}
