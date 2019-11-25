package com.dao.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.model.AddCartItem;
import com.model.AppUpdate;
import com.model.CartItem;
import com.model.CityItem;
import com.model.ConstantItem;
import com.model.CouponItem;
import com.model.Employee;
import com.model.GetCategoryItem;
import com.model.GrocerProductItem;
import com.model.LoginItem;
import com.model.MenuImage;
import com.model.OrderItem;
import com.model.OrderProductItem;
import com.model.ProductOtherImageItem;
import com.model.RegisterUserItem;
import com.model.SaveOrderitem;
import com.model.SearchProductItem;
import com.model.AdminModel;
import com.model.VendorDetailItem;
import com.model.WalletItem;

import java.sql.CallableStatement;
import com.util.AppConstant;
import com.util.SendGmailMail;

public class Dao {
	String mailId;
	String userName;
	String pwd = "";
	String mobileNo = "";
	String address = "";

	ResultSet rs = null;
	String res = "";

	Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(AppConstant.SQL_SERVER_DRIVER);
			con = DriverManager.getConnection(AppConstant.DATABASE_NAME);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String testConnection() {
		Connection con = getConnection();
		String SQLq = "select * from  admin where username=?";
		try {
			PreparedStatement pstmtq = con.prepareStatement(SQLq);
			pstmtq.setString(1, "delvetech");
			rs = pstmtq.executeQuery();
			while (rs.next()) {
				res = rs.getString(3);

			}

			System.out.println(" name " + res);
		} catch (Exception exception) {
			System.out.println(" exception " + exception);
		}

		return res;
	}

	public List<GetCategoryItem> getCategory() throws SQLException {
		Connection con = getConnection();
		PreparedStatement pstmtq = null;
		String SQLq = "SELECT * FROM category";
		List<GetCategoryItem> caList = new ArrayList<GetCategoryItem>();
		try {
			pstmtq = con.prepareStatement(SQLq);
			rs = pstmtq.executeQuery();
			while (rs.next()) {

				GetCategoryItem categoryItem = new GetCategoryItem();
				categoryItem.setCategoryId(rs.getString(1));
				categoryItem.setCategoryName(rs.getString(2));
				caList.add(categoryItem);
			}
			System.out.println(" List Size " + caList.size());
		} catch (Exception exception) {
			System.out.println(" exception " + exception);
		} finally {
			pstmtq.close();
			con.close();
		}
		return caList;
	}

	/* API Get City */

	public List<VendorDetailItem> getVendorDetail(int categoryId, int cityId) throws SQLException {
		Connection con = getConnection();
		PreparedStatement pstmtq = null;

		/*
		 * SELECT c.*,v.mobile FROM category_details c LEFT JOIN vendor v ON c.vendor_id
		 * = v.vendor_id WHERE category_id=37 AND city_id=3
		 */

		String SQLq = "SELECT c.*,v.mobile FROM category_details c LEFT JOIN vendor v ON c.vendor_id = v.vendor_id WHERE category_id=? AND city_id=?";
		List<VendorDetailItem> caList = new ArrayList<VendorDetailItem>();
		try {
			pstmtq = con.prepareStatement(SQLq);
			pstmtq.setInt(1, categoryId);
			pstmtq.setInt(2, cityId);
			rs = pstmtq.executeQuery();
			while (rs.next()) {

				VendorDetailItem vendorDetailItem = new VendorDetailItem();
				vendorDetailItem.setResCode(true);
				vendorDetailItem.setVendorId(rs.getInt(1));
				vendorDetailItem.setCategortId(rs.getInt(2));
				vendorDetailItem.setTitle(rs.getString(3));

				vendorDetailItem.setStreet(rs.getString(4) + " " + rs.getString(7));
				vendorDetailItem.setCity(rs.getString(5));
				vendorDetailItem.setState(rs.getString(6));
				vendorDetailItem.setDiscription(rs.getString(9));
				vendorDetailItem.setImage(AppConstant.IMAGE_URL + rs.getString(12));
				vendorDetailItem.setRating(rs.getInt(13));
				vendorDetailItem.setMobileNumber(rs.getString(15));
				caList.add(vendorDetailItem);
			}
			System.out.println(" List Size " + caList.size());
		} catch (Exception exception) {
			System.out.println(" exception " + exception);
		} finally {
			pstmtq.close();
			con.close();
		}
		return caList;
	}

	public List<CouponItem> getCoupon(int vendorId) throws SQLException {
		Connection con = getConnection();
		PreparedStatement pstmtq = null;
		String SQLq = "SELECT * FROM coupon_management WHERE VendorId=?";
		List<CouponItem> caList = new ArrayList<CouponItem>();
		try {
			pstmtq = con.prepareStatement(SQLq);
			pstmtq.setInt(1, vendorId);
			rs = pstmtq.executeQuery();
			while (rs.next()) {

				CouponItem vendorDetailItem = new CouponItem();
				vendorDetailItem.setResCode(true);

				vendorDetailItem.setCouponId(rs.getInt(1));
				// vendorDetailItem.setCategortId(rs.getInt(2));
				vendorDetailItem.setTitle(rs.getString(3));
				vendorDetailItem.setCouponName(rs.getString(4));
				vendorDetailItem.setDescription(rs.getString(5));

				vendorDetailItem.setValidDate(rs.getString(6));
				vendorDetailItem.setCouponValue(rs.getString(7));
				vendorDetailItem.setDiscount(rs.getString(8));
				vendorDetailItem.setMinimumAmount(rs.getInt(9));

				vendorDetailItem.setLocation(rs.getString(10));
				vendorDetailItem.setVendorId(rs.getInt(11));
				vendorDetailItem.setImage(AppConstant.COUPON_URL + rs.getString(12));
				vendorDetailItem.setRating(rs.getInt(13));
				caList.add(vendorDetailItem);
			}
			System.out.println(" List Size " + caList.size());
		} catch (Exception exception) {
			System.out.println(" exception " + exception);
		} finally {
			pstmtq.close();
			con.close();
		}
		return caList;
	}

