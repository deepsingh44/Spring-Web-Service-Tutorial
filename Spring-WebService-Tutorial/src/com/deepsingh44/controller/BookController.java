package com.deepsingh44.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepsingh44.dao.BookDao;
import com.deepsingh44.model.Book;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookDao bookDao;

	@PostMapping("/add")
	public ResponseEntity<String> addBook(@RequestBody Book book) {
		int i=bookDao.addBook(book);
		return (i>0)?new ResponseEntity<String>("Successfully Book Added", HttpStatus.OK):new ResponseEntity<String>("Book Added Failed", HttpStatus.FORBIDDEN);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteBook(@RequestBody Book book) {
		int i=bookDao.delete(book);
		return (i>0)?new ResponseEntity<String>("Successfully Book Deleted", HttpStatus.OK):new ResponseEntity<String>("Book Deleted Failed", HttpStatus.FORBIDDEN);
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateBook(@RequestBody Book book) {
		int i=bookDao.update(book);
		return (i>0)?new ResponseEntity<String>("Successfully Book Updated", HttpStatus.OK):new ResponseEntity<String>("Book Updated Failed", HttpStatus.FORBIDDEN);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Book> findBook(@PathVariable("id") int bid) {
		Book book=bookDao.findBookById(bid);
		return (book!=null)?new ResponseEntity<Book>(book, HttpStatus.OK):new ResponseEntity<Book>(new Book(), HttpStatus.FORBIDDEN);
	}

	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks(@RequestParam("column") String sortbycolumn) {
		System.out.println(sortbycolumn);
		List<Book>books=bookDao.fetchAllBooks(sortbycolumn);
		return (books!=null)?new ResponseEntity<List<Book>>(books, HttpStatus.OK):new ResponseEntity<List<Book>>(new ArrayList<Book>(), HttpStatus.FORBIDDEN);
	}
}
