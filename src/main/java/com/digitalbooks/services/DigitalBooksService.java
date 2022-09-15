package com.digitalbooks.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbooks.models.Book;
import com.digitalbooks.models.Payment;
import com.digitalbooks.models.User;
import com.digitalbooks.repository.BookRepository;
import com.digitalbooks.repository.DigitalBookimpl;
import com.digitalbooks.repository.PaymentRepository;
import com.digitalbooks.repository.UserRepository;


@Service
public class DigitalBooksService {
	@Autowired
	BookRepository repository;
	
	@Autowired
	DigitalBookimpl digitalBookimpl;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PaymentRepository paymentRepository;

	
	//Save the books
	public Book save(Book book) {
			repository.save(book);
		return book;
	}
	
	//Fetch the books
	public List<Book> getBookByCatagoryAndAuthorAndPrice(String catagory,String author,Integer price) {
	return	digitalBookimpl.findByCatagoryAndAuthorAndPrice(catagory,author,price);
	
}
	
	//Check the books
	public Boolean isUserAvailable(String userName) {
		Boolean isUserAvailable = userRepository.existsByUsername(userName);
		return isUserAvailable;
	}

	//Check the books
		public Boolean isBookAvailable(Long bookId) {
			Boolean isBookAvaiable = digitalBookimpl.existsByBookId(bookId);
			return isBookAvaiable;
		}
		
		//Check the book
				public Book getBookByBookId(Long bookId) {
					return digitalBookimpl.findByBookId(bookId);
				}
				public Optional<User> getUserByName(String username) {
					return userRepository.findByUsername(username);
				}
				
				public Optional<User> getByEmail(String email) {
					System.out.println("getByEmail in string email"+email);
					System.out.println("getByEmail in string email"+userRepository.findByEmail(email));
					return userRepository.findByEmail(email);
				}
				
				public Payment save(Payment payment) {
					return paymentRepository.save(payment);
				
			}
				
				//Check the books
				public Boolean  isPaymentAvailableByReaderId(Long readerid) {
					Boolean paymentAvaible = paymentRepository.existsByReaderId(readerid);
					return paymentAvaible;
				}
				
				public Map<String,Set<Long>>  getBookId(Long readerid) {
					List<Payment> paymentList = paymentRepository.findAllByreaderId(readerid);
					Set<Long>  bookIdList = new HashSet<Long>();
					Map<String,Set<Long>> map = new HashMap<String,Set<Long>>();
					paymentList.forEach(payment->{
						bookIdList.add(payment.getBookId());
					});
					map.put("bookId", bookIdList);
					   return map;
				}
				
				public Boolean isUserAvailableByEmail(String email) {
					return userRepository.existsByEmail(email);
					
				}

				public Map<String,String> readContent(String email,Long bookId) {
					Boolean isuser =isUserAvailableByEmail(email);
					Map<String,String> map = new HashMap<String,String>();
					if(isuser) {
						Book book =  getBookByBookId(bookId); 
						System.out.println("book is generated::"+book.getAuthor());
						map.put("catagory", book.getCatagory());
						map.put("content", book.getContent());
						map.put("Author", book.getAuthor());
					}
					return map;	
				}
				
				public Payment getPaymentById(Long paymentId) {
					Payment payment=paymentRepository.findByPaymentId(paymentId);
					return payment;
				}
				
				public Map<String,String> findBookByPaymentId(String email,Long payemntId) {
					Boolean isuser = isUserAvailableByEmail(email);
					Map<String,String> payload = new HashMap<String,String>();
					if(isuser) {
						Payment payment = getPaymentById(payemntId); 
						Long bookId = payment.getBookId();
						Book book =  getBookByBookId(bookId); 
						payload.put("author",book.getAuthor());
						payload.put("catagory",book.getCatagory());
						payload.put("publishedDate",book.getPublishedDate());
						payload.put("publisher",book.getPublisher());
						payload.put("title",book.getTitle());
						payload.put("price",book.getPrice().toString());
					}
					return payload;	
				}
}
