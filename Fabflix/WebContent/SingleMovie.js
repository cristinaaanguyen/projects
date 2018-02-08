


function handleStarResult(resultDataArray) {
	console.log("handleStarResult: populating star table from resultData");
	
	// populate the star table
	if (resultDataArray[0]['errmsg'] == "success"){
		//var url = new URL(window.location.href);
		
		var MovieListTableBodyElement = jQuery("#movie_table_body");
		for (var i = 1; i < resultDataArray.length; i++) {
			var rowHTML = "";
			rowHTML += "<tr>";
			rowHTML += "<th>" + resultDataArray[i]["movieid"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["title"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["director"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["year"] + "</th>";
			//rowHTML += "<th>" + resultDataArray[i]["genres"] + "</th>";
			//rowHTML += "<th>" + resultDataArray[i]["stars"] + "</th>";
			rowHTML += "</tr>";
			MovieListTableBodyElement.append(rowHTML);
		}
	}
}


var url = new URL( window.location.href);
//var title = url.searchParams.get("title");
var q = window.location.href.split('?');
console.log(q[1]);
var remainingquery = q[1];




jQuery.ajax({
	  dataType: "json",
	  method: "GET",
	  url: "/Fabflix/MovieList?" + q[1],
	  success: (resultData) => handleStarResult(resultData)
});
