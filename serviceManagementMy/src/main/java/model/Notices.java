package model;

import java.sql.*;

public class Notices {

	public Connection connect()
	{
	 Connection con = null;

	 try
	 {
		 Class.forName("com.mysql.jdbc.Driver");
		 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notices", "root", "2230166");
	 }
	 catch(Exception e)
	 {
		 //System.out.print("Disconnected");
		 e.printStackTrace();
	 }

	 return con;
	}
	
	//Insert 
	public String insertNotes(String phone, String address, String note, String zipcode)
	{
	 String output = "";
	try
	 {
	 Connection con = connect();
	 if (con == null)
	 {
	 return "Error while connecting to the database";
	 }
	 
	 // create a prepared statement
	 String query = " insert into service(`noticeId`,`phone`,`address`,`note`, `zipcode`)" + " values (?, ?, ?, ?,?)";
	 
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, 0);
	 preparedStmt.setString(2, phone);
	 preparedStmt.setString(3, address);
	 preparedStmt.setString(4, note); 
	 preparedStmt.setString(5, zipcode);
	 
	//execute the statement
	 preparedStmt.execute();
	 con.close();
	 
	 output = "Inserted successfully";
	 }
	
	catch (Exception e)
	 {
	 output = "Error while inserting";
	 System.err.println(e.getMessage());
	 }
	return output; 
	}
	
	//Read
	public String readNotes()
	{
	 String output = "";
	 
	 try
	 {
	  Connection con = connect();
	 if (con == null)
	  {
	  return "Error while connecting to the database for reading.";
	  }
	 
	// Prepare the html table to be displayed
	 output = "<table border='1' class='table table-striped'><tr><th>ID</<th>"
	 + "<th>Phone</th>"
	 +"<th>District</th>"
	 + "<th>Time</th>"
	 + "<th>Zip Code</th>"
	 + "<th>Update</th><th>Delete</th></tr>";
	 
	 String query = "select * from service";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
	 String noticeId = Integer.toString(rs.getInt("noticeId"));
	 String phone = rs.getString("phone");
	 String address = rs.getString("address");
	 String note = rs.getString("note");
	 String zipcode = rs.getString("zipcode");
	 
	 // Add a row into the html table
	 output += "<tr><td>" + noticeId + "</td>";
	 output += "<td>" + phone + "</td>";
	 output += "<td>" + address + "</td>";
	 output += "<td>" + note + "</td>";
	 output += "<td>" + zipcode + "</td>";
	
	 // buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-success'></td>"
	 + "<td><form method='post' action='Notices.jsp'>"
	 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
	 + "<input name='noticeId' type='hidden' +  value='" + noticeId + "'>" + "</form></td></tr>";
	 }
	 
	 con.close();
	 // Complete the html table
	 output += "</table>";
	}
	 catch (Exception e)
	 {
	 output = "Error while reading the notes.";
	  System.err.println(e.getMessage());
	 }
	 
	 return output;
	}
	
	
	//Update
	public String updateNotes(String noticeId, String phone, String address, String note, String zipcode) 
	{
			 String output = "";
			 try
			 {
			 Connection con = connect();
			 if (con == null)
			 {return "Error while connecting to the database for updating."; }
			 // create a prepared statement
			 String query = "update service set phone=?, address=?, note=?, zipcode=? where noticeId=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setString(1, phone);
			 preparedStmt.setString(2, address);
			 preparedStmt.setString(3, note);
			 preparedStmt.setString(4, zipcode);
			 preparedStmt.setInt(5, Integer.parseInt(noticeId));
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Updated successfully";
			 }
			 catch (Exception e)
			 {
			 output = "Error while updating the item.";
			 System.err.println(e.getMessage());
			 }
			 return output;
	}
	
	//Delete
	public String deleteNotes(String noticeId)
	{
	 String output = "";
	 try
	  {
	  Connection con = connect();
	  if (con == null)
	  {
	  return "Error while connecting to the database for deleting.";
	  }
	  // create a prepared statement
	  String query = "delete from service where noticeId=?";
	  PreparedStatement preparedStmt = con.prepareStatement(query);
	  // binding values
	  preparedStmt.setInt(1, Integer.parseInt(noticeId));

	  // execute the statement
	  preparedStmt.execute();
	  con.close();
	  output = "Deleted successfully";
	  }
	 catch (Exception e)
	  {
	  output = "Error while deleting the item.";
	  System.err.println(e.getMessage());
	  }
	 return output; 
	}
}
