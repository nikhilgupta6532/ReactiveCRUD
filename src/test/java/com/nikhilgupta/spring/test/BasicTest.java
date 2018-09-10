package com.nikhilgupta.spring.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.junit.Assert.assertThat;

import com.nikhilgupta.spring.book.Book;
import com.nikhilgupta.spring.repository.BookRepository;
import com.nikhilgupta.spring.service.BookService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicTest {

	@MockBean
	private BookRepository bookRepository;

	@Autowired
	private BookService bookService;
	
	@Autowired
	RouterFunction<ServerResponse> routerFunction;
	
	@Autowired
	ApplicationContext applicationContext;

	private List<Book> bookList;

	@Before
	public void createList() {
		String[] book1 = { "two", "one", "three" };
		String[] book2 = { "four", "five", "six" };
		String[] book3 = { "seven", "eight", "nine" };

		List<Book> listBooks = Arrays.asList(new Book("DeathNote", "Ryuuk", book1),
				new Book("Origin", "Dan Brown", book2), new Book("Ralph", "Bobby", book3));

		this.bookList = listBooks;
	}

	@Test
	public void firstTestCaseForRepo() {
		// given
		String[] book1 = { "two", "one", "three" };
		String[] book2 = { "four", "five", "six" };
		String[] book3 = { "seven", "eight", "nine" };
		Flux<Book> flux = Flux.just(new Book("DeathNote", "Ryuuk", book1), new Book("Origin", "Dan Brown", book2),
				new Book("Ralph", "Bobby", book3));

		// when
		when(bookRepository.getBooks(bookList)).thenReturn(flux);

		// then
//		StepVerifier.create(bookService.getBooks())
//			.recordWith(ArrayList::new)
//			.expectNextCount(3)
//			.consumeRecordedWith(results->{
//				assertThat(results).extracting(Book::getName).contains("DeathNote","Origin","Ralph");
//			})
//			.verifyComplete();

		StepVerifier.create(bookRepository.getBooks(bookList)).expectNext(new Book("DeathNote", "Ryuuk", book1))
				.expectNext(new Book("Origin", "Dan Brown", book2)).expectNext(new Book("Ralph", "Bobby", book3))
				.expectComplete().verify();
	}

	@Test
	public void secondTestCaseForRepo() {

		// given
		String author = "Ryuuk";
		Mono<String> mono = Mono.just(author);

		// when
		when(bookRepository.getBookByName("DeathNote", bookList)).thenReturn(mono);

		// then
		StepVerifier.create(bookRepository.getBookByName("DeathNote", bookList)).expectNext("Ryuuk").expectComplete()
				.verify();
	}

	@Test
	public void thirdTestCaseForRepo() {
		// given
		String[] book1 = { "two", "one", "three" };
		Flux<String> fromArray = Flux.fromArray(book1);

		// when
		when(bookRepository.getBookByChapters("DeathNote", bookList)).thenReturn(fromArray);

		// then
		StepVerifier.create(bookRepository.getBookByChapters("DeathNote", bookList)).expectNext("two").expectNext("one")
				.expectNext("three").expectComplete().verify();
	}

	@Test
	public void firstTestCaseForService() {
		// given
		String[] book1 = { "two", "one", "three" };
		String[] book2 = { "four", "five", "six" };
		String[] book3 = { "seven", "eight", "nine" };
		Flux<Book> flux = Flux.just(new Book("DeathNote", "Ryuuk", book1), new Book("Origin", "Dan Brown", book2),
				new Book("Ralph", "Bobby", book3));

		// when
		when(bookService.getBooks()).thenReturn(flux);

		// then
		StepVerifier.create(bookService.getBooks()).expectNext(new Book("DeathNote", "Ryuuk", book1))
				.expectNext(new Book("Origin", "Dan Brown", book2)).expectNext(new Book("Ralph", "Bobby", book3))
				.expectComplete().verify();
	}

	@Test
	public void secondTestCaseForService() {
		// given
		String author = "Ryuuk";
		Mono<String> mono = Mono.just(author);

		// when
		when(bookService.getBookByName("DeathNote")).thenReturn(mono);

		// then
		StepVerifier.create(bookService.getBookByName("DeathNote")).expectNext("Ryuuk").expectComplete().verify();
	}

	@Test
	public void thirdTestCaseForService() {
		// given
		String[] book1 = { "two", "one", "three" };
		Flux<String> fromArray = Flux.fromArray(book1);
		
		//when
		when(bookService.getBookChapters("DeathNote")).thenReturn(fromArray);
		
		//then
		StepVerifier.create(bookService.getBookChapters("DeathNote"))
			.expectNext("two")
			.expectNext("one")
			.expectNext("three")
			.expectComplete()
			.verify();
	}
	
	@Test
	public void firstTestRouter() {
		//given
		String[] book1 = { "two", "one", "three" };
		String[] book2 = { "four", "five", "six" };
		String[] book3 = { "seven", "eight", "nine" };
		Flux<Book> flux = Flux.just(new Book("DeathNote", "Ryuuk", book1), new Book("Origin", "Dan Brown", book2),
				new Book("Ralph", "Bobby", book3));

		WebTestClient webTestClient = WebTestClient.bindToRouterFunction(routerFunction)
				.build();
		
		//when
		when(bookRepository.getBooks(bookList)).thenReturn(flux);
		
		//then
		Flux<Book> responseBody = webTestClient.get()
			.uri(uriBuilder->uriBuilder.path("/getBooks").build())
			.exchange()
			.expectStatus().isOk()
			.returnResult(Book.class).getResponseBody();
		
		StepVerifier.create(bookRepository.getBooks(bookList))
			.expectNext(new Book("DeathNote", "Ryuuk", book1))
			.expectNext(new Book("Origin", "Dan Brown", book2))
			.expectNext(new Book("Ralph", "Bobby", book3))
			.expectComplete()
			.verify();
	}

}
