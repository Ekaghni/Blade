<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>
		<%
		//System.out.println("Ekaghni M="+session.getAttribute("helloWorld"));

		int x = (Integer) session.getAttribute("helloWorld");
		String userId = (String) session.getAttribute("userEmail");
		String userPass = (String) session.getAttribute("userPass");
		int no_of_tokens_user_wants_to_buy = x * 2;
		System.out.println("x=" + x);
		System.out.println("userEmail=" + userId);
		System.out.println("userPass=" + userPass);
		session.removeAttribute("helloWorld");
		%>
	</h1>


	<button id="payButton" onClick="CreateOrderId()" class="bttnStyle">Pay
		Now</button>
	<script src="https://checkout.razorpay.com/v1/checkout.js">

var xhttp = new XMLHttpRequest();
var RazorpayOrderId;
function CreateOrderId(){
	
	xhttp.open("GET","http://localhost:8080/Blade/OrderCreation",false);
	xhttp.send();
	RazorpayOrderId  = xhttp.responseText;
	OpenCheckout();
}
</script>
	
<script>
var javaScriptVar="<%out.print(x);%>";
var options = {
    "key": "rzp_test_OnRWy6TMKMVsKf", // Enter the Key ID generated from the Dashboard
    "amount": javaScriptVar, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
    "currency": "INR",
    "name": "Acme Corp",
    "description": "Test Transaction",
    "image": "https://example.com/your_logo",
    //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
    "handler": function (response){
        alert(response.razorpay_payment_id);
        alert(response.razorpay_order_id);
        alert(response.razorpay_signature)
        var frameID=1;
        console.log(frameID);
        $.ajax({
            type: 'GET',
            url: 'localhost:8080/Blade/try_java',
            data: {"frameID": frameID}
            
       });
    },
    "prefill": {
        "name": "Blade Corporation",
        "email": "ekaghni.mukherjee@gmail.com",
        "contact": "9310524629"
    },
    "notes": {
        "address": "Razorpay Corporate Office"
    },
    "theme": {
        "color": "#3399cc"
    }
};
var rzp1 = new Razorpay(options);
rzp1.on('payment.failed', function (response){
        alert(response.error.code);
        alert(response.error.description);
        alert(response.error.source);
        alert(response.error.step);
        alert(response.error.reason);
        alert(response.error.metadata.order_id);
        alert(response.error.metadata.payment_id);
});
document.getElementById('payButton').onclick = function(e){
    rzp1.open();
    e.preventDefault();
}
</script>
	
	




</body>
</html>