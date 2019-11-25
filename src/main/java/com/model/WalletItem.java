package com.model;

public class WalletItem {
	boolean resCode;
	String msg;
	boolean isActive;
	int customerId;
	double walletAmt;
	int walletId;
	String transactionType;
	String transactionDetail;
	String dateTime;
	double amtForUpation;
	
	String transactionId;
	String modeOfPayment;
	

	
	
	
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isResCode() {
		return resCode;
	}
	public void setResCode(boolean resCode) {
		this.resCode = resCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public double getWalletAmt() {
		return walletAmt;
	}
	public void setWalletAmt(double walletAmt) {
		this.walletAmt = walletAmt;
	}
	public int getWalletId() {
		return walletId;
	}
	public void setWalletId(int walletId) {
		this.walletId = walletId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionDetail() {
		return transactionDetail;
	}
	public void setTransactionDetail(String transactionDetail) {
		this.transactionDetail = transactionDetail;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public double getAmtForUpation() {
		return amtForUpation;
	}
	public void setAmtForUpation(double amtForUpation) {
		this.amtForUpation = amtForUpation;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getModeOfPayment() {
		return modeOfPayment;
	}
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
	
	

}
