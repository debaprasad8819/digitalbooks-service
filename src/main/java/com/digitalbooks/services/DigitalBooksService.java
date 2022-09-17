package com.digitalbooks.services;


import java.util.ArrayList;
import java.util.Date;
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
import com.digitalbooks.models.Refund;
import com.digitalbooks.models.User;
import com.digitalbooks.payload.request.RefundRequest;
import com.digitalbooks.payload.request.UpdateRequest;
import com.digitalbooks.repository.BookRepository;
import com.digitalbooks.repository.DigitalBookimpl;
import com.digitalbooks.repository.PaymentRepository;
import com.digitalbooks.repository.RefundRepository;
import com.digitalbooks.repository.UserRepository;

@Service
public class DigitalBooksService {
	
	@Autowired
	BookRepository repository;
	
//	@Autowired
//	DigitalBookimpl digitalBookimpl;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	RefundRepository refundRepository;
	
	
	//Save the books
		public Book save(Book book) {
				repository.save(book);
			return book;
		}
		
		
		public String updateRequest(UpdateRequest request) {
			Long bookId = Long.parseLong(request.getBookId());
			Book book = getBookByBookId(bookId);
			String msg="";
			Integer price =Integer.parseInt(request.getPrice());
			if(request.getPrice()!=null  ) {
				book.setPrice(price);
			}
			if(request.getCatagory()!=null) {
				book.setCatagory(request.getCatagory());
			}
			if(request.getPrice()!=null) {
				book.setPrice(price);
			}
			if(request.getContent()!=null) {
				book.setContent(request.getContent());
			}
			if(request.getPublisher()!=null) {
				book.setPublisher(request.getPublisher());
			}
			if(request.getTitle()!=null) {
				book.setTitle(request.getTitle());
			}
			if(request.getIsBlocked()!=null) {
				book.setIsBlocked(request.getIsBlocked());
			}
			
			
			book.setBookId(Long.parseLong(request.getBookId()));
			
			
			
			
			//book.setActive(request.getActive());
			Book book1 =save(book);
			if(book1!=null) {
				msg= "updated book successfully";
			}
			return msg;
			
		}
		
		
		//Fetch the books
		public List<Book> getBookByCatagoryAndAuthorAndPrice(String catagory,String author,Integer price) {
		return	repository.findByCatagoryAndAuthorAndPrice(catagory,author,price);
		
	}
		
		//Check the books
		public Boolean isUserAvailable(String userName) {
			Boolean isUserAvailable = userRepository.existsByUsername(userName);
			return isUserAvailable;
		}
		
		//Check the books
				public Boolean isBookAvailable(Long bookId) {
					Boolean isBookAvaiable = repository.existsByBookId(bookId);
					return isBookAvaiable;
				}
				
				//Check the book
						public Book getBookByBookId(Long bookId) {
							return repository.findByBookId(bookId);
						}
						public Optional<User> getUserByName(String username) {
							return userRepository.findByUsername(username);
						}
						
						public Optional<User> getByEmail(String email) {
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
						
						public String paymentRequest(RefundRequest refundRequest) {
							Long paymentid= Long.parseLong(refundRequest.getPaymentId());
							
							Long bookid= Long.parseLong(refundRequest.getBookId());
							
							String msg="";
							
							if(refundRequest.getEmail()!=null && refundRequest.getBookId()!=null && refundRequest.getPaymentId()!=null) {
								Boolean isUser = isUserAvailableByEmail(refundRequest.getEmail());
								if(isUser) {
									Payment payment = getPaymentById(paymentid);
									Long readerid=payment.getReaderId();
									Integer price = payment.getPrice();
									System.out.println("readerid"+readerid+"bookid"+bookid+"paymentid"+paymentid+"refundRequest.getRefundAmount()   "+refundRequest.getRefundAmount());
									 msg=saveRefund(readerid, bookid, paymentid,refundRequest.getRefundAmount());
								}else {
									msg ="User is not available";
								}
							}else {
								msg ="PaymentId,BookId and email Should be mandatory";
							}
							return msg;
						}
						
						
					public String saveRefund(Long readerId,Long bookId,Long paymentId,Double refundAmount) {
						try {
						Integer statusId=2000;
						String status ="Reader requested";
						Refund refund =new Refund();
						refund.setBookId(bookId);
						refund.setPaymentId(paymentId);
						refund.setReaderId(readerId);
						System.out.println("Aftyer readerId@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						refund.setRefundDate(new Date());
						refund.setRefundedAmount(refundAmount);
						refund.setRefundStatus(status);
						System.out.println("After status@@@@@@@@@@@@@@@@@@@@");
						refund.setStatusId(statusId);
						System.out.println(refund+"   Refund object");
						System.out.println(refund.getRefundStatus()+"BookId##"+refund.getBookId()+"paymentId:  "+refund.getPaymentId());
						System.out.println("refundId: "+refund.getReaderId()+"statusId: "+refund.getStatusId()+"staus"+refund.getRefundStatus());
						refundRepository.save(refund);
						}catch(Exception e) {
							e.printStackTrace();
						}
						
						return "Refund request generated successfully";
						
					}

}
