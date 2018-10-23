package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.core.Response;

public class UserDB {

	public static LoginResponse insertTokenAuth(long tokenExpDuration, LoginResponse loginResponse) {
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		String newToken = null;

		try {
			// create a mysql database connection
			String myDriver = "org.mariadb.jdbc.Driver";
			String myUrl = "jdbc:mariadb://localhost:3310/test";
			Class.forName(myDriver);
			conn = DriverManager.getConnection(myUrl, "", "");
			System.out.println("Connected database successfully...");

			// the mysql query statement
			String updateQuery = " UPDATE core_user SET token = ? WHERE login = ?";
			// create token
			newToken = JWT.createJWT(loginResponse.email, tokenExpDuration);

			// create the mysql insert preparedstatement
			preparedStmt = conn.prepareStatement(updateQuery);
			preparedStmt.setString(1, newToken);
			preparedStmt.setString(2, loginResponse.userName);

			// execute the preparedstatement
			preparedStmt.executeQuery();

			String selectQuery = " SELECT token FROM core_user WHERE (login = ?)";
			preparedStmt = conn.prepareStatement(selectQuery);
			preparedStmt.setString(1, loginResponse.userName);
			// execute the preparedstatement
			ResultSet myResults = preparedStmt.executeQuery();

			// check if token initialized
			if (myResults.next()) {
				System.out.println("Token success");
				newToken = myResults.getString(1);
				loginResponse.setToken(newToken);
			}
			//token failed to initialize
			else {
				System.out.println("Token failed to initialize");
				loginResponse.setStatus(204);
			}

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			System.err.println("Got a database exception!");
		} finally {
			// finally block used to close resources
			try {
				if (preparedStmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Connection closed");
		return loginResponse;
	}
	
	

	public static LoginResponse queryUserLogin(LoginResponse loginResponse) {
		Connection conn = null;
		PreparedStatement preparedStmt = null;

		try {
			// create a mysql database connection
			String myDriver = "org.mariadb.jdbc.Driver";
			String myUrl = "jdbc:mariadb://localhost:3310/test";
			Class.forName(myDriver);
			conn = DriverManager.getConnection(myUrl, "root", "local");
			System.out.println("Connected database successfully...");

			// the mysql query statement
			String query = " select * from core_user where (email = ?) OR (login = ?)";

			// create the mysql insert preparedstatement
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, loginResponse.email);
			preparedStmt.setString(2, loginResponse.email);

			// execute the preparedstatement
			ResultSet myResults = preparedStmt.executeQuery();

			// check if user exists
			if (myResults.next()) {
				System.out.println("User " + loginResponse.email + " found");
				String passCheck = myResults.getString(4);

				// check if password is valid
				if (passCheck.equals(loginResponse.password)) {
					System.out.println("Password accepted for " + loginResponse.email);
					loginResponse.setuserName(myResults.getString(2));
					loginResponse.setStatus(200);
					loginResponse.setToken(UserDB.insertTokenAuth(10000, loginResponse).token);
				}
				// wrong password
				else {
					System.out.println("Wrong password");
					loginResponse.setStatus(202);
				}
				// User does not exist
			} else {
				System.out.println(loginResponse.email + "doesn't exist");
				loginResponse.setStatus(203);
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			loginResponse.setStatus(201);
		} catch (Exception e) {
			// Handle errors for Class.forName
			System.err.println("Got a database exception!");
			loginResponse.setStatus(201);
		} finally {
			// finally block used to close resources
			try {
				if (preparedStmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Connection closed");
		return loginResponse;
	}
	
	

	public static void insertUser(String email, String password) {
		Connection conn = null;
		PreparedStatement preparedStmt = null;
		try {
			// create a mysql database connection
			String myDriver = "org.mariadb.jdbc.Driver";
			String myUrl = "jdbc:mariadb://localhost:3310/test";
			Class.forName(myDriver);
			conn = DriverManager.getConnection(myUrl, "root", "local");
			System.out.println("Connected database successfully...");

			// the mysql insert statement
			String query = " insert into core_user (login, email, password, lastName, firstName)"
					+ " values (?, ?, ?, ?, ?)";

			// create the mysql insert preparedstatement
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, "Barns12");
			preparedStmt.setString(2, email);
			preparedStmt.setString(3, password);
			preparedStmt.setString(4, "Rubble");
			preparedStmt.setString(5, "Barney");

			// execute the preparedstatement
			preparedStmt.execute();

			conn.close();
		} catch (Exception e) {
			System.err.println("Got an exception!");
		}
	}

}
