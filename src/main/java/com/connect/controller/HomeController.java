package com.connect.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.connect.dao.BookRepository;
import com.connect.dao.BooksIssuedRepository;
import com.connect.dao.UserRepository;
import com.connect.entities.Books;
import com.connect.entities.BooksIssued;
import com.connect.entities.User;
import com.connect.helper.Alerts;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	// autowire repository
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BooksIssuedRepository issuedRepository;

	// home page handler
	@RequestMapping("/")
	public String home(Model m) {

		// sending data to template
		m.addAttribute("title", "Home page");

		return "index";
	}

	// book list page handler
	@RequestMapping("/book-list")
	public String bookList(Model m) {

		// fetching data from database
		List<User> users = this.userRepository.findAll();
		List<Books> books = this.bookRepository.findAll();
		List<BooksIssued> issues = this.issuedRepository.findAll();

		// sending data to template
		m.addAttribute("showSearchBookBar", true);
		m.addAttribute("title", "Book List");
		m.addAttribute("books", books);
		m.addAttribute("users", users);
		m.addAttribute("issues", issues);
		m.addAttribute("book", new Books());

		return "booklist";
	}

	// user list page handler
	@RequestMapping("/user-list")
	public String userList(Model m) {

		// fetching data from database
		List<User> users = this.userRepository.findAll();
		List<Books> books = this.bookRepository.findAll();
		List<BooksIssued> issues = this.issuedRepository.findAll();

		// sending data to template
		m.addAttribute("showSearchUserBar", true);
		m.addAttribute("title", "User List");
		m.addAttribute("users", users);
		m.addAttribute("user", new User());
		m.addAttribute("books", books);
		m.addAttribute("issues", issues);

		return "userlist";
	}

	// add book page handler
	@RequestMapping("/add-book")
	public String addBook(Model m) {

		// sending data to template
		m.addAttribute("title", "Add Book");
		m.addAttribute("book", new Books());

		return "addbook";
	}

	// add user page handler
	@RequestMapping("/add-user")
	public String addUser(Model m) {

		// sending data to template
		m.addAttribute("title", "Add user");
		m.addAttribute("user", new User());

		return "adduser";
	}

	// add book process handler
	@PostMapping("/process-add-book")
	public String addBookProcess(@Valid @ModelAttribute("book") Books book, BindingResult result, Model m,
			HttpSession session) {

		m.addAttribute("title", "Add Book");

		try {
			// checking validations and sending error to register form
			if (result.hasErrors()) {
				m.addAttribute(book);
				return "addbook";
			}

			if (book.getAvailable() > book.getQuantity()) {
				throw new Exception("available can't be greater than book quantity...");
			} else {
				this.bookRepository.save(book);
				session.setAttribute("alert", new Alerts("Book successfully added", "alert-success"));
				m.addAttribute("book", new Books());
			}

			return "addbook";

		} catch (Exception ex) {
			ex.printStackTrace();
			m.addAttribute(book);
			session.setAttribute("alert",
					new Alerts("somthing went wrong!!! the ISBN cannot be duplicate for any 2 books", "alert-danger"));
			return "addbook";
		}

	}

	// add user process handler
	@PostMapping("/process-add-user")
	public String addUserProcess(@Valid @ModelAttribute("user") User user, BindingResult result, Model m,
			HttpSession session) {

		m.addAttribute("title", "Add user");

		try {
			// checking validations and sending error to register form
			if (result.hasErrors()) {
				m.addAttribute(user);
				return "adduser";
			}

			// save user in database
			this.userRepository.save(user);
			session.setAttribute("alert", new Alerts("User successfully added", "alert-success"));
			m.addAttribute("user", new User());
			return "adduser";

		} catch (Exception ex) {
			ex.printStackTrace();
			m.addAttribute(user);
			session.setAttribute("alert", new Alerts("somthing went wrong!!!", "alert-danger"));
			return "adduser";
		}

	}

	// update book details controller
	@PostMapping("/update-book")
	public String editBookProcess(@Valid @ModelAttribute("book") Books book, BindingResult result,
			HttpSession session) {

		try {

			if (book.getAvailable() > book.getQuantity()) {
				throw new Exception("available can't be greater than book quantity...");
			} else {
				this.bookRepository.save(book);
				session.setAttribute("alert",
						new Alerts("Book " + book.getBookTitle() + " successfully edited", "alert-success"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("alert", new Alerts("somthing went wrong!!! " + e.getMessage(), "alert-danger"));
		}

		return "redirect:/book-list";
	}

	// delete book record handler
	@GetMapping("/delete-record/{bookId}")
	public String deleteBookProcess(@PathVariable("bookId") int bid, HttpSession session) {

		try {
			Books book = bookRepository.getBookById(bid);

			book.setBooksIssued(null);
			this.bookRepository.delete(book);

			session.setAttribute("alert", new Alerts("Book successfully deleted", "alert-warning"));
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("alert", new Alerts("somthing went wrong!!! " + e.getMessage(), "alert-danger"));
		}

		return "redirect:/book-list";
	}

	// delete user record handler
	@GetMapping("/delete-record-user/{userId}")
	public String deleteUserProcess(@PathVariable("userId") int uid, HttpSession session) {

		try {
			User user = userRepository.getUserById(uid);

			user.setBooksIssued(null);
			this.userRepository.delete(user);

			session.setAttribute("alert", new Alerts("user successfully deleted", "alert-warning"));
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("alert", new Alerts("somthing went wrong!!! " + e.getMessage(), "alert-danger"));
		}

		return "redirect:/user-list";
	}

	// delete user record handler
	@GetMapping("/delete-record-issue/{issueId}")
	public String deleteIssueProcess(@PathVariable("issueId") int iid, HttpSession session) {

		try {
			BooksIssued issue = issuedRepository.getBooksIssuedById(iid);

			// increasing available
			Books book = issue.getBooks();
			book.setAvailable(book.getAvailable() + 1);
			this.bookRepository.save(book);

			issue.setBooks(null);
			issue.setUser(null);
			this.issuedRepository.delete(issue);

			session.setAttribute("alert", new Alerts("Book successfully returned", "alert-warning"));
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("alert", new Alerts("somthing went wrong!!! " + e.getMessage(), "alert-danger"));
		}

		return "redirect:/book-list";
	}

	@PostMapping("/process-issue-book")
	public String issueBook(@RequestParam Map<String, String> allParam, HttpSession session) {

		int book;
		Books b = new Books();

		try {
			// getting all parameters from the book issue form
			String p1 = allParam.get("books1");
			String p2 = allParam.get("books2");
			String p3 = allParam.get("books3");
			String uid = allParam.get("userId");

			// getting current date
			long currentTimeMillis = System.currentTimeMillis();
			Date currentDate = new Date(currentTimeMillis);

			// fetching user
			User user = this.userRepository.getUserById(Integer.parseInt(uid));

			// creating list for books issued
			List<Books> issuedBooks = new ArrayList<Books>();

			// saving in the database
			// checking if user has selected 1, 2 or 3 books and fetching it from database
			if (!p1.equalsIgnoreCase("")) {
				book = Integer.parseInt(p1);
				b = this.bookRepository.getBookById(book);
				issuedBooks.add(b);
				b.setAvailable(b.getAvailable() - 1);
				this.bookRepository.save(b);
				BooksIssued issued = new BooksIssued(user, b, currentDate, user.getSubscribtionValidation());
				this.issuedRepository.save(issued);
			}
			if (!p2.equalsIgnoreCase("")) {
				book = Integer.parseInt(p2);
				b = this.bookRepository.getBookById(book);
				issuedBooks.add(b);
				b.setAvailable(b.getAvailable() - 1);
				this.bookRepository.save(b);
				BooksIssued issued = new BooksIssued(user, b, currentDate, user.getSubscribtionValidation());
				this.issuedRepository.save(issued);
			}
			if (!p3.equalsIgnoreCase("")) {
				book = Integer.parseInt(p3);
				b = this.bookRepository.getBookById(book);
				issuedBooks.add(b);
				b.setAvailable(b.getAvailable() - 1);
				this.bookRepository.save(b);
				BooksIssued issued = new BooksIssued(user, b, currentDate, user.getSubscribtionValidation());
				this.issuedRepository.save(issued);
			}

			session.setAttribute("alert", new Alerts("Books issued!", "alert-success"));

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("alert", new Alerts("somthing went wrong!!! " + e.getMessage(), "alert-danger"));
		}

		return "redirect:/user-list";
	}

}
