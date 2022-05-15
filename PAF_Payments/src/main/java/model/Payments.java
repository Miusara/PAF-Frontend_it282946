package model;

import java.sql.*;

public class Payments {

	public Connection connect()
	{
	 Connection con = null;

	 try
	 {
		 Class.forName("com.mysql.jdbc.Driver");
		 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pay", "root", "admin123");
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }

	 return con;
	}
	
	//Read function
	public String readPay()
	{
	 String output = "";
	 
	 try
	 {
	  Connection con = connect();
	  if (con == null)
	  {
		  return "Error while connecting to the database for reading.";
	  }
	 
	// Prepare the HTML table to be displayed
	 output = "<table border='1' class='table table-striped'><tr>"
	 + "<th>Payment ID</th>"
	 + "<th>User ID</th>"
	 + "<th>Payment Method</th>"
	 + "<th>Date</th>"
	 + "<th>Amount</th>"
	 + "<th>Update</th><th>Delete</th></tr>";
	 
	 String query = "select * from payments";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 
	 // Iterate through the rows in the result set
	 while (rs.next())
	 {
		 String noticeId = Integer.toString(rs.getInt("noticeId"));
		 String payID = rs.getString("payID");
		 String UserID = rs.getString("UserID");
		 String method = rs.getString("method");
		 String date = rs.getString("date");
		 String amount = rs.getString("amount");
	 
		 // Add a row into the HTML table
//		 output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + noticeId
//				 + "'>" + phone + "</td>"; 
		 output += "<tr><td><input id=\'hidItemIDUpdate\' name=\'hidItemIDUpdate\' type=\'hidden\' value=\'"
					+ noticeId + "'>" + payID + "</td>";
		 output += "<td>" + UserID + "</td>";
		 output += "<td>" + method + "</td>";
		 output += "<td>" + date + "</td>";
		 output += "<td>" + amount + "</td>";
		
		 
		 // Buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-success' data-noticeid='" + noticeId +"'></td>"
		 + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-noticeid='" + noticeId + "'></td></tr>";
	 }
	 con.close();
	 
	 // Complete the HTML table
	 output += "</table>";
	}
	 
	catch (Exception e)
	{
		output = "Error while reading the payments.";
		System.err.println(e.getMessage());
	}
	 
	return output;
	}
	
	//Insert function
	public String insertPay(String payID, String UserID, String method, String date, String amount)
	{
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {
	 return "Error while connecting to the database";
	 }
	 
	 // Prepared statement
	 String query = " insert into payments(`noticeId`,`payID`,`UserID`,`method`, `date`, `amount`)" + " values (?, ?, ?, ?,?,?)";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	 // Binding values
	 preparedStmt.setInt(1, 0);
	 preparedStmt.setString(2, payID);
	 preparedStmt.setString(3, UserID);
	 preparedStmt.setString(4, method); 
	 preparedStmt.setString(5, date);
	 preparedStmt.setString(6, amount);
	 
	 //Execute the statement
	 preparedStmt.execute();
	 con.close();
	 
	 String newPay = readPay();
	 output = "{\"status\":\"success\", \"data\": \"" + 
			 newPay + "\"}"; 
	 }
	
	catch (Exception e)
	{
	 //output = "Error while inserting the payment";
	 output = "{\"status\":\"error\", \"data\": \"Error while inserting the payment.\"}"; 
	 System.err.println(e.getMessage());
	 }
	return output; 
	}
	
	//Update function
	public String updatePay(String noticeId, String payID, String UserID, String method, String date, String amount) 
	{
			 String output = "";
			 try
			 {
			 Connection con = connect();
			 if (con == null)
			 {return "Error while connecting to the database for updating."; }
			 
			 // Create a prepared statement
			 String query = "update payments set payID=?, UserID=?, method=?, date=?, amount=? where noticeId=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // Binding values
			 preparedStmt.setString(1, payID);
			 
			 preparedStmt.setString(2, UserID);
			 preparedStmt.setString(3, method);
			 preparedStmt.setString(4, date);
			 preparedStmt.setString(5, amount);
			 preparedStmt.setInt(6, Integer.parseInt(noticeId));
			 
			 // Execute the statement
			 preparedStmt.execute();
			 con.close();
			 
			 String newPay = readPay();
			 output = "{\"status\":\"success\", \"data\": \"" + 
					 newPay + "\"}"; 
     		 //output = "Updated successfully";
			 }
			 
			 catch (Exception e)
			 {
			 //output = "Error while updating the payment.";
			 output = "{\"status\":\"error\", \"data\":\"Error while updating the payment.\"}";
			 System.err.println(e.getMessage());
			 }
			 return output;
	}
	
	//Delete function
	public String deletePay(String noticeId)
	{
	 String output = "";
	 try
	  {
	  Connection con = connect();
	  if (con == null)
	  {
	  return "Error while connecting to the database for deleting.";
	  }
	  
	  // Create a prepared statement
	  String query = "delete from payments where noticeId=?";
	  PreparedStatement preparedStmt = con.prepareStatement(query);
	  
	  // Binding values
	  preparedStmt.setInt(1, Integer.parseInt(noticeId));

	  // Execute the statement
	  preparedStmt.execute();
	  con.close();
	  
	  String newPay = readPay();
	  output = "{\"status\":\"success\", \"data\": \"" + newPay + "\"}"; 
	  //output = "Deleted successfully";
	  }
	 catch (Exception e)
	  {
	  //output = "Error while deleting the payment.";
	  output = "{\"status\":\"error\", \"data\": \"Error while deleting the payment.\"}";
	  System.err.println(e.getMessage());
	  }
	 return output; 
	}
}
