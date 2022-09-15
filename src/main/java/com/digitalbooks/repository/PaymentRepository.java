package com.digitalbooks.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.digitalbooks.models.Payment;
import com.digitalbooks.models.User;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Boolean existsByReaderId(Long readerId);
	List<Payment> findAllByreaderId(Long readerId);
	Payment  findByPaymentId(Long paymentId);
	

}
