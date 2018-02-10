


var resultDataArray = seesionStorage.getItem('sales')
var MovieListTableBodyElement = jQuery("#sales_table_body");
		for (var i = 1; i < resultDataArray.length; i++) {
			var rowHTML = "";
			rowHTML += "<tr>";
			rowHTML += "<th>"  + resultDataArray[i]["SaleID"] + "</th>";
			//rowHTML += "<th><a href = \"\SingleMovie.html?Movieid="+ resultDataArray[i]["movieid"]+"\">" + resultDataArray[i]["title"] + "<\a></th>";
			rowHTML += "<th>" + resultDataArray[i]["CustomerID"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["MovieID"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["date"] + "</th>";
			//rowHTML += "<th>" + handleStarsInMovie(resultDataArray[i]["stars"]) + "</th>";
			//rowHTML += "<td>" + makeForm(resultDataArray[i]["movieid"]) + "</td>";
			rowHTML += "</tr>";
			MovieListTableBodyElement.append(rowHTML);
		}