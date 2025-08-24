package com.connect.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@NotNull
	@NotBlank(message = "Please enter the name of the user")
	private String name;
	@NotNull
	private String phoneNumber;
	@NotNull
	@NotBlank(message = "Please enter email of the user")
	@Email
	private String email;
	@NotNull
	private Date subscribtionValidation;
	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private List<BooksIssued> booksIssued = new ArrayList<BooksIssued>();

	public User() {
		super();
	}

	public User(String name, String phoneNumber, String email, Date subscribtionValidation,
			List<BooksIssued> booksIssued) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.subscribtionValidation = subscribtionValidation;
		this.booksIssued = booksIssued;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the subscribtionValidation
	 */
	public Date getSubscribtionValidation() {
		return subscribtionValidation;
	}

	/**
	 * @param subscribtionValidation the subscribtionValidation to set
	 */
	public void setSubscribtionValidation(Date subscribtionValidation) {
		this.subscribtionValidation = subscribtionValidation;
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
		return "User [userId=" + userId + ", name=" + name + ", phoneNumber=" + phoneNumber + ", email=" + email
				+ ", subscribtionValidation=" + subscribtionValidation + ", booksIssued=" + booksIssued + "]";
	}

}
