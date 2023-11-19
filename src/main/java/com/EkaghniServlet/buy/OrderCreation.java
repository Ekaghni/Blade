package com.EkaghniServlet.buy;

import java.io.IOException;

import org.json.JSONObject;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OrderCreation
 */
public class OrderCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderCreation() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		RazorpayClient client = null;
		String orderId = null;
		try {
			client = new RazorpayClient("rzp_test_OnRWy6TMKMVsKf", "sghSEy6YqwQ23Pi3IxSt6Owm");
			JSONObject options = new JSONObject();
			options.put("amount", "5000");
			options.put("currency", "INR");
			options.put("receipt", "zxr456");
			options.put("payment_capture", true);
			Order order = client.Orders.create(options);
			orderId = order.get("id");
		} catch (RazorpayException e) {
			e.printStackTrace();
		}
		response.getWriter().append(orderId);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		RazorpayClient client = null;
		try {
			client = new RazorpayClient("rzp_test_OnRWy6TMKMVsKf", "sghSEy6YqwQ23Pi3IxSt6Owm");
			JSONObject options = new JSONObject();
			options.put("razorpay_payment_id", request.getParameter("razorpay_payment_id"));
			options.put("razorpay_order_id", request.getParameter("razorpay_order_id"));
			options.put("razorpay_signature", request.getParameter("razorpay_signature"));
			boolean SigRes = Utils.verifyPaymentSignature(options, "secret");
			if (SigRes) {
				response.getWriter().append("Payment successful");
			} else {
				response.getWriter().append("Payment not successful");
			}
		} catch (RazorpayException e) {
			e.printStackTrace();
		}
	}

}
