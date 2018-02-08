

function handleStarResult(resultDataArray) {
	console.log("handleStarResult: populating star table from resultData");
	
	// populate the star table
	if (resultDataArray[0]['errmsg'] == "success"){
		var url = new URL(window.location.href);
		
		var MovieListTableBodyElement = jQuery("#movie_list_table_body");
		for (var i = 1; i < resultDataArray.length; i++) {
			var rowHTML = "";
			rowHTML += "<tr>";
			rowHTML += "<th>"  + resultDataArray[i]["movieid"] + "</th>";
			rowHTML += "<th><a href = \"\SingleMovie.html?id="+ resultDataArray[i]["movieid"]+"\">" + resultDataArray[i]["title"] + "<\a></th>";
			rowHTML += "<th>" + resultDataArray[i]["director"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["year"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["genres"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["stars"] + "</th>";
			rowHTML += "</tr>";
			MovieListTableBodyElement.append(rowHTML);
		}
	}
}
//"<th><a id=link"+ String.fromCharCode(i)+" href = \"\MovieList.html?browse=true&title="+ String.fromCharCode(i) + "\">" + String.fromCharCode(i) + "<\a></th>";

var url = new URL( window.location.href);
//var title = url.searchParams.get("title");
var q = window.location.href.split('?');
console.log(q[1]);
var remainingquery = q[1];
/*
console.log("printing title from js file")
console.log(title);

console.log("printing button val from js file");

var val = $('button').val();
console.log(val);
*/
// makes the HTTP GET request and registers on success callback function handleStarResult

jQuery.ajax({
	  dataType: "json",
	  method: "GET",
	  url: "/Fabflix/MovieList?" + q[1],
	  success: (resultData) => handleStarResult(resultData)
});





/*
function submitForm(formSubmitEvent) {
	console.log("submit form");
	
	// important: disable the default action of submitting the form
	//   which will cause the page to refresh
	//   see jQuery reference for details: https://api.jquery.com/submit/
	formSubmitEvent.preventDefault();
	console.log("printing button value before preventDefault");
	var val = $(this).val();
	console.log(val);
	var url = new URL( window.location.href);
	console.log(url);
	var url_full = "/Fabflix/MovieList.html?title="+ title + "&year=" + 
  	  	url.searchParams.get("year") + "&director=" +  url.searchParams.get("director")+
  	  	"&starfn="+ url.searchParams.get("starfn") + "&starln="+ url.searchParams.get("starln") + "&button=" + val;
	window.location.replace(url_full);
	//jQuery.post(
	//	url, 
		// serialize the login form to the data sent by POST request
	//	jQuery("#ordering_by").serialize(),
	//	(resultDataString) => handleStarResult(resultDataString));

}


jQuery("#ordering_by").submit((event) => submitForm(event));

*/

$('.order').click(function (event){
			event.preventDefault();
            	//formSubmitEvent.preventDefault();
            	var val = $(this).val();
            	console.log(val);
            	
            	var q = window.location.href.split('?');
            	console.log(q[1]);
            	var remainingquery = q[1];
            	/*
            	var url_full = 	"/Fabflix/MovieList.html?" + remainingquery + "&order=" + val;
            	console.log("printing url after choosing to sort by title or year");
            	*/
            	var url_full = queryStringUrlReplacement("/Fabflix/MovieList.html?" + q[1], "order", val);
            	console.log(url_full);
            	window.location.replace(url_full);
});


$('.pagination').click(function (event){
	event.preventDefault();
    	//formSubmitEvent.preventDefault();
    	var limit = $("#records").val();
    	console.log(limit);
    	var q = window.location.href.split('?');
    	var remainingquery = q[1];
    	console.log("printing remaining query in .pagination");
    	console.log(remainingquery);
    	var url_full = queryStringUrlReplacement("/Fabflix/MovieList.html?" + q[1], "limit", limit);
    	console.log(url_full);
    	window.location.replace(url_full);
   
});


function queryStringUrlReplacement(url, param, value) 
{
    var re = new RegExp("[\\?&]" + param + "=([^&#]*)"), match = re.exec(url), delimiter, newString;

    if (match === null) {
        // append new param
        var hasQuestionMark = /\?/.test(url); 
        delimiter = hasQuestionMark ? "&" : "?";
        newString = url + delimiter + param + "=" + value;
    } else {
        delimiter = match[0].charAt(0);
        newString = url.replace(re, delimiter + param + "=" + value);
    }

    return newString;
}

