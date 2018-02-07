//function handleGenreResult(resultData) {
//	console.log("handleStarResult: populating star table from resultData");

	// populate the star table
	var starTableBodyElement = jQuery("#title_table_body");
	for (var i = 48; i < 91; i++) {
		if (!(i >= 58 && i <= 64)){
		var url = "<th><a id=link"+ String.fromCharCode(i)+" href = \"\MovieList.html?browse=true&title="+ String.fromCharCode(i) + "\">" + String.fromCharCode(i) + "<\a></th>";//"<th><a id=link"+ String.fromCharCode(i)+" href = \"\MovieList.html\" onclick=\"location.href=this.href+?browse=true&title="+String.fromCharCode(i)+ ";return false;\">" + String.fromCharCode(i) + "<\a></th>";
		var rowHTML = "";
		rowHTML += "<tr>";
		rowHTML += "<th><a id=link"+ String.fromCharCode(i)+" href = \"\MovieList.html?browse=true&title="+ String.fromCharCode(i) + "\">" + String.fromCharCode(i) + "<\a></th>";
		//rowHTML += "<th>" + resultData[i]["star_dob"] + "</th>";
		rowHTML += "</tr>"
		starTableBodyElement.append(rowHTML);
		console.log(url)
		}
	}
//}