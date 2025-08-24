package com.connect.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.connect.entities.Books;

public interface BookRepository extends JpaRepository<Books, Integer> {
	
	@Query("select b from Books b where b.bookId = :bid")
	public Books getBookById(@Param("bid") int bid);

	//getting books for search results with book title
	public List<Books> findByBookTitleContaining(String keywords);
	
	//getting books for search results with author name
	public List<Books> findByAuthorContaining(String keywords);

	//getting books for search results with ISBN
	public List<Books> findByIsbnContaining(String keywords);
}
