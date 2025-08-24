package com.connect.entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Books {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookId;
	@NotNull
	@NotBlank(message = "Please enter the Book Title")
	private String bookTitle;
	@NotNull
	@NotBlank(message = "Please enter the ISBN")
	@Pattern(regexp = "^\\d+-\\d+$", message = "Invalid ISBN format. Should be in the format xxx-xxxxxxxxxxx")
	@Column(unique = true)
	private String isbn;
	@NotNull
	@NotBlank(message = "Please enter the Authors name")
	private String author;
	@NotNull
	@Range(min = 1, max = 100, message = "Quantity must be greater than or equal to 1 and less than 100")
	private Integer quantity;
	@NotNull
	@Range(min = 0, max = 100, message = "Available Quantity must be greater than or equal to 0 and less than 100")
	private Integer available;
	@OneToMany(mappedBy = "books")
	@JsonManagedReference
	private List<BooksIssued> booksIssued = new ArrayList<BooksIssued>();

	public Books() {
		super();
	}

	public Books(String bookTitle, String isbn, String author, int quantity, int available,
			List<BooksIssued> booksIssued) {
		super();
		this.bookTitle = bookTitle;
		this.isbn = isbn;
		this.author = author;
		this.quantity = quantity;
		this.available = available;
		this.booksIssued = booksIssued;
	}

	/**
	 * @return the bookId
	 */
	public int getBookId() {
		return bookId;
	}

	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the bookTitle
	 */
	public String getBookTitle() {
		return bookTitle;
	}

	/**
	 * @param bookTitle the bookTitle to set
	 */
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	/**
	 * @return the iSBN
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param iSBN the iSBN to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the avilable
	 */
	public Integer getAvailable() {
		return available;
	}

	/**
	 * @param avilable the avilable to set
	 */
	public void setAvailable(Integer available) {
		this.available = available;
	}

	/**
	 * @return the booksIssued
	 */
	public List<BooksIssued> getBooksIssued() {
		return booksIssued;
	}

	/**
	 * @param booksIssued the booksIssued to set
	 */
	public void setBooksIssued(List<BooksIssued> booksIssued) {
		this.booksIssued = booksIssued;
	}

	@Override
	public String toString() {
		return "Books [bookId=" + bookId + ", bookTitle=" + bookTitle + ", isbn=" + isbn + ", author=" + author
				+ ", quantity=" + quantity + ", available=" + available + ", booksIssued=" + booksIssued + "]";
	}

}
