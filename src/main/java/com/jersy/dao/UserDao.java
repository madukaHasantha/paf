package com.jersy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.jersy.bean.UserBean;
import com.jersy.dbconnect.dbConnection;

public class UserDao {

	public static String registerDao(UserBean rs) {

		int otp = new Random().nextInt(345);

		Connection con = dbConnection.connect();

		try {

			PreparedStatement ps1 = con.prepareStatement("select email from user where email=?");
			ps1.setString(1, rs.getEmail());
			ResultSet rrs = ps1.executeQuery();

			if (rrs.next()) {
				return "Already Exist";
			} else {

				
				PreparedStatement ps = con.prepareStatement("insert into user values(?,?,?,?, ?, ?)");
				ps.setString(1, rs.getName());
				ps.setString(2, rs.getEmail());
				ps.setString(3, rs.getPass());
				ps.setString(4, rs.getMobile());
				ps.setInt(5, otp);
				ps.setString(6, "inActive");

				int i = ps.executeUpdate();

				if (i > 0) {
					return "success";
				} else {
					return "failed";
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return "fail";

	}

}
