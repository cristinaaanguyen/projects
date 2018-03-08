/*$(document).ready(function(){
    $('#btn-click').click(function() {
        switch(this.id) {
            case 'search':window.location.replace = "/Fabflix/search.html";
            	console.log("pressed search");break;
            case 'browse': window.location.href="http://yahoo.com"; break;
        }
    });
})
*/



function redirectTo(sUrl) {
	window.location.replace(sUrl);

}

//declare global variable

/*
 var person = {
  first_name: "John",
  last_name: "Smith",
  age: 39
  
  person['first_name'] will retreive the value
};
 */

var map = {};


/*
 * CS 122B Project 4. Autocomplete Example.
 * 
 * This Javascript code uses this library: https://github.com/devbridge/jQuery-Autocomplete
 * 
 * This example implements the basic features of the autocomplete search, features that are 
 *   not implemented are mostly marked as "TODO" in the codebase as a suggestion of how to implement them.
 * 
 * To read this code, start from the line "$('#autocomplete').autocomplete" and follow the callback functions.
 * 
 */


/*
 * This function is called by the library when it needs to lookup a query.
 * 
 * The parameter query is the query string.
 * The doneCallback is a callback function provided by the library, after you get the
 *   suggestion list from AJAX, you need to call this function to let the library know.
 */
function handleLookup(query, doneCallback) {
	console.log("autocomplete initiated")
	
	// TODO: if you want to check past query results first, you can do it here
	if (map.hasOwnProperty(query)) {
		console.log("query is in global variable");
		doneCallback( { suggestions: map[query] } );

	}
	
	// sending the HTTP GET request to the Java Servlet endpoint hero-suggestion
	// with the query data
	else {
		console.log("sending AJAX request to backend Java Servlet")

		jQuery.ajax({
			"method": "GET",
			// generate the request url from the query.
			// escape the query string to avoid errors caused by special characters 
			"url": "Home?query=" + escape(query),
			"success": function(data) {
				// pass the data, query, and doneCallback function into the success handler
				handleLookupAjaxSuccess(data, query, doneCallback) 
			},
			"error": function(errorData) {
				console.log("lookup ajax error")
				console.log(errorData)
			}
		})
	}
}


/*
 * This function is used to handle the ajax success callback function.
 * It is called by our own code upon the success of the AJAX request
 * 
 * data is the JSON data string you get from your Java Servlet
 * 
 */
function handleLookupAjaxSuccess(data, query, doneCallback) {
	console.log("lookup ajax successful")
	
	// parse the string into JSON
	var jsonData = data;
	console.log("after parsing data ")

	console.log(jsonData);
	// TODO: if you want to cache the result into a global variable you can do it here, make a map -> query : results
	console.log("adding query to map");
	map[query] = jsonData;
	

	// call the callback function provided by the autocomplete library
	// add "{suggestions: jsonData}" to satisfy the library response format according to
	//   the "Response Format" section in documentatio
		doneCallback( { suggestions: jsonData } );
	
}


/*
 * This function is the select suggestion hanlder function. 
 * When a suggestion is selected, this function is called by the library.
 * 
 * You can redirect to the page you want using the suggestion data.
 */
function handleSelectSuggestion(suggestion) {
	// TODO: jump to the specific result page based on the selected suggestion
	
	console.log("you select " + suggestion["value"])
	var category =  suggestion["data"]["category"];
	var url = "";
	if (category == "movies"){
		url = "/Fabflix/SingleMovie.html?Movieid=" + suggestion["data"]["movieid"];
		redirectTo(url);
		//change locations 
	}
	else {
		url = "/Fabflix/MovieStar.html?Starid=" + suggestion["data"]["starid"];
		redirectTo(url);
		

	}
	console.log(url)
}


/*
 * This statement binds the autocomplete library with the input box element and 
 *   sets necessary parameters of the library.
 * 
 * The library documentation can be find here: 
 *   https://github.com/devbridge/jQuery-Autocomplete
 *   https://www.devbridge.com/sourcery/components/jquery-autocomplete/
 * 
 */
// $('#autocomplete') is to find element by the ID "autocomplete"
$('#searchfield input').autocomplete({
	minChars: 3,
	// documentation of the lookup function can be found under the "Custom lookup function" section
    lookup: function (query, doneCallback) {
    		handleLookup(query, doneCallback)
    },
    lookupLimit: 10,
    onSelect: function(suggestion) {
    		handleSelectSuggestion(suggestion)
    },
    // set the groupby name in the response json data field
    groupBy: "category",
    // set delay time
    deferRequestBy: 300,
    // there are some other parameters that you might want to use to satisfy all the requirements
    // TODO: add other parameters, such as mininum characters
});


/*
 * do normal full text search if no suggestion is selected 
 */
function handleNormalSearch(query) {
	console.log("doing normal search with query: " + query);
	// TODO: you should do normal search here
	var url = "/Fabflix//MovieList.html?title=" + query;
	console.log("redirecting url");

	redirectTo(url);
}

// bind pressing enter key to a hanlder function
$('#searchfield input').keypress(function(event) {
		
	// keyCode 13 is the enter key
	if (event.keyCode == 13) {
		console.log("enter key was pressed");
        event.preventDefault();

		// pass the value of the input box to the hanlder function
		handleNormalSearch($('#searchfield input').val());
	}
})

// TODO: if you have a "search" button, you may want to bind the onClick event as well of that button

function submitSearchForm(formSubmitEvent) {
	console.log("submit Search form");
	
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	console.log("before jQuery preventDefault");
	formSubmitEvent.preventDefault();
	console.log("before handling normal search");
	handleNormalSearch($('#searchfield input').val());
	


}

jQuery("#advanced_search_form").submit((event) => submitSearchForm(event));
