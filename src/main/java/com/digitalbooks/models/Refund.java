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
	private Date  refundDate;
	
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
	public Date getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
	
	

}
