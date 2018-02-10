function handleMoviesInStars(resultDataArrayMovie){
	var res = "";
	console.log("trying to link the movies");
	for (var i = 0; i < resultDataArrayMovie.length; i++){
		if (i != 0 ){
			res += ", ";
		}
		console.log("printing movieid");
		console.log(resultDataArrayMovie[i]["movieid"]);
		res += "<a href = \"\SingleMovie.html?Movieid=" + resultDataArrayMovie[i]["movieid"] + "\">" + resultDataArrayMovie[i]["title"] + "</a>"
		console.log(res);}
	return res;
}


function handleStarResult(resultDataArray) {
	console.log("handleStarResult: populating star table from resultData");
	
	// populate the star table
	//if (resultDataArray[0]['errmsg'] == "success"){
		///var url = new URL(window.location.href);
		
		var StarListTableBodyElement = jQuery("#star_table_body");
		//for (var i = 1; i < resultDataArray.length; i++) {
			var rowHTML = "";
			rowHTML += "<tr>";
			//rowHTML += "<th>" + resultDataArray["movieid"] + "</th>";
			rowHTML += "<th>" + resultDataArray["name"] + "</th>";
			rowHTML += "<th>" + resultDataArray["birthYear"] + "</th>";
			//rowHTML += "<th>" + resultDataArray["year"] + "</th>";
			//rowHTML += "<th>" + handleGenresInMovie(resultDataArray["genres"]) + "</th>";
			//rowHTML += "<th>" + resultDataArray["genres"] + "</th>";
			console.log("printing list of stars");
			console.log(resultDataArray["stars"]);
			rowHTML += "<th>" + handleMoviesInStars(resultDataArray["movies"]) + "</th>";
			console.log(rowHTML);
			rowHTML += "</tr>";
			StarListTableBodyElement.append(rowHTML);
		//}
	//}
}

var url = new URL( window.location.href);
//var title = url.searchParams.get("title");
var q = window.location.href.split('?');
console.log(q[1]);
var remainingquery = q[1];




jQuery.ajax({
	  dataType: "json",
	  method: "GET",
	  url: "/Fabflix/SingleEntity?" + q[1],
	  success: (resultData) => handleStarResult(resultData)
});