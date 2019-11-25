package com.model;

import java.util.List;
import java.util.Map;

public class GrocerProductItem {
	
	
	String productId;
	String productName;
	String subCatId;
	String description;
	String unitId;
	String unitName;
	String discount;
	
	String actualPrice;
	String payablePrice;
	String productImageThumbnail;
	List<ProductOtherImageItem> imageMap;
	
	
	public List<ProductOtherImageItem> getImageMap() {
		return imageMap;
	}
	public void setImageMap(List<ProductOtherImageItem> imageMap) {
		this.imageMap = imageMap;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSubCatId() {
		return subCatId;
	}
	public void setSubCatId(String subCatId) {
		this.subCatId = subCatId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(String actualPrice) {
		this.actualPrice = actualPrice;
	}
	public String getPayablePrice() {
		return payablePrice;
	}
	public void setPayablePrice(String payablePrice) {
		this.payablePrice = payablePrice;
	}
	public String getProductImageThumbnail() {
		return productImageThumbnail;
	}
	public void setProductImageThumbnail(String productImageThumbnail) {
		this.productImageThumbnail = productImageThumbnail;
	}
	
	
	

}
