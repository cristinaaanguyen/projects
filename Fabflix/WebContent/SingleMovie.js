
function handleGenresInMovie(resultDataArrayGenre){
	var res = "";
	console.log("trying to link the Genres");
	for (var i = 0; i < resultDataArrayGenre.length; i++){
		if (i != 0 ){
			res += ", ";
		}
		console.log("printing Genre name");
		console.log(resultDataArrayGenre[i]["name"]);
		res += "<a href = \"\MovieList.html?browse=true&genre=" + resultDataArrayGenre[i]["name"] + "\">" + resultDataArrayGenre[i]["name"] + "</a>"
		console.log(res);}
	return res;
}

function handleStarsInMovie(resultDataArrayStar){
	var res = "";
	console.log("trying to link the stars");
	for (var i = 0; i < resultDataArrayStar.length; i++){
		if (i != 0 ){
			res += ", ";
		}
		console.log("printing starid");
		console.log(resultDataArrayStar[i]["starid"]);
		res += "<a href = \"\MovieStar.html?Starid=" + resultDataArrayStar[i]["starid"] + "\">" + resultDataArrayStar[i]["name"] + "</a>"
		console.log(res);}
	return res;
}

function handleStarResult(resultDataArray) {
	console.log("handleStarResult: populating star table from resultData");
	
	// populate the star table
	//if (resultDataArray[0]['errmsg'] == "success"){
		///var url = new URL(window.location.href);
		
		var MovieListTableBodyElement = jQuery("#movie_table_body");
		//for (var i = 1; i < resultDataArray.length; i++) {
			var rowHTML = "";
			rowHTML += "<tr>";
			//rowHTML += "<th>" + resultDataArray["movieid"] + "</th>";
			rowHTML += "<th>" + resultDataArray["title"] + "</th>";
			rowHTML += "<th>" + resultDataArray["director"] + "</th>";
			rowHTML += "<th>" + resultDataArray["year"] + "</th>";
			rowHTML += "<th>" + handleGenresInMovie(resultDataArray["genres"]) + "</th>";
			//rowHTML += "<th>" + resultDataArray["genres"] + "</th>";
			console.log("printing list of stars");
			console.log(resultDataArray["stars"]);
			rowHTML += "<th>" + handleStarsInMovie(resultDataArray["stars"]) + "</th>";
			console.log(rowHTML);
			rowHTML += "</tr>";
			MovieListTableBodyElement.append(rowHTML);
		//}
	//}
}

/*function handleStarResult(resultDataArray) {
	console.log("handleStarResult: populating star table from resultData");
	
	// populate the star table
	//if (resultDataArray[0]['errmsg'] == "success"){
		//var url = new URL(window.location.href);
		
		var MovieListTableBodyElement = jQuery("#movie_table_body");
		//for (var i = 1; i < resultDataArray.length; i++) {
			var rowHTML = "";
			rowHTML += "<tr>";
			rowHTML += "<th>" + resultDataArray["movieid"] + "</th>";
			rowHTML += "<th>" + resultDataArray["title"] + "</th>";
			rowHTML += "<th>" + resultDataArray["director"] + "</th>";
			rowHTML += "<th>" + resultDataArray["year"] + "</th>";
			//rowHTML += "<th>" + resultDataArray[i]["genres"] + "</th>";
			//rowHTML += "<th>" + resultDataArray[i]["stars"] + "</th>";
			rowHTML += "</tr>";
			MovieListTableBodyElement.append(rowHTML);
		//}
	//}
}
*/

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
