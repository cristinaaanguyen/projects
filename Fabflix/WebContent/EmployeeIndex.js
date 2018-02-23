function handleStarResult(resultDataString) {
	console.log("Beginning of handle result");
	console.log(resultDataString);
	//resultDataJson = JSON.parse(resultDataString);
	
	
	console.log(resultDataString);
	console.log(resultDataString["status"]);

	// if search success, redirect to index.html page
	if (resultDataString["status"] == "success") {
			console.log("star successfully added");
			jQuery("#star_message").text(resultDataString["message"]);

		} 
	else {	
		console.log("show error message");
		window.location.replace("/Fabflix/employeeindex.html");
		jQuery("#star_message").text(resultDataString["message"]);
	}
}


function handleMovieResult(resultDataString) {
	console.log("Beginning of handle result");
	console.log(resultDataString);
	//resultDataJson = JSON.parse(resultDataString);
	
	
	console.log(resultDataString);
	console.log(resultDataString["status"]);

	// if search success, redirect to index.html page
	if (resultDataString["status"] == "success") {
			console.log("movie successfully added");
			jQuery("#movie_message").text(resultDataString["spmsg"]);

		} 
	else {	
		console.log("show error message");
		window.location.replace("/Fabflix/employeeindex.html");
		jQuery("#movie_message").text(resultDataString["spmsg"]);
	}
}





function submitStarForm(formSubmitEvent) {
	console.log("submit Star form");
	
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	console.log("before jQuery preventDefault");
	formSubmitEvent.preventDefault();
	console.log("before jQuery post");
	jQuery.post(
		"/Fabflix/EmployeeIndex", 
		// serialize the search form to the data sent by POST request
		jQuery("#star_form").serialize(),
		(resultDataString) => handleStarResult(resultDataString));

}


function submitMovieForm(formSubmitEvent) {
	console.log("submit Movie form");
	
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	console.log("before jQuery preventDefault");
	formSubmitEvent.preventDefault();
	console.log("before jQuery post");
	jQuery.post(
		"/Fabflix/EmployeeIndex", 
		// serialize the search form to the data sent by POST request
		jQuery("#movie_form").serialize(),
		(resultDataString) => handleMovieResult(resultDataString));

}

// bind the submit action of the form to a handler function
jQuery("#star_form").submit((event) => submitStarForm(event));

jQuery("#movie_form").submit((event) => submitMovieForm(event));