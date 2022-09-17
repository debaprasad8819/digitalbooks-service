package com.digitalbooks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.digitalbooks.models.Book;




public interface BookRepository extends  JpaRepository<Book, Long>{
	List<Book> findByCatagoryAndAuthorAndPrice(String Catagory, String Author,Integer price);
	Boolean existsByBookId(Long bookId);
	Book  findByBookId(Long bookId);

}
