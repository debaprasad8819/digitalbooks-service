package com.digitalbooks.payload.request;

public class RefundRequest {
	private String  paymentId;	
	private String email;
	private String bookId; 
	private Double refundAmount;
	
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public Double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	

}
