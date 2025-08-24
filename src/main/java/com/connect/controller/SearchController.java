package com.connect.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.connect.dao.BookRepository;
import com.connect.dao.UserRepository;
import com.connect.entities.Books;
import com.connect.entities.User;

@RestController
public class SearchController {

	@Autowired
	private BookRepository bookRep;
	@Autowired
	private UserRepository userRep;

	// search handler
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query) {

		// calling search function

		List<Books> booksByTitle = this.bookRep.findByBookTitleContaining(query);
		List<Books> booksByAuthor = this.bookRep.findByAuthorContaining(query);
		List<Books> booksByIsbn = this.bookRep.findByIsbnContaining(query);

		// creating new user list
		List<Books> books = new ArrayList<Books>();
		
		// add all searched results in one list
		books.addAll(booksByTitle);
		books.addAll(booksByAuthor);
		books.addAll(booksByIsbn);
		
		// removing duplicates
		Set<Books> uniqueSet = new HashSet<>(books);
        List<Books> listWithoutDuplicates = new ArrayList<>(uniqueSet);
		
		return ResponseEntity.ok(listWithoutDuplicates);
	}
	
	// search handler
	@GetMapping("/search-user/{query}")
	public ResponseEntity<?> searchUser(@PathVariable("query") String query) {
		
		// calling search function
		
		List<User> userByName = this.userRep.findByNameContaining(query);
		List<User> userByEmail = this.userRep.findByEmailContaining(query);
		List<User> userByPhno = this.userRep.findByPhoneNumberContaining(query);
		
		// creating new user list
		List<User> books = new ArrayList<User>();
		
		// add all searched results in one list
		books.addAll(userByName);
		books.addAll(userByEmail);
		books.addAll(userByPhno);
		
		// removing duplicates
		Set<User> uniqueSet = new HashSet<>(books);
		List<User> listWithoutDuplicates = new ArrayList<>(uniqueSet);
		
		return ResponseEntity.ok(listWithoutDuplicates);
	}

}
