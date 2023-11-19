var xhttp = new XMLHttpRequest();
var RazorpayOrderId;
function CreateOrderId(){
	
	xhttp.open("GET","http://localhost:8080/Blade/OrderCreation",false);
	xhttp.send();
	RazorpayOrderId  = xhttp.responseText;
	OpenCheckout();
}


CreateOrderId()
var src="https://checkout.razorpay.com/v1/checkout.js";

	    var javaScriptVar="<%out.print(200);%>";
		var options = {
				
			"key" : "rzp_test_OnRWy6TMKMVsKf", // Enter the Key ID generated from the Dashboard
			"amount" : javaScriptVar, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
			"currency" : "INR",
			"name" : "Acme Corp",
			"description" : "Test Transaction",
			"image" : "https://example.com/your_logo",
			//This is a sample Order ID. Pass the `id` obtained in the response of Step 1
			"handler" : function(response) {
				alert(response.razorpay_payment_id);
				alert(response.razorpay_order_id);
				alert(response.razorpay_signature)
			},
			"prefill" : {
				"name" : "Gaurav Kumar",
				"email" : "gaurav.kumar@example.com",
				"contact" : "9310524629"
			},
			"notes" : {
				"address" : "Razorpay Corporate Office"
			},
			"theme" : {
				"color" : "#3399cc"
			}
		};
		var rzp1 = new Razorpay(options);
		rzp1.on('payment.failed', function(response) {
			alert(response.error.code);
			alert(response.error.description);
			alert(response.error.source);
			alert(response.error.step);
			alert(response.error.reason);
			alert(response.error.metadata.order_id);
			alert(response.error.metadata.payment_id);
		});
		document.getElementById('payButton').onclick = function(e) {
			rzp1.open();
			e.preventDefault();
		}
