package com.digitalbooks.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbooks.models.Book;
import com.digitalbooks.models.User;




public interface DigitalBookimpl extends JpaRepository<Book, Long> {
	List<Book> findByCatagoryAndAuthorAndPrice(String Catagory, String Author,Integer price);
	Boolean existsByBookId(Long bookId);
	Book  findByBookId(Long bookId);
	
}
