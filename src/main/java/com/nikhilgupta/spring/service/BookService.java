package com.nikhilgupta.spring.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.nikhilgupta.spring.book.Book;
import com.nikhilgupta.spring.repository.BookRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;

	private List<Book> bookList;
	
	public BookService() {
		System.out.println("service class constructor called");
		String[] book1 = {"two","one","three"};
		String[] book2 = {"four","five","six"};
		String[] book3 = {"seven","eight","nine"};
		
		List<Book> listBooks = Arrays.asList(new Book("DeathNote", "Ryuuk",book1), new Book("Origin", "Dan Brown",book2),
				new Book("Ralph", "Bobby",book3));

		this.bookList = listBooks;
		
		System.out.println(listBooks);

	}

	public Flux<Book> getBooks() {
		
		System.out.println("service class method called");
		
		return bookRepository.getBooks(bookList);
		
		//Mono<List<Book>> collectList = bookFlux.collectList();
		

	}

	public Mono<String> getBookByName(String bookName) {
		
		return bookRepository.getBookByName(bookName,bookList);
	}

	
	public Flux<String> getBookChapters(String bookName) {
		return bookRepository.getBookByChapters(bookName,bookList);
	}

	public Mono<Book> addBook(Mono<Book> bodyToMono) {
		
		return bookRepository.addBook(bodyToMono,bookList);
	}


}
