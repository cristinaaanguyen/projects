


function handleSearchResult(resultDataString) {
	console.log("Beginning of handle result");
	console.log(resultDataString);
	//resultDataJson = JSON.parse(resultDataString);
	
	
	console.log(resultDataString);
	console.log(resultDataString["title"]);

	// if search success, redirect to index.html page
	if (resultDataString["title"] != "failed") {
			console.log("title is valid");
			var url = "/Fabflix//MovieList.html?title=" +resultDataString['title'];
			url += "&year=" + resultDataString["year"] + "&director=" +  resultDataString["director"] + "&starfn=" +  
			resultDataString["starfn"] + "&starln=" + resultDataString["starln"] + "&page=" + resultDataString["page"];
			//var modifiedUrl = url.toString();
			console.log(url);
			//var title = resultDataString["title"].split(' ').join('%20')
			//window.location.href = modifiedUrl;
			window.location.replace(url);
		} 
	else {	
		console.log("show error message");
		window.location.replace("/Fabflix/search.html");
		console.log(resultDataArray["title"]);
		jQuery("#search_error_message").text(resultDataArray[0]["title"]);
	}
}



function sortByKey(array, key) {
	return array.sort(function(a, b) {
	    var x = a[key]; var y = b[key];
	    return ((x < y) ? -1 : ((x > y) ? 1 : 0));
	});
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
		"/Fabflix/Search", 
		// serialize the search form to the data sent by POST request
		jQuery("#search_form").serialize(),
		(resultDataString) => handleSearchResult(resultDataString));

}

// bind the submit action of the form to a handler function
jQuery("#search_form").submit((event) => submitSearchForm(event));



