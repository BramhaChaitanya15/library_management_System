package com.connect.entities;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class BooksIssued {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int issueId;
	@ManyToOne
	@JoinColumn(name = "user_user_id")
	@JsonBackReference
	private User user;
	@ManyToOne
	@JoinColumn(name = "books_book_id")
	@JsonBackReference
	private Books books;
	@NotNull
	private Date issueDate;
	@NotNull
	private Date returnDate;

	public BooksIssued() {
		super();
	}

	public BooksIssued(User user, Books books, Date issueDate, Date returnDate) {
		super();
		this.user = user;
		this.books = books;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
	}
	
	/**
	 * @return the issueId
	 */
	public int getIssueId() {
		return issueId;
	}

	/**
	 * @param issueId the issueId to set
	 */
	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the books
	 */
	public Books getBooks() {
		return books;
	}

	/**
	 * @param books the books to set
	 */
	public void setBooks(Books books) {
		this.books = books;
	}

	/**
	 * @return the issueDate
	 */
	public Date getIssueDate() {
		return issueDate;
	}

	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * @return the returnDate
	 */
	public Date getReturnDate() {
		return returnDate;
	}

	/**
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	@Override
	public String toString() {
		return "BooksIssued [user=" + user + ", books=" + books + ", issueDate=" + issueDate + ", returnDate="
				+ returnDate + "]";
	}

}
