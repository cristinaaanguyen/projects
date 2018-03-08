
function handleLoginResult(resultDataString) {
	console.log("Beginning of handle result");
	console.log(resultDataString);
	//resultDataJson = JSON.parse(resultDataString);
	
	
	console.log(resultDataString);
	console.log(resultDataString["status"]);

	// if login success, redirect to home.html page
	if (resultDataString["status"] == "success") {
		window.location.replace("/Fabflix/home.html");
	} else {
		console.log("show error message");
		console.log(resultDataString["message"]);
		jQuery("#login_error_message").text(resultDataString["message"]);
	}
}

function submitLoginForm(formSubmitEvent) {
	console.log("submit login form");
	
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	console.log("before jQuery post");
	formSubmitEvent.preventDefault();
		
	jQuery.post(
		"/Fabflix/loginServlet", 
		// serialize the login form to the data sent by POST request
		jQuery("#login_form").serialize(),
		(resultDataString) => handleLoginResult(resultDataString));

}

// bind the submit action of the form to a handler function
jQuery("#login_form").submit((event) => submitLoginForm(event));

