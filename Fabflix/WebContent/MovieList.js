

function handleStarResult(resultDataArray) {
	console.log("handleStarResult: populating star table from resultData");
	
	// populate the star table

	var url = new URL(window.location.href);
	
	var MovieListTableBodyElement = jQuery("#movie_list_table_body");
	for (var i = 0; i < resultDataArray.length; i++) {
		var rowHTML = "";
		rowHTML += "<tr>";
		rowHTML += "<th>" + resultDataArray[i]["movieid"] + "</th>";
		rowHTML += "<th>" + resultDataArray[i]["title"] + "</th>";
		rowHTML += "<th>" + resultDataArray[i]["director"] + "</th>";
		rowHTML += "<th>" + resultDataArray[i]["year"] + "</th>";
		rowHTML += "<th>" + resultDataArray[i]["genres"] + "</th>";
		rowHTML += "<th>" + resultDataArray[i]["stars"] + "</th>";
		rowHTML += "</tr>"
		MovieListTableBodyElement.append(rowHTML);
	}
}

var url = new URL( window.location.href);
var title = url.searchParams.get("title");
console.log("printing title from js file")
console.log(title);

// makes the HTTP GET request and registers on success callback function handleStarResult
jQuery.ajax({
	  dataType: "json",
	  method: "GET",
	  url: "/Fabflix/MovieList?title="+ title + "&year=" + 
	  url.searchParams.get("year") + "&director=" +  url.searchParams.get("director")+
	  "&starfn="+ url.searchParams.get("starfn") + "&starln="+ url.searchParams.get("starln"),
	  success: (resultData) => handleStarResult(resultData)
});

