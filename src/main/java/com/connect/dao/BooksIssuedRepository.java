package com.connect.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.connect.entities.BooksIssued;

public interface BooksIssuedRepository extends JpaRepository<BooksIssued, Integer> {
	
	@Query("select i from BooksIssued i where i.issueId = :iid")
	public BooksIssued getBooksIssuedById(@Param("iid") int iid);
	
}
