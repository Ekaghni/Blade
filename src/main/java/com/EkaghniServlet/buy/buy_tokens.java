package com.EkaghniServlet.buy;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

class m {
	String tokenAmount;
	String emailString;

	m(String tokenAmount, String emailString) {
		this.tokenAmount = tokenAmount;
		this.emailString = emailString;

	}
}

@WebServlet("/Ekaghni_Index/buy")
public class buy_tokens extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getEmailAndTokens(request, response);

	}

/////////////////////////////////////////////////////////////////getEmailAndTokens///////////////////////////////////////////////////////////////////

	public void getEmailAndTokens(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("buy_enter_user_id");
		String userPass = request.getParameter("buy_enter_user_password");
		String amountOfTokens = request.getParameter("buy_enter_amount_of_tokens");
		int at = Integer.parseInt(amountOfTokens);
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://sql6.freesqldatabase.com:3306/sql6512592?user=sql6512592&password=k3IX9Wjks9");

			PreparedStatement preparedStatement = connection
					.prepareStatement("select token_amount,email from sellers where token_amount>= ?");
			preparedStatement.setInt(1, at);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			ArrayList<m> list1 = new ArrayList<>();
			while (rs.next()) {
				String tokString = rs.getString("token_amount");
				String tokStringEmail = rs.getString("email");
				System.out.println("tkString=" + tokString);
				System.out.println("tokStringEmail=" + tokStringEmail);
				m objM = new m(tokString, tokStringEmail);
				list1.add(objM);
			}
			// int s= rs.getInt("token_amount");
			PrintWriter pt = response.getWriter();
			m objM = new m(amountOfTokens, amountOfTokens);
			objM = list1.get(0);
			pt.print(objM.emailString);
			System.out.println(list1);

			////////////////
			int tempCost = Integer.parseInt(amountOfTokens);
			System.out.println(tempCost);
			HttpSession session = request.getSession();
			// save message in session
			session.setAttribute("helloWorld", tempCost);
			session.setAttribute("userEmail", userId);
			session.setAttribute("userPass", userPass);
			response.sendRedirect("/Blade/Checkout/index.jsp");

			///////////////

			upadateSellerDatabase(Integer.parseInt(amountOfTokens), objM.emailString,
					Integer.parseInt(objM.tokenAmount), request, response, userId, userPass);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	///////////////////////////////////////////////////////////////////// upadateSellerDatabase//////////////////////////////////////////////////////////

	public void upadateSellerDatabase(int amountOfTokens, String email, int fetchedTokens, HttpServletRequest request,
			HttpServletResponse response, String userId, String userPass) throws IOException {
		PrintWriter out = response.getWriter();
		System.out.println("seller db-\n" + amountOfTokens + " " + email + " " + fetchedTokens);
		Connection connection = null;
		fetchedTokens = fetchedTokens - amountOfTokens;
		boolean rowUpdated = false;
		out.print(amountOfTokens);
		out.print(email);
		out.print(fetchedTokens);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://sql6.freesqldatabase.com:3306/sql6512592?user=sql6512592&password=k3IX9Wjks9");
			PreparedStatement preparedStatement = connection
					.prepareStatement("update sellers set token_amount= ? where email= ?");
			preparedStatement.setInt(1, fetchedTokens);
			preparedStatement.setString(2, email);
			out.print(preparedStatement);
			rowUpdated = preparedStatement.executeUpdate() > 0;
			out.print(rowUpdated);
			System.out.println("Row status-" + rowUpdated);
			if (rowUpdated == true) {

				updateBuyersDatabase(userId, userPass, amountOfTokens, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	////////////////////////////////////////////////////////////////////////////////// updateBuyersDatabase////////////////////////////////////////////////

	public void updateBuyersDatabase(String userId, String userPass, int amountofTokens, HttpServletRequest request,
			HttpServletResponse response) {
		int temp_token_amount = amountofTokens;
		Connection connection = null;
		String tokres = null;
		////////////// Fetching token amount of buyer which they have in their
		////////////// db///////////////////////////////
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://sql6.freesqldatabase.com:3306/sql6512592?user=sql6512592&password=k3IX9Wjks9");
			PreparedStatement preparedStatement = connection.prepareStatement(
					"select amount_of_blade_tokens from users where email_of_users= ? and password_of_users= ?");
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, userPass);
			ResultSet res = preparedStatement.executeQuery();
			while (res.next()) {
				tokres = res.getString("amount_of_blade_tokens");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("tokres=" + tokres);

		//////////////////// Updating users db ////////////////////////////////////

		amountofTokens = amountofTokens + Integer.parseInt(tokres);
		System.out.println("amount of final tokens of user = " + amountofTokens);
		boolean rowUpdated = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://sql6.freesqldatabase.com:3306/sql6512592?user=sql6512592&password=k3IX9Wjks9");
			PreparedStatement preparedStatement = connection.prepareStatement(
					"update users set amount_of_blade_tokens= ? where email_of_users= ? and password_of_users= ?");
			preparedStatement.setInt(1, amountofTokens);
			preparedStatement.setString(2, userId);
			preparedStatement.setString(3, userPass);
			rowUpdated = preparedStatement.executeUpdate() > 0;
			System.out.println("Row statusssssssssss------------>>>>" + rowUpdated);
			if (rowUpdated == true) {

				/////////////////////////////////// Update Transaction
				/////////////////////////////////// Table/////////////////////////////////////////

				rowUpdated = false;
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					connection = DriverManager.getConnection(
							"jdbc:mysql://sql6.freesqldatabase.com:3306/sql6512592?user=sql6512592&password=k3IX9Wjks9");
					preparedStatement = connection.prepareStatement(
							"insert into transaction_details(name_of_users,password_of_users,amount_of_tokens,mode_of_transaction,status_of_transaction) values(?,?,?,?,?)");
					preparedStatement.setString(1, userId);
					preparedStatement.setString(2, userPass);
					preparedStatement.setInt(3, temp_token_amount);
					preparedStatement.setString(4, "Purchased");
					preparedStatement.setString(5, "Completed");
					rowUpdated = preparedStatement.executeUpdate() > 0;
					System.out.println("transaction table status- " + rowUpdated);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//////////// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------////////////////////////
	///////////////////////////////////////////////////////////////////////// Selling
	//////////// Tokens////////////////////////////////////////////////////////////////////////////////////////////
	//////////// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------////////////////////////

}
