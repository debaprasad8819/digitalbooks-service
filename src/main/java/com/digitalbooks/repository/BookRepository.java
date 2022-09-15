package com.digitalbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.digitalbooks.models.Book;




public interface BookRepository extends  CrudRepository<Book, Integer>{

}
