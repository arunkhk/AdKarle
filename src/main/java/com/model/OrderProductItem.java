package com.model;

public class OrderProductItem {
	/*
	 * SELECT
	 * gpim.image_1,gpm.product_name,goim.purchaceQty,goim.unit,goim.unitname,goim.
	 * payableprice,goim.Discount FROM grocer_order_item_master goim LEFT JOIN
	 * grocer_product_image_master gpim ON goim. productId= gpim.product_id LEFT
	 * JOIN grocer_product_master gpm ON gpm.productid=goim.productid WHERE
	 * ordernumber ='AdKarle1'
	 */
	
	
	boolean resCode;
	String msg;
	
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
	
	String productName;
	String image;
	String purchaseQty;
	String unit;
	String unitName;
	String payablePrice;
	String discount;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPurchaseQty() {
		return purchaseQty;
	}
	public void setPurchaseQty(String purchaseQty) {
		this.purchaseQty = purchaseQty;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getPayablePrice() {
		return payablePrice;
	}
	public void setPayablePrice(String payablePrice) {
		this.payablePrice = payablePrice;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	
	
}
