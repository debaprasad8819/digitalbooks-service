package com.digitalbooks.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(	name = "refunds")
@Data
public class Refund {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long refundId;
	
	private Long paymentId;
	private Double refundedAmount;
	private Date   refundDate;
	private Integer statusId;
	private String  refundStatus;
	private Long readerId;
	private Long bookId;
	
	public Date getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
	
	
	
	
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public Long getReaderId() {
		return readerId;
	}
	public void setReaderId(Long readerId) {
		this.readerId = readerId;
	}
	
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	
	
	public Long getRefundId() {
		return refundId;
	}
	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public Double getRefundedAmount() {
		return refundedAmount;
	}
	public void setRefundedAmount(Double refundedAmount) {
		this.refundedAmount = refundedAmount;
	}
	
	

}
