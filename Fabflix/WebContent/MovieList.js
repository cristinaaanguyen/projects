function handleStarResult(resultDataArray) {
	console.log("handleStarResult: populating star table from resultData");

	// populate the star table
	var results = resultDataArray.sort(function(a,b) {
	    if ( a['title']< b['title'] )
	        return -1;
	    if ( a['title']> b['title'] )
	        return 1;
	    return 0;
	} );
	var MovieListTableBodyElement = jQuery("#movie_list_table_body");
	for (var i = 0; i < Math.min(10, results.length); i++) {
		var rowHTML = "";
		rowHTML += "<tr>";
		rowHTML += "<th>" + results[i]["movieid"] + "</th>";
		rowHTML += "<th>" + results[i]["title"] + "</th>";
		rowHTML += "<th>" + results[i]["director"] + "</th>";
		rowHTML += "<th>" + results[i]["year"] + "</th>";
		rowHTML += "<th>" + results[i]["genres"] + "</th>";
		rowHTML += "<th>" + results[i]["stars"] + "</th>";
		rowHTML += "</tr>"
		MovieListTableBodyElement.append(rowHTML);
	}
}

// makes the HTTP GET request and registers on success callback function handleStarResult
jQuery.ajax({
	  dataType: "json",
	  method: "GET",
	  url: "/Fabflix/MovieList",
	  success: (resultData) => handleStarResult(resultData)
});


