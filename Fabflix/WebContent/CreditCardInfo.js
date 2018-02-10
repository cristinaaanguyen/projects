function handleCardResult(resultDataString) {
	console.log("Beginning of handle result");
	console.log(resultDataString);
	//resultDataJson = JSON.parse(resultDataString);
	
	
	console.log(resultDataString);
	console.log(resultDataString["status"]);

	// if login success, redirect to index.html page
	if (resultDataString[0]["status"] == "success") {
		
		sessionStorage.setItem('sales', resultDataString)
		window.location.replace("/Fabflix/ConfirmationPage.html");
	} else {
		console.log("show error message");
		console.log(resultDataString[0]["message"]);
		jQuery("#card_error_message").text(resultDataString[0]["message"]);
	}
}


function submitSearchForm(formSubmitEvent) {
	console.log("submit Search form");
	
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	console.log("before jQuery preventDefault");
	formSubmitEvent.preventDefault();
	console.log("before jQuery post");
	jQuery.post(
		"/Fabflix/CreditCardInfo", 
		// serialize the search form to the data sent by POST request
		jQuery("#credit_card_form").serialize(),
		(resultDataString) => handleCardResult(resultDataString));

}

// bind the submit action of the form to a handler function
jQuery("#credit_card_form").submit((event) => submitSearchForm(event));