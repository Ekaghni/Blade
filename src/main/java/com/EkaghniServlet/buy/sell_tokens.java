package com.EkaghniServlet.buy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Ekaghni_Index/sell")
public class sell_tokens extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getEmailAndTokens(request, response);

	}

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
					.prepareStatement("select token_amount from sellers where email=? and pass=?");
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, userPass);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				int temp = rs.getInt("token_amount");
				at = at + temp;
				System.out.println("Final-->" + at);
				preparedStatement = null;
				preparedStatement = connection
						.prepareStatement("update sellers set token_amount = ? where email = ? and pass = ?");
				preparedStatement.setInt(1, at);
				preparedStatement.setString(2, userId);
				preparedStatement.setString(3, userPass);
				boolean check = false;
				check = preparedStatement.executeUpdate() > 0;
				if (check == true) {
					System.out.println("Database updated");
				} else {
					System.out.println("Database not updated");
				}
			} else {
				System.out.println("Data not found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		////////////////

	}
}
