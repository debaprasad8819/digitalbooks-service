package com.digitalbooks.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.models.Book;
import com.digitalbooks.models.Payment;
import com.digitalbooks.models.User;
import com.digitalbooks.payload.request.BookRequest;
import com.digitalbooks.services.DigitalBooksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "/*", maxAge = 3600)
@RestController 
@RequestMapping("/api/book")
public class DigitalBooksController  {
	@Autowired 
	DigitalBooksService digitalBooksService; 

	
	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@PostMapping("/createBook") 
	@ResponseStatus(code = HttpStatus.CREATED)
	ResponseEntity saveUser(@Valid @RequestBody Book book) {
		System.out.println("Save method execurted");
		digitalBooksService.save(book);
		
		ResponseEntity responseEntity = new ResponseEntity("Book Created Sucessfully" , HttpStatus.CREATED);
		
		return responseEntity;
	}
	
	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/searchBooks") 
	@ResponseBody
	public ResponseEntity SearchBooks(@RequestParam String catagory,@RequestParam String author,@RequestParam String price) throws JsonProcessingException {
		
		Integer price1=Integer.parseInt(price);
		List<Book> bookList=digitalBooksService.getBookByCatagoryAndAuthorAndPrice(catagory, author, price1);
	   Map<String,String> payload= new HashMap<String,String>();
		bookList.forEach(book->{
			payload.put("author",book.getAuthor());
			payload.put("catagory",book.getCatagory());
			payload.put("publishedDate",book.getPublishedDate());
			payload.put("publisher",book.getPublisher());
			payload.put("title",book.getTitle());
			payload.put("price",book.getPrice().toString());
		});
		

//		String json = new ObjectMapper().writeValueAsString(payload);
//		System.out.println(json);
		ResponseEntity responseEntity = new ResponseEntity(payload , HttpStatus.OK);
		
		return responseEntity;
	}
	
	@PreAuthorize("hasRole('ROLE_READER')")
	@PostMapping("/buyBooks") 
	@ResponseBody
	public ResponseEntity  buyBooks(@Valid @RequestBody BookRequest bookRequst)  {
		
		System.out.println("Hitting the Response entity@@@@@@@@");
		Long price1=Long.parseLong(bookRequst.getBookId());
		Boolean isUserAvailable=digitalBooksService.isUserAvailable(bookRequst.getUsername());
		Boolean isBookAvailable=digitalBooksService.isBookAvailable(price1);
		Map<String,Long> respayload= new HashMap<String,Long>();
		System.out.println("Hitting the Response entity11111111"+isBookAvailable);
		if(isUserAvailable && isBookAvailable) {
			Book book = digitalBooksService.getBookByBookId(price1);
			Optional<User> optional = digitalBooksService.getUserByName(bookRequst.getUsername());
			
			System.out.println("------------------------------");
			System.out.println("Optionlal User::"+optional);
			System.out.println("Book retrive::"+book);
			Payment payment = new Payment();
			User user =optional.get();
			
			payment.setPaymentDate(new Date());
			payment.setPrice(book.getPrice());
			payment.setBookId(book.getBookId());
			payment.setReaderId(user.getId());
			  payment =digitalBooksService.save(payment);
			System.out.println(payment.getBookId()+"-------------------@@@@");
			
			
			respayload.put("pamentId", payment.getPaymentId());
			respayload.put("bookId", payment.getBookId());
			System.out.println("respayload in controller:"+respayload);
		
		}		
		ResponseEntity responseEntity = new ResponseEntity(respayload , HttpStatus.OK);
		
		return responseEntity;
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/allPurchasedBooks/{email}") 
	@ResponseBody
	public ResponseEntity getAllBooks(@PathVariable("email") String email)  {
		
		Optional<User> optional = digitalBooksService.getByEmail(email);
		
		User user =optional.get();
		
		Boolean isReaderPurchased = digitalBooksService.isPaymentAvailableByReaderId(user.getId());
		if(isReaderPurchased) {
			
		}

		Map<String,Set<Long>> bookList=digitalBooksService.getBookId(user.getId());
	  
		ResponseEntity responseEntity = new ResponseEntity(bookList , HttpStatus.OK);
		
		return responseEntity;
	}
	
	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/readers/{email}/books/{bookId}") 
	@ResponseBody
	public ResponseEntity readBooks(@PathVariable("email") String email,@PathVariable("bookId") String bookId) throws JsonProcessingException {
		
		Long bookId1 = Long.parseLong(bookId);
		Map<String,String> mapString = digitalBooksService.readContent(email,bookId1);
	
		ResponseEntity responseEntity = new ResponseEntity(mapString ,HttpStatus.OK);
		
		return responseEntity;
	}
	
	
	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@PutMapping("/editBook") 
	@ResponseBody
	public ResponseEntity updateBook(@RequestBody Book b) throws JsonProcessingException {
		
		//Long bookId1 = Long.parseLong(bookId);
		Map<String,String> mapString = digitalBooksService.readContent(email,bookId1);
	
		ResponseEntity responseEntity = new ResponseEntity(mapString ,HttpStatus.OK);
		
		return responseEntity;
	}
	
	


	
}
