package model;

import java.sql.*;

public class PowerInterruption {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", "root");
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Error connected");
		}
		return con;
	}

	public String insertPowerInterruption(String name, int pnumber, String intype, String inloca, String remarks) {
		String output = "";
		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = " insert into inquiries(`inquiryID`, `customerName`,`phoneNumber`,`inquiryType`,`inquiryLocation`,`remarks`)"
					+ " values (?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);			
			preparedStmt.setString(2, name);
			preparedStmt.setInt(3, pnumber);
			preparedStmt.setString(4, intype);
			preparedStmt.setString(5, inloca);
			preparedStmt.setString(6, remarks);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the inquiries.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readPowerInterruption() {
		String output = "";

		try {

			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Customer Name</th>"
					+ "<th>Phone Number</th>" 
					+ "<th>Inquiry Type</th>"
					+ "<th>Inquiry Location</th>" 
					+ "<th>Remarks</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from inquiries";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String inquiryID = Integer.toString(rs.getInt("inquiryID"));
				String customerName = rs.getString("customerName");
				String phoneNumber = Integer.toString(rs.getInt("phoneNumber"));
				String inquiryType = rs.getString("inquiryType");
				String inquiryLocation = rs.getString("inquiryLocation");
				String remarks = rs.getString("remarks");

				// Add into the html table
				
				output += "<td>" + customerName + "</td>";
				output += "<td>" + phoneNumber + "</td>";
				output += "<td>" + inquiryType + "</td>";
				output += "<td>" + inquiryLocation + "</td>";
				output += "<td>" + remarks + "</td>";


				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						+ "<input name='inquiryID' type='hidden' value='" + inquiryID + "'>" + "</form></td></tr>";
			}
			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the inquiries.";
			System.err.println(e.getMessage());
		}

		return output;
	}
	
	public String updatePowerInterruption(int ID, String name, int pnumber, String intype, String inloca, String remarks) {
		String output = "";
		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE inquiries SET customerName=?,phoneNumber=?,inquiryType=?,inquiryLocation=?,remarks=? " + 
						"WHERE inquiryID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
									
			preparedStmt.setString(1, name);
			preparedStmt.setInt(2, pnumber);
			preparedStmt.setString(3, intype);
			preparedStmt.setString(4, inloca);
			preparedStmt.setString(5, remarks);
			preparedStmt.setInt(6, ID);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the inquiries.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deletePowerInterruption(String inquiryID) {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from inquiries where inquiryID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(inquiryID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";

		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}

		return output;
	}
}
