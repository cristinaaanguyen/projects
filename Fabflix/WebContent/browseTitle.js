//function handleGenreResult(resultData) {
//	console.log("handleStarResult: populating star table from resultData");

	// populate the star table
	var starTableBodyElement = jQuery("#title_table_body");
	for (var i = 48; i < 91; i++) {
		if (!(i >= 58 && i <= 64)){
		var rowHTML = "";
		rowHTML += "<tr>";
		rowHTML += "<th><a href = \"\MovieList.html?title="+ String.fromCharCode(i) + "\">" + String.fromCharCode(i) + "<\a></th>";
		//rowHTML += "<th>" + resultData[i]["star_dob"] + "</th>";
		rowHTML += "</tr>"
		starTableBodyElement.append(rowHTML);
		}
	}
//}