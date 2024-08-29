package com.github.lab.files.controller;

import com.github.lab.files.model.Book;
import com.github.lab.files.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/books")
public class BookController
{

	@Autowired
	private BookService service;

	@PostMapping
	public Book addBook(@RequestBody Book book)
	{
		return service.saveBook(book);
	}

	@GetMapping
	public Collection<Book> findAllBooks()
	{
		return service.getBooks().values();
	}

	@GetMapping("/id")
	public Book findBookById(@RequestParam("id") int bookId)
	{
		return service.getBookById(bookId);
	}

	@PutMapping
	public Book updateBook(@RequestBody Book book)
	{
		return service.updateBook(book);
	}

	@DeleteMapping
	public String deleteBook(@RequestParam("id") int bookId)
	{
		return service.deleteBook(bookId);
	}
}