	@SuppressWarnings("resource")
	public ConstantItem insertUser(RegisterUserItem item) {
		Connection con = getConnection();
		PreparedStatement pstmtq = null;
		ConstantItem caList = new ConstantItem();

		String email = "";

		try {
			String SQLq = "select * from  users where email_id=?";
			pstmtq = con.prepareStatement(SQLq);
			pstmtq.setString(1, item.getEmail_id());
			rs = pstmtq.executeQuery();

			while (rs.next()) {
				email = rs.getString(4);
			}

			if (email.equalsIgnoreCase("") || email.equals(null)) {

				SQLq = "INSERT INTO users (user_name, password,email_id,mobile_number) VALUES(?,?,?,?)";
				pstmtq = con.prepareStatement(SQLq);
				pstmtq.setString(1, item.getUser_name());
				pstmtq.setString(2, item.getPassword());
				pstmtq.setString(3, item.getEmail_id());
				pstmtq.setString(4, item.getMobile_number());
				pstmtq.executeUpdate();

				caList.setMsg("User registered successfully");
				caList.setResCode(AppConstant.S_CODE);

			} else {
				caList.setMsg("Email Id already registered ");
				caList.setResCode(AppConstant.F_CODE);
			}

		} catch (Exception e) {
			caList.setMsg("Server error");
			caList.setResCode(AppConstant.F_CODE);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return caList;
	}

	public List<LoginItem> doLogin(LoginItem item) {
		Connection con = getConnection();
		PreparedStatement pstmtq = null;
		List<LoginItem> loginItems = new ArrayList<LoginItem>();
		try {
			String SQL = "select * from  users where email_id=? and password=?";
			pstmtq = con.prepareStatement(SQL);
			pstmtq.setString(1, item.getEmailId());
			pstmtq.setString(2, item.getPassword());
			rs = pstmtq.executeQuery();

			while (rs.next()) {
				LoginItem loginItem = new LoginItem();
				loginItem.setResCode(true);
				loginItem.setUserID(rs.getInt(1));
				loginItem.setUserName(rs.getString(2));
				loginItem.setPassword(rs.getString(3));
				loginItem.setEmailId(rs.getString(4));
				loginItem.setImage(rs.getString(6));
				loginItem.setMobileNumber(rs.getString(7));
				loginItems.add(loginItem);
			}
		} catch (Exception e) {
			LoginItem loginItem = new LoginItem();
			loginItem.setResCode(false);
			loginItems.add(loginItem);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				LoginItem loginItem = new LoginItem();
				loginItem.setResCode(false);
				loginItems.add(loginItem);
				e.printStackTrace();
			}
		}
		return loginItems;

	}

