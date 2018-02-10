function onTitleClick(event) {
	//console.log("submit login form");
	
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	//console.log("before jQuery post");
	//formSubmitEvent.preventDefault();
		window.location.replace("/Fabflix/BrowseTitle.html")
	//jQuery.post(
	//	"/Fabflix/Browse", 
		// serialize the login form to the data sent by POST request
	//	{type: "title"},
	//	(resultDataString) => handleLoginResult(resultDataString));

}


function onGenreClick(event) {
	console.log("submit login form");
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	window.location.replace("/Fabflix/BrowseGenre.html")
	console.log("before jQuery post");
	//formSubmitEvent.preventDefault();
	
	//jQuery.post(
	//		"/Fabflix/Browse", 
	//		{type: "genre"},
	//		(resultDataString) => handleLoginResult(resultDataString));
}

//function onTitleClick(event) {
	
//}

jQuery("#genre").click((event) => onGenreClick(event));
jQuery("#title").click((event) => onTitleClick(event));
