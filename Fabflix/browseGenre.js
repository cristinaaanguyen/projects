function handleGenreResult(resultData) {
	console.log("handleStarResult: populating star table from resultData");

	// populate the star table
	var starTableBodyElement = jQuery("#genre_table_body");
	for (var i = 0; i < resultData.length; i++) {
		var rowHTML = "";
		rowHTML += "<tr>";
		//rowHTML += "<th>" + resultData[i]["genre_name"] + "</th>";
		rowHTML += "<th><a href = \"\MovieList.html?browse=true&genre="+ resultData[i]["genre_name"] + "\">" + resultData[i]["genre_name"] + "<\a></th>";
		//rowHTML += "<th>" + resultData[i]["star_dob"] + "</th>";
		rowHTML += "</tr>"
		starTableBodyElement.append(rowHTML);
	}
}

jQuery.get(
				"/Fabflix/Browse", 
				{type: "genre"},
				(resultDataString) => handleGenreResult(resultDataString));