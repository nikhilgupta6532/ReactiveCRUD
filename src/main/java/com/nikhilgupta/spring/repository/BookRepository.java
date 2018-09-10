package com.nikhilgupta.spring.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nikhilgupta.spring.book.Book;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BookRepository {

	public Flux<Book> getBooks(List<Book> bookList) {
		Flux<Book> bookFlux = Flux.fromIterable(bookList);
		return bookFlux;
	}

	public Mono<String> getBookByName(String bookName,List<Book> bookList) {
		String author = bookList.stream().filter(book -> book.getName().equals(bookName)).findFirst().get().getAuthor();

		return Mono.just(author);
	}

	public Flux<String> getBookByChapters(String bookName, List<Book> bookList) {
		String[] chapters = bookList.stream()
				.filter(book->book.getName().equals(bookName)).findFirst().get().getChapters();
		
		System.out.println("Chapters are " + chapters);
		
		return Flux.just(chapters);
	}

	public Mono<Book> addBook(Mono<Book> bodyToMono, List<Book> bookList) {
		return bodyToMono.flatMap(book->{
			bookList.add(book);
			return Mono.empty();
		});
	}
	
	

}