	@SuppressWarnings("resource")
	public ConstantItem changePassword(RegisterUserItem item) {
		Connection con = getConnection();
		PreparedStatement pstmtq = null;
		ConstantItem caList = new ConstantItem();

		String email = "";

		try {
			String SQLq = "select * from  users where email_id=?";
			pstmtq = con.prepareStatement(SQLq);
			pstmtq.setString(1, item.getEmail_id());
			rs = pstmtq.executeQuery();

			while (rs.next()) {
				email = rs.getString(4);
			}

			if (!email.equalsIgnoreCase("")) {
				SQLq = "UPDATE users SET PASSWORD =? WHERE email_id=?";
				pstmtq = con.prepareStatement(SQLq);
				pstmtq.setString(1, item.getPassword());
				pstmtq.setString(2, item.getEmail_id());
				pstmtq.executeUpdate();

				caList.setMsg("Password has been changed successfully");
				caList.setResCode(AppConstant.S_CODE);

			} else {
				caList.setMsg("User not found");
				caList.setResCode(AppConstant.F_CODE);
			}

		} catch (Exception e) {
			caList.setMsg("Server error");
			caList.setResCode(AppConstant.F_CODE);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return caList;
	}

	public List<VendorDetailItem> getNewOffer() throws SQLException {
		Connection con = getConnection();
		PreparedStatement pstmtq = null;
		String SQLq = "SELECT c.*,v.mobile FROM category_details c LEFT JOIN vendor v ON c.vendor_id = v.vendor_id  ORDER BY created_date DESC";
		List<VendorDetailItem> caList = new ArrayList<VendorDetailItem>();
		try {
			pstmtq = con.prepareStatement(SQLq);
			rs = pstmtq.executeQuery();
			while (rs.next()) {

				VendorDetailItem vendorDetailItem = new VendorDetailItem();
				vendorDetailItem.setResCode(true);
				vendorDetailItem.setVendorId(rs.getInt(1));
				vendorDetailItem.setCategortId(rs.getInt(2));
				vendorDetailItem.setTitle(rs.getString(3));

				vendorDetailItem.setStreet(rs.getString(4) + " " + rs.getString(7));
				vendorDetailItem.setCity(rs.getString(5));
				vendorDetailItem.setState(rs.getString(6));
				vendorDetailItem.setDiscription(rs.getString(9));
				vendorDetailItem.setImage(AppConstant.IMAGE_URL + rs.getString(12));
				vendorDetailItem.setRating(rs.getInt(13));
				vendorDetailItem.setMobileNumber(rs.getString(15));
				caList.add(vendorDetailItem);
			}
			System.out.println(" List Size " + caList.size());
		} catch (Exception exception) {
			System.out.println(" exception " + exception);
		} finally {
			pstmtq.close();
			con.close();
		}
		return caList;
	}

	public List<VendorDetailItem> getTopRated() throws SQLException {
		Connection con = getConnection();
		PreparedStatement pstmtq = null;
		String SQLq = "SELECT c.*,v.mobile FROM category_details c LEFT JOIN vendor v ON c.vendor_id = v.vendor_id  ORDER BY rating DESC";
		List<VendorDetailItem> caList = new ArrayList<VendorDetailItem>();
		try {
			pstmtq = con.prepareStatement(SQLq);
			rs = pstmtq.executeQuery();
			while (rs.next()) {

				VendorDetailItem vendorDetailItem = new VendorDetailItem();
				vendorDetailItem.setResCode(true);
				vendorDetailItem.setVendorId(rs.getInt(1));
				vendorDetailItem.setCategortId(rs.getInt(2));
				vendorDetailItem.setTitle(rs.getString(3));

				vendorDetailItem.setStreet(rs.getString(4) + " " + rs.getString(7));
				vendorDetailItem.setCity(rs.getString(5));
				vendorDetailItem.setState(rs.getString(6));
				vendorDetailItem.setDiscription(rs.getString(9));
				vendorDetailItem.setImage(AppConstant.IMAGE_URL + rs.getString(12));
				vendorDetailItem.setRating(rs.getInt(13));
				vendorDetailItem.setMobileNumber(rs.getString(15));
				caList.add(vendorDetailItem);
			}
			System.out.println(" List Size " + caList.size());
		} catch (Exception exception) {
			System.out.println(" exception " + exception);
		} finally {
			pstmtq.close();
			con.close();
		}
		return caList;
	}

	public List<VendorDetailItem> getByArea(int cityId, int categoryId, String Value) throws SQLException {
		Connection con = getConnection();
		PreparedStatement pstmtq = null;
		String SQLq = "SELECT c.*,v.mobile FROM category_details c LEFT JOIN vendor v ON c.vendor_id = v.vendor_id WHERE city_id=? AND  category_id=? AND street LIKE '%"
				+ Value + "%'";
		List<VendorDetailItem> caList = new ArrayList<VendorDetailItem>();
		try {
			pstmtq = con.prepareStatement(SQLq);
			pstmtq.setInt(1, cityId);
			pstmtq.setInt(2, categoryId);
			rs = pstmtq.executeQuery();
			while (rs.next()) {

				VendorDetailItem vendorDetailItem = new VendorDetailItem();
				vendorDetailItem.setResCode(true);
				vendorDetailItem.setVendorId(rs.getInt(1));
				vendorDetailItem.setCategortId(rs.getInt(2));
				vendorDetailItem.setTitle(rs.getString(3));

				vendorDetailItem.setStreet(rs.getString(4) + " " + rs.getString(7));
				vendorDetailItem.setCity(rs.getString(5));
				vendorDetailItem.setState(rs.getString(6));
				vendorDetailItem.setDiscription(rs.getString(9));
				vendorDetailItem.setImage(AppConstant.IMAGE_URL + rs.getString(12));
				vendorDetailItem.setRating(rs.getInt(13));
				vendorDetailItem.setMobileNumber(rs.getString(15));
				caList.add(vendorDetailItem);
			}
			System.out.println(" List Size " + caList.size());
		} catch (Exception exception) {
			System.out.println(" exception " + exception);
		} finally {
			pstmtq.close();
			con.close();
		}
		return caList;
	}

	public List<MenuImage> getMenuImage(int vendorId) throws SQLException {
		Connection con = getConnection();
		PreparedStatement pstmtq = null;
		String SQLq = "SELECT * FROM menu_image WHERE category=?";
		List<MenuImage> caList = new ArrayList<MenuImage>();
		try {
			pstmtq = con.prepareStatement(SQLq);
			pstmtq.setInt(1, vendorId);
			rs = pstmtq.executeQuery();
			while (rs.next()) {

				MenuImage menuImage = new MenuImage();
				menuImage.setResCode(true);
				menuImage.setId(rs.getInt(1));
				menuImage.setImage(AppConstant.IMAGE_URL + rs.getString(2));

				caList.add(menuImage);
			}
			System.out.println(" List Size " + caList.size());
		} catch (Exception exception) {
			System.out.println(" exception " + exception);
		} finally {
			pstmtq.close();
			con.close();
		}
		return caList;
	}

	/*
	 * Groces DAO
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * ADDED BY ARUN
	 */

	public List<GetCategoryItem> getGrocersCategory(String imagePath) throws SQLException {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		String SQLq = "{ call getAllCategory() }";

		List<GetCategoryItem> caList = new ArrayList<GetCategoryItem>();
		try {
			pstmtq = (CallableStatement) con.prepareCall(SQLq);
			rs = pstmtq.executeQuery();
			while (rs.next()) {

				GetCategoryItem categoryItem = new GetCategoryItem();
				categoryItem.setCategoryId(rs.getString(1));
				categoryItem.setCategoryName(rs.getString(2));
				categoryItem.setStoreId(rs.getString(3));
				categoryItem.setCategoryDescription(rs.getString(4));
				categoryItem.setCategoryImage(imagePath + rs.getString(5));
				caList.add(categoryItem);
			}
			System.out.println(" List Size " + caList.size());
		} catch (Exception exception) {
			System.out.println(" exception " + exception);
		} finally {
			pstmtq.close();
			con.close();
		}
		return caList;
	}

	public List<GetCategoryItem> getSubGrocersCategory(String parentCatID, String imagePath) throws SQLException {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		String SQLq = "{call getSubCategory(?)}";

		List<GetCategoryItem> caList = new ArrayList<GetCategoryItem>();
		try {
			pstmtq = con.prepareCall(SQLq);
			pstmtq.setInt(1, Integer.parseInt(parentCatID));
			rs = pstmtq.executeQuery();
			while (rs.next()) {

				GetCategoryItem categoryItem = new GetCategoryItem();
				categoryItem.setCategoryId(rs.getString(1));
				categoryItem.setCategoryName(rs.getString(3));
				categoryItem.setCategoryImage(imagePath + rs.getString(4));
				categoryItem.setDisount(rs.getString(5));
				caList.add(categoryItem);
			}
			System.out.println(" List Size " + caList.size());
		} catch (Exception exception) {
			System.out.println(" exception " + exception);
		} finally {
			pstmtq.close();
			con.close();
		}
		return caList;
	}

	public List<GrocerProductItem> getGrocerProduct(String subCatID, String imagePath) throws SQLException {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		String SQLq = "{call getGrocerProduct(?)}";

		List<GrocerProductItem> caList = new ArrayList<GrocerProductItem>();
		try {
			pstmtq = con.prepareCall(SQLq);
			pstmtq.setInt(1, Integer.parseInt(subCatID));
			rs = pstmtq.executeQuery();
			List<ProductOtherImageItem> mapImage = new ArrayList<ProductOtherImageItem>();
			while (rs.next()) {

				GrocerProductItem productItem = new GrocerProductItem();
				productItem.setProductId(rs.getString(1));
				productItem.setProductName(rs.getString(2));
				productItem.setSubCatId(rs.getString(3));
				productItem.setDescription(rs.getString(4));
				productItem.setUnitId(rs.getString(5));
				productItem.setUnitName(rs.getString(6));
				productItem.setDiscount(rs.getString(7));
				productItem.setActualPrice(rs.getString(8));
				productItem.setPayablePrice(rs.getString(9));
				productItem.setProductImageThumbnail(imagePath + rs.getString(10));

				ProductOtherImageItem imageItem = new ProductOtherImageItem();
				/*
				 * mapImage.put("image", imagePath+rs.getString(12)); mapImage.put("image1",
				 * imagePath+rs.getString(14)); mapImage.put("image2",
				 * imagePath+rs.getString(15)); mapImage.put("image3",
				 * imagePath+rs.getString(16));
				 */

				imageItem.setImage(imagePath + rs.getString(12));
				imageItem.setImage1(imagePath + rs.getString(14));
				imageItem.setImage2(imagePath + rs.getString(15));
				imageItem.setImage3(imagePath + rs.getString(16));
				mapImage.add(imageItem);

				productItem.setImageMap(mapImage);
				caList.add(productItem);
			}
			System.out.println(" List Size " + caList.size());
		} catch (Exception exception) {
			System.out.println(" exception " + exception);
		} finally {
			pstmtq.close();
			con.close();
		}
		return caList;
	}

	public List<LoginItem> grocerDoLogin(LoginItem item, String userImagePath) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		String SQLq = "{call doGrocerLogin(?,?)}";

		List<LoginItem> loginItems = new ArrayList<LoginItem>();
		try {

			pstmtq = con.prepareCall(SQLq);
			pstmtq.setString(1, item.getMobileNumber());
			pstmtq.setString(2, item.getPassword());
			rs = pstmtq.executeQuery();

			while (rs.next()) {
				LoginItem loginItem = new LoginItem();
				loginItem.setResCode(true);
				loginItem.setUserID(rs.getInt(1));
				loginItem.setUserName(rs.getString(2));
				loginItem.setMobileNumber(rs.getString(3));
				loginItem.setEmailId(rs.getString(4));
				loginItem.setPassword(rs.getString(5));
				loginItem.setImage(userImagePath + rs.getString(6));

				loginItem.setAddress(rs.getString(8));
				loginItem.setCity(rs.getString(9));
				loginItem.setState(rs.getString(10));
				loginItem.setPinCode(rs.getString(11));
				loginItem.setLandmark(rs.getString(12));
				loginItem.setAddressType(rs.getString(13));
				loginItem.setShippingAddress(rs.getBoolean(14));
				loginItems.add(loginItem);
			}
		} catch (Exception e) {
			LoginItem loginItem = new LoginItem();
			loginItem.setResCode(false);
			loginItems.add(loginItem);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				LoginItem loginItem = new LoginItem();
				loginItem.setResCode(false);
				loginItems.add(loginItem);
				e.printStackTrace();
			}
		}
		return loginItems;

	}

	public List<ConstantItem> addCartItem(AddCartItem item) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		String SQLq = "{call addCartItem(?,?,?,?,?)}";
		String statusCode = "";
		ConstantItem ConstantItem = new ConstantItem();
		List<ConstantItem> ConstantItemList = new ArrayList<ConstantItem>();
		try {

			pstmtq = con.prepareCall(SQLq);
			pstmtq.setInt(1, Integer.parseInt(item.getProductID()));
			pstmtq.setString(2, item.getProductName());
			pstmtq.setInt(3, Integer.parseInt(item.getProductQty()));
			pstmtq.setInt(4, Integer.parseInt(item.getUserID()));
			pstmtq.setString(5, item.getUserMobile());
			rs = pstmtq.executeQuery();

			while (rs.next()) {
				statusCode = rs.getString(1);
				String msg = rs.getString(2);
				System.out.println(" Add item  " + statusCode + "------------- " + msg);
			}

			if (statusCode.equalsIgnoreCase("101")) {
				ConstantItem.setResCode(true);
				ConstantItem.setMsg("Data Saved Successfully");
				ConstantItemList.add(ConstantItem);
			} else {
				ConstantItem.setResCode(false);
				ConstantItem.setMsg("Data Not Saved");
				ConstantItemList.add(ConstantItem);
			}

		} catch (Exception e) {
			ConstantItem.setResCode(false);
			ConstantItem.setMsg("Data Not Saved");
			ConstantItemList.add(ConstantItem);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				ConstantItem.setResCode(false);
				ConstantItem.setMsg("Data Not Saved");
				ConstantItemList.add(ConstantItem);
				e.printStackTrace();
			}
		}
		return ConstantItemList;

	}

	public List<CartItem> getCartItem(String userId, String imagePath) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		List<CartItem> caList = new ArrayList<CartItem>();
		String SQLq = "{call getUserCartItem(?)}";
		try {
			pstmtq = con.prepareCall(SQLq);
			pstmtq.setInt(1, Integer.parseInt(userId));
			rs = pstmtq.executeQuery();
			while (rs.next()) {
				CartItem productItem = new CartItem();
				productItem.setResCode(true);
				productItem.setMsg("Cart Data");
				productItem.setProductQty(rs.getString(4));
				productItem.setProductId(rs.getString(7));
				productItem.setProductName(rs.getString(8));
				productItem.setSubCatId(rs.getString(9));
				productItem.setDescription(rs.getString(10));
				productItem.setUnitId(rs.getString(11));
				productItem.setUnitName(rs.getString(12));
				productItem.setDiscount(rs.getString(13));
				productItem.setActualPrice(rs.getString(14));
				productItem.setPayablePrice(rs.getString(15));
				productItem.setProductImageThumbnail(imagePath + rs.getString(16));
				caList.add(productItem);
			}
			System.out.println(" List Size " + caList.size());
		} catch (Exception exception) {
			CartItem productItem = new CartItem();
			productItem.setResCode(false);
			productItem.setMsg("Server Error");
			System.out.println(" exception " + exception);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return caList;

	}

	public List<ConstantItem> removeCartItem(AddCartItem item, int clearCartValue) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;

		/* third parameter value is 4 is for clear cart when user place their order */
		String SQLq = "{call removeCartItem(?,?,?)}";

		ConstantItem ConstantItem = new ConstantItem();
		List<ConstantItem> ConstantItemList = new ArrayList<ConstantItem>();
		try {

			pstmtq = con.prepareCall(SQLq);
			pstmtq.setInt(1, Integer.parseInt(item.getUserID()));
			pstmtq.setInt(2, Integer.parseInt(item.getProductID()));
			pstmtq.setInt(3, clearCartValue);
			pstmtq.executeQuery();

			ConstantItem.setResCode(true);
			ConstantItem.setMsg("Item removed successfully");
			ConstantItemList.add(ConstantItem);

		} catch (Exception e) {
			ConstantItem.setResCode(false);
			ConstantItem.setMsg("Item not removed");
			ConstantItemList.add(ConstantItem);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				ConstantItem.setResCode(false);
				ConstantItem.setMsg("Item not removed");
				ConstantItemList.add(ConstantItem);
				e.printStackTrace();
			}
		}
		return ConstantItemList;

	}

	public List<ConstantItem> updateItemQuantity(AddCartItem item) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		String SQLq = "{call updateItemQty(?,?,?)}";

		ConstantItem ConstantItem = new ConstantItem();
		List<ConstantItem> ConstantItemList = new ArrayList<ConstantItem>();
		try {

			pstmtq = con.prepareCall(SQLq);
			pstmtq.setInt(1, Integer.parseInt(item.getUserID()));
			pstmtq.setInt(2, Integer.parseInt(item.getProductID()));
			pstmtq.setInt(3, Integer.parseInt(item.getProductQty()));
			pstmtq.executeQuery();

			ConstantItem.setResCode(true);
			ConstantItem.setMsg("Item added successfully");
			ConstantItemList.add(ConstantItem);

		} catch (Exception e) {
			ConstantItem.setResCode(false);
			ConstantItem.setMsg("Item not added");
			ConstantItemList.add(ConstantItem);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				ConstantItem.setResCode(false);
				ConstantItem.setMsg("Item not added");
				ConstantItemList.add(ConstantItem);
				e.printStackTrace();
			}
		}
		return ConstantItemList;

	}

	public List<CityItem> getCity(String city) throws SQLException {
		Connection con = getConnection();
		CallableStatement pstmtq = null;

		System.out.println("city Dao " + city);
		String SQLq = "{call getCity(?)}";
		List<CityItem> caList = new ArrayList<CityItem>();
		try {
			pstmtq = con.prepareCall(SQLq);
			pstmtq.setString(1, city);
			rs = pstmtq.executeQuery();
			while (rs.next()) {
				CityItem cityItem = new CityItem();
				cityItem.setCityId(rs.getInt(1));
				cityItem.setCityName(rs.getString(2));
				cityItem.setStateId(rs.getInt(3));
				cityItem.setStateName(rs.getString(5));
				caList.add(cityItem);
			}
			System.out.println(" List Size " + caList.size());
		} catch (Exception exception) {
			System.out.println(" exception " + exception);
		} finally {
			pstmtq.close();
			con.close();
		}
		return caList;
	}

	public List<ConstantItem> addUpdateUser(final LoginItem item) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		String SQLq = "{call addGrocerUser(?,?,?,?)}";

		ConstantItem ConstantItem = new ConstantItem();
		List<ConstantItem> ConstantItemList = new ArrayList<ConstantItem>();
		try {

			pstmtq = con.prepareCall(SQLq);
			pstmtq.setString(1, item.getUserName());
			pstmtq.setString(2, item.getMobileNumber());
			pstmtq.setString(3, item.getEmailId());
			pstmtq.setString(4, item.getPassword());
			pstmtq.executeQuery();

			ExecutorService emailExecutor = Executors.newCachedThreadPool();
			emailExecutor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						String msg = "Dear " + item.getUserName()
								+ " Your login crediantial secussfully created. Your login ID is "
								+ item.getMobileNumber() + " and password is : " + item.getPassword()
								+ "\nKeep in touch with us ! thanks";
						SendGmailMail.sendMail("AdKarle bazaar login crediantial", msg, item.getEmailId());
					} catch (Exception e) {
						System.out.println("mail SendIng exception: " + e.toString());
					}
				}
			});

			ConstantItem.setResCode(true);
			ConstantItem.setMsg("User added successfully");
			ConstantItemList.add(ConstantItem);

		} catch (Exception e) {
			ConstantItem.setResCode(false);
			ConstantItem.setMsg("User not added");
			ConstantItemList.add(ConstantItem);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				ConstantItem.setResCode(false);
				ConstantItem.setMsg("User not added");
				ConstantItemList.add(ConstantItem);
				e.printStackTrace();
			}
		}
		return ConstantItemList;

	}

	public List<ConstantItem> addChangeAddressGrocer(LoginItem item) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		String SQLq = "{call addChangeAddress(?,?,?,?,?,?)}";

		ConstantItem ConstantItem = new ConstantItem();
		List<ConstantItem> ConstantItemList = new ArrayList<ConstantItem>();
		try {
			pstmtq = con.prepareCall(SQLq);
			pstmtq.setInt(1, (item.getUserID()));
			pstmtq.setString(2, item.getAddress());
			pstmtq.setString(3, item.getCity());
			pstmtq.setString(4, item.getState());
			pstmtq.setString(5, item.getPinCode());
			pstmtq.setString(6, item.getLandmark());
			pstmtq.executeQuery();

			ConstantItem.setResCode(true);
			ConstantItem.setMsg("Address changed successfully");
			ConstantItemList.add(ConstantItem);

		} catch (Exception e) {
			ConstantItem.setResCode(false);
			ConstantItem.setMsg("Address not added");
			ConstantItemList.add(ConstantItem);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				ConstantItem.setResCode(false);
				ConstantItem.setMsg("Address not added");
				ConstantItemList.add(ConstantItem);
				e.printStackTrace();
			}
		}
		return ConstantItemList;

	}

	public List<SearchProductItem> getSearchProduct(String productName, String imagePath) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		List<SearchProductItem> caList = new ArrayList<SearchProductItem>();
		String SQLq = "{call getSearchProduct(?)}";
		try {
			pstmtq = con.prepareCall(SQLq);
			pstmtq.setString(1, productName);
			rs = pstmtq.executeQuery();
			while (rs.next()) {
				SearchProductItem productItem = new SearchProductItem();

				productItem.setResCode(true);
				productItem.setMsg("Product Data");
				productItem.setProductId(rs.getString(1));
				productItem.setProductName(rs.getString(2));
				productItem.setSubCatId(rs.getString(3));
				productItem.setDescription(rs.getString(4));
				productItem.setProductQty(rs.getString(5));
				productItem.setUnitName(rs.getString(6));
				productItem.setDiscount(rs.getString(7));
				productItem.setActualPrice(rs.getString(8));
				productItem.setPayablePrice(rs.getString(9));
				productItem.setProductImage(imagePath + rs.getString(10));

				productItem.setImage1(imagePath + rs.getString(14));
				productItem.setImage2(imagePath + rs.getString(15));
				productItem.setImage3(imagePath + rs.getString(16));

				caList.add(productItem);
			}
			System.out.println(" List Size " + caList.size());
		} catch (Exception exception) {
			SearchProductItem productItem = new SearchProductItem();
			productItem.setResCode(false);
			productItem.setMsg("Server Error");
			System.out.println(" exception " + exception);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return caList;

	}

	@SuppressWarnings("resource")
	public ConstantItem saveOrder(final SaveOrderitem orderitem, List<SaveOrderitem> list) {
		Connection con = getConnection();
		ConstantItem productItem = new ConstantItem();
		CallableStatement pstmtq = null;
		String statusCode = "";
		String msg = "";

		System.out.println(" List Size " + list.size());

		// String SQLq = "{call saveOrderDetails(?,?,?,?,?,?,?,?,?,?)}";
		String SQLq = "{call purchacedOrderItem(?,?,?,?,?,?,?)}";

		try {
			pstmtq = con.prepareCall(SQLq);
			for (int i = 0; i < list.size(); i++) {
				SaveOrderitem saveOrderitem = list.get(i);
				pstmtq.setString(1, saveOrderitem.getProductId());
				pstmtq.setString(2, saveOrderitem.getUnit());
				pstmtq.setString(3, saveOrderitem.getUnit());
				pstmtq.setString(4, saveOrderitem.getUnitName());
				pstmtq.setString(5, saveOrderitem.getPayablePrice());
				pstmtq.setString(6, saveOrderitem.getDiscount());
				pstmtq.setString(7, orderitem.getOrderNo());
				rs = pstmtq.executeQuery();

				while (rs.next()) {
					statusCode = rs.getString(1);
					msg = rs.getString(2);
					System.out.println("purchacedOrderItem  " + statusCode + " " + msg);
				}
			}

			if (statusCode.equalsIgnoreCase("101")) {
				SQLq = "{call saveOrderDetails(?,?,?,?,?,?,?,?,?,?)}";
				pstmtq = con.prepareCall(SQLq);
				pstmtq.setString(1, orderitem.getUseId());
				pstmtq.setString(2, orderitem.getProductId());
				pstmtq.setString(3, orderitem.getOrderNo());
				pstmtq.setString(4, orderitem.getPaymentMode());
				pstmtq.setString(5, orderitem.getTotalAmt());
				pstmtq.setString(6, orderitem.getDiscountAmt());
				pstmtq.setString(7, orderitem.getNetpayableAmt());
				pstmtq.setString(8, orderitem.getNoOfItem());
				pstmtq.setString(9, orderitem.getPaymentStatus());
				pstmtq.setString(10, orderitem.getOrderStatus());
				rs = pstmtq.executeQuery();

				while (rs.next()) {
					statusCode = rs.getString(1);
					msg = rs.getString(2);
					userName = rs.getString(3);
					mailId = rs.getString(4);
					address = rs.getString(5) + ", " + rs.getString(6) + ", " + rs.getString(7) + ", "
							+ rs.getString(8);
					System.out.println(" saveOrderDetails " + statusCode + "------------- " + msg);
					System.out.println(" saveOrderDetails " + statusCode + "------------- " + msg);
				}

				if (statusCode.equalsIgnoreCase("101")) {

					/* Mailing */
					ExecutorService emailExecutor = Executors.newCachedThreadPool();
					emailExecutor.execute(new Runnable() {
						@Override
						public void run() {
							try {
								String msg = "Dear " + userName
										+ "\n\nYour order has been shipped, it will arrive to your following shipping address : "
										+ address + ", within maximum 2 days," + "  your Order no. is "
										+ orderitem.getOrderNo() + ", and as you choosed payment option as a "
										+ orderitem.getPaymentMode() + " for Rs." + orderitem.getNetpayableAmt()
										+ " \n Thanks for shopping with us ( addKarle Bazzar )";
								SendGmailMail.sendMail("Your Order has been shipped ", msg, mailId);
							} catch (Exception e) {
								System.out.println("mail SendIng exception: " + e.toString());
							}
						}
					});

					productItem.setResCode(true);
					productItem.setMsg("Data has been saved");

					SQLq = "{call removeCartItem(?,?,?)}";
					pstmtq = con.prepareCall(SQLq);
					pstmtq.setInt(1, Integer.parseInt(orderitem.getUseId()));
					pstmtq.setInt(2, Integer.parseInt(orderitem.getProductId()));
					pstmtq.setInt(3, 4);
					pstmtq.executeQuery();

					System.out.println("removeCartItem  " + "Removed user cart");

				} else if (statusCode.equalsIgnoreCase("102")) {
					productItem.setResCode(false);
					productItem.setMsg("Order no. already exist");

				} else if (statusCode.equalsIgnoreCase("103")) {
					productItem.setResCode(false);
					productItem.setMsg("103");
				}

			} else {
				productItem.setResCode(false);
				productItem.setMsg("Order no. already exist");
			}

		} catch (Exception exception) {
			productItem.setResCode(false);
			productItem.setMsg(exception.toString());
			System.out.println(" exception " + exception);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return productItem;

	}

	public List<OrderItem> getOrder(String userId) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		String SQLq = "{call getOrders(?)}";

		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		try {
			pstmtq = con.prepareCall(SQLq);
			pstmtq.setString(1, userId);
			rs = pstmtq.executeQuery();

			while (rs.next()) {
				OrderItem productItem = new OrderItem();

				productItem.setResCode(true);
				productItem.setMsg("Order Data");
				productItem.setUserId(rs.getString(2));
				productItem.setOrderNo(rs.getString(4));
				productItem.setPaymentMode(rs.getString(5));
				productItem.setTotalAmt(rs.getString(6));
				productItem.setDiscountAmt(rs.getString(7));
				productItem.setNetpayableAmt(rs.getString(8));
				productItem.setNoOfItem(rs.getString(9));
				productItem.setPaymentStatus(rs.getString(10));
				productItem.setOrderStatus(rs.getString(11));
				orderItems.add(productItem);

			}

		} catch (Exception e) {
			OrderItem productItem = new OrderItem();
			productItem.setResCode(false);
			productItem.setMsg("No order found");
			orderItems.add(productItem);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		Collections.reverse(orderItems);
		return orderItems;

	}

	public List<OrderItem> getOrderItem(String productNumber) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		String SQLq = "{call getOrderItem(?)}";

		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		try {
			pstmtq = con.prepareCall(SQLq);
			pstmtq.setString(1, productNumber);
			rs = pstmtq.executeQuery();

			while (rs.next()) {
				OrderItem productItem = new OrderItem();
				productItem.setResCode(true);
				productItem.setMsg("Order Data");
				productItem.setUserId(rs.getString(2));
				productItem.setOrderNo(rs.getString(4));
				productItem.setPaymentMode(rs.getString(5));
				productItem.setTotalAmt(rs.getString(6));
				productItem.setDiscountAmt(rs.getString(7));
				productItem.setNetpayableAmt(rs.getString(8));
				productItem.setNoOfItem(rs.getString(9));
				productItem.setPaymentStatus(rs.getString(10));
				productItem.setOrderStatus(rs.getString(11));
				orderItems.add(productItem);

			}

		} catch (Exception e) {
			OrderItem productItem = new OrderItem();
			productItem.setResCode(false);
			productItem.setMsg("No order found");
			orderItems.add(productItem);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return orderItems;

	}

	public List<ConstantItem> orderCancelled(String orderNo) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		String SQLq = "{call cancelOrder(?)}";

		ConstantItem ConstantItem = new ConstantItem();
		List<ConstantItem> ConstantItemList = new ArrayList<ConstantItem>();
		try {
			pstmtq = con.prepareCall(SQLq);
			pstmtq.setString(1, orderNo);
			pstmtq.executeQuery();
			ConstantItem.setResCode(true);
			ConstantItem.setMsg("Order has been cancelled successfully");
			ConstantItemList.add(ConstantItem);

		} catch (Exception e) {
			ConstantItem.setResCode(false);
			ConstantItem.setMsg("Order did not cancelled");
			ConstantItemList.add(ConstantItem);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ConstantItemList;

	}

	public List<OrderProductItem> getOrderitem(String imagePath, String orderNumber) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		List<OrderProductItem> caList = new ArrayList<OrderProductItem>();
		String SQLq = "{call getOrderItem(?)}";
		try {
			pstmtq = con.prepareCall(SQLq);
			pstmtq.setString(1, orderNumber);
			rs = pstmtq.executeQuery();
			while (rs.next()) {
				OrderProductItem productItem = new OrderProductItem();

				productItem.setResCode(true);
				productItem.setMsg("Item Data");
				productItem.setImage(imagePath + rs.getString(1));
				productItem.setProductName(rs.getString(2));
				productItem.setPurchaseQty(rs.getString(3));
				productItem.setUnit(rs.getString(4));
				productItem.setUnitName(rs.getString(5));
				productItem.setPayablePrice(rs.getString(6));
				productItem.setDiscount(rs.getString(7));

				caList.add(productItem);
			}
			System.out.println(" List Size " + caList.size());
		} catch (Exception exception) {
			OrderProductItem productItem = new OrderProductItem();
			productItem.setResCode(false);
			productItem.setMsg("Server Error");
			System.out.println(" exception " + exception);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return caList;

	}

	public ConstantItem forgetPassword(final String email) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		String SQLq = "{call getPassword(?)}";

		ConstantItem ConstantItem = new ConstantItem();

		try {
			pstmtq = con.prepareCall(SQLq);
			pstmtq.setString(1, email.trim());
			rs = pstmtq.executeQuery();
			while (rs.next()) {

				userName = rs.getString(1);
				pwd = rs.getString(2);
				mobileNo = rs.getString(3);
				System.out.println("userName pwd mobile " + userName + " " + pwd + " " + mobileNo);

			}

			ConstantItem.setResCode(true);
			ConstantItem.setMsg("Your password seccessfully sent to your registered mail ID");

			/* Mailing */
			ExecutorService emailExecutor = Executors.newCachedThreadPool();
			emailExecutor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						String msg = "Dear " + userName + "\n\nYour password is " + pwd + " and login id is " + mobileNo
								+ "\nKeep logging with us Thanks ! ( addKarle Bazzar )";
						SendGmailMail.sendMail("Password ", msg, email);
					} catch (Exception e) {
						System.out.println("mail SendIng exception: " + e.toString());
					}
				}
			});

		} catch (Exception e) {
			ConstantItem.setResCode(true);
			ConstantItem.setMsg("Your mail ID did not found in our record");

		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ConstantItem;

	}

	public AppUpdate getAppVersion(String platform) {
		Connection con = getConnection();
		PreparedStatement pstmtq = null;
		AppUpdate appUpdate = new AppUpdate();
		String version = "";
		String SQLq = "SELECT * FROM app_update WHERE platform=?";
		try {
			pstmtq = con.prepareStatement(SQLq);
			pstmtq.setString(1, platform);
			rs = pstmtq.executeQuery();
			while (rs.next()) {

				version = rs.getString(2);
				platform = rs.getString(3);

			}

			appUpdate.setResCode(true);
			appUpdate.setMsg("We have a found a new app version. Kindly update the app from play store");
			appUpdate.setPlatform(platform);
			appUpdate.setVersion(version);

		} catch (Exception exception) {
			System.out.println(" exception " + exception);
			appUpdate.setResCode(false);
			appUpdate.setMsg("Server Error");
			appUpdate.setPlatform("");
			appUpdate.setVersion("");
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return appUpdate;
	}

	public ConstantItem addWalletMoney(WalletItem walletItem) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;
		String statusCode = "";
		String msg = "";
		String SQLq = "{call walletAddMoney(?,?,?,?,?,?,?,?)}";

		ConstantItem ConstantItem = new ConstantItem();

		try {
			pstmtq = con.prepareCall(SQLq);
			pstmtq.setInt(1, walletItem.getCustomerId());
			pstmtq.setDouble(2, walletItem.getWalletAmt());
			pstmtq.setInt(3, walletItem.getWalletId());
			pstmtq.setString(4, walletItem.getTransactionType());
			pstmtq.setString(5, walletItem.getTransactionDetail());
			pstmtq.setString(6, walletItem.getDateTime());
			pstmtq.setString(7, walletItem.getTransactionId());
			pstmtq.setString(8, walletItem.getModeOfPayment());
			rs = pstmtq.executeQuery();

			while (rs.next()) {
				statusCode = rs.getString(1);
				msg = rs.getString(2);
				System.out.println(" saveOrderDetails " + statusCode + "------------- " + msg);
			}
			System.out.println("userName pwd mobile " + userName + " " + pwd + " " + mobileNo);

			if (statusCode.equalsIgnoreCase("101")) {
				ConstantItem.setResCode(true);
				ConstantItem.setMsg(msg);
			} else {
				ConstantItem.setResCode(false);
				ConstantItem.setMsg("Server error");
			}

		} catch (Exception e) {
			ConstantItem.setResCode(true);
			ConstantItem.setMsg("Server error");

		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ConstantItem;

	}

	public WalletItem getWallet(String customerID) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;

		String SQLq = "{call getWalletAmount(?)}";

		WalletItem walletItem = new WalletItem();

		try {
			pstmtq = con.prepareCall(SQLq);
			pstmtq.setString(1, customerID);
			rs = pstmtq.executeQuery();

			while (rs.next()) {

				walletItem.setResCode(true);
				walletItem.setMsg("Wallet avaliable");
				walletItem.setWalletId(rs.getInt(1));
				walletItem.setCustomerId(rs.getInt(2));
				walletItem.setWalletAmt(rs.getInt(3));

				if (rs.getInt(1) == 1)
					walletItem.setActive(true);
				else
					walletItem.setActive(false);

			}

		} catch (Exception e) {
			walletItem.setResCode(false);
			walletItem.setMsg("Wallet unavaliable");
			walletItem.setWalletId(0);
			walletItem.setCustomerId(0);
			walletItem.setWalletAmt(00.00);
			walletItem.setActive(false);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return walletItem;

	}

	public List<WalletItem> getWalletDetails(String customerID) {
		Connection con = getConnection();
		CallableStatement pstmtq = null;

		String SQLq = "{call getWalletDetails(?)}";

		List<WalletItem> list = new ArrayList<WalletItem>();
		try {
			pstmtq = con.prepareCall(SQLq);
			pstmtq.setString(1, customerID);
			rs = pstmtq.executeQuery();

			while (rs.next()) {
				WalletItem walletItem = new WalletItem();
				walletItem.setResCode(true);
				walletItem.setMsg("Wallet details avaliable");
				walletItem.setWalletId(rs.getInt(2));
				walletItem.setTransactionType(rs.getString(3));
				walletItem.setTransactionDetail(rs.getString(4));
				walletItem.setDateTime(rs.getString(5));
				walletItem.setAmtForUpation(rs.getDouble(6));
				walletItem.setTransactionId(rs.getString(7));
				walletItem.setModeOfPayment(rs.getString(8));

				list.add(walletItem);
			}

		} catch (Exception e) {
			WalletItem walletItem = new WalletItem();
			walletItem.setResCode(false);
			walletItem.setMsg("Wallet unavaliable");
			walletItem.setWalletId(0);
			walletItem.setCustomerId(0);
			walletItem.setWalletAmt(00.00);
			walletItem.setActive(false);
			list.add(walletItem);
		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Collections.reverse(list);
		return list;

	}

	/*
	 * admin
	 */
	public ConstantItem addStore(AdminModel storeItem) {
		Connection con = getConnection();
		PreparedStatement pstmtq = null;
		// String statusCode = "";
		String msg = "";
		String SQLq = "Insert into grocer_store_master (store_name,store_address,state,city,pincode) values(?,?,?,?,?)";
		System.out.println("GET SQL " + SQLq);
		ConstantItem ConstantItem = new ConstantItem();
		try {
			pstmtq = con.prepareStatement(SQLq);
			pstmtq.setString(1, storeItem.getStoreName());
			pstmtq.setString(2, storeItem.getStoreAddress());
			pstmtq.setString(3, storeItem.getState());
			pstmtq.setString(4, storeItem.getCity());
			pstmtq.setInt(5, storeItem.getPinCode());

			pstmtq.execute();
			/*
			 * String qw=""; List<String > list=new ArrayList<String>(); while (rs.next()) {
			 * 
			 * WalletItem walletItem= new WalletItem(); walletItem.setResCode(true);
			 * walletItem.setMsg("Wallet details avaliable");
			 * walletItem.setWalletId(rs.getInt(2));
			 * walletItem.setTransactionType(rs.getString(3));
			 * walletItem.setTransactionDetail(rs.getString(4));
			 * walletItem.setDateTime(rs.getString(5));
			 * walletItem.setAmtForUpation(rs.getDouble(6));
			 * walletItem.setTransactionId(rs.getString(7));
			 * walletItem.setModeOfPayment(rs.getString(8));
			 * 
			 * System.out.println("GET SQL "+rs.getString(3)); qw += rs.getString(3)+""; }
			 */
			ConstantItem.setResCode(true);
			ConstantItem.setMsg("Store added successfully");

		} catch (Exception e) {
			System.out.println("Exception " + e.toString());
			ConstantItem.setResCode(false);
			ConstantItem.setMsg("Server error");

		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ConstantItem;

	}
	
	
	public ConstantItem addCategory(AdminModel storeItem) {
		Connection con = getConnection();
		PreparedStatement pstmtq = null;
		// String statusCode = "";
		String msg = "";
		String SQLq = "Insert into grocer_category_master (cat_name,store_id,cat_description,cat_image) values(?,?,?,?)";
		System.out.println("GET SQL " + SQLq);
		ConstantItem ConstantItem = new ConstantItem();
		try {
			pstmtq = con.prepareStatement(SQLq);
			pstmtq.setString(1, storeItem.getCat_name());
			pstmtq.setInt(2, storeItem.getStore_id());
			pstmtq.setString(3, storeItem.getCat_description());
			pstmtq.setString(4, storeItem.getCat_image());
		
			pstmtq.execute();
		
			ConstantItem.setResCode(true);
			ConstantItem.setMsg("Category added successfully");

		} catch (Exception e) {
			System.out.println("Exception " + e.toString());
			ConstantItem.setResCode(false);
			ConstantItem.setMsg("Server error");

		} finally {
			try {
				pstmtq.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ConstantItem;

	}
}
