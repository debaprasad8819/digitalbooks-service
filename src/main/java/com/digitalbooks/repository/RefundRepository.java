package com.digitalbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbooks.models.Book;
import com.digitalbooks.models.Refund;

public interface RefundRepository  extends  JpaRepository<Refund, Long>{

	
	
	//Boolean existsByPaymentIdAndBookIdAnd();

}
