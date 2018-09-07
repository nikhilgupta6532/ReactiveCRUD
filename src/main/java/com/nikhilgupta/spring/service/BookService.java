package com.nikhilgupta.spring.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.nikhilgupta.spring.book.Book;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService {

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

		Flux<Book> bookFlux = Flux.fromIterable(bookList);

		return bookFlux;

	}

	public Mono<String> getBookByName(String bookName) {
		
		String author = bookList.stream().filter(book->book.getName().equals(bookName)).findFirst().get().getAuthor();
		
		return  Mono.just(author);
	}

	public Flux<String> getBookChapters(String bookName) {
		String[] chapters = bookList.stream()
				.filter(book->book.getName().equals(bookName)).findFirst().get().getChapters();
		
		System.out.println("Chapters are " + chapters);
		
		return Flux.just(chapters);
	}

	public Mono<Book> addBook(Mono<Book> bodyToMono) {
		return bodyToMono.flatMap(book->{
			bookList.add(book);
			return Mono.empty();
		});
	}

}
