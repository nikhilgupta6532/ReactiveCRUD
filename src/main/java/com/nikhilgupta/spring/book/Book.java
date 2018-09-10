package com.nikhilgupta.spring.book;

public class Book {

	private String name;
	private String author;
	private String[] chapters;
	
	public Book(String name, String author, String[] chapters) {
		this.name = name;
		this.author = author;
		this.chapters = chapters;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String[] getChapters() {
		return chapters;
	}

	public void setChapters(String[] chapters) {
		this.chapters = chapters;
	}

	@Override
	public String toString() {
		return "Name of book is "+name+" and author is "+author; 
	}
	
	@Override
	public boolean equals(Object obj) {
		Book oBook = (Book) obj;
		if(oBook.getName() == this.getName()) {
			return true;
		}else{
			return false;
		}
	}
	
}
