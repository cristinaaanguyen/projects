

function handleStarResult(resultDataArray) {
	console.log("handleStarResult: populating star table from resultData");
	
	// populate the star table
<<<<<<< HEAD
	var results = resultDataArray.sort(function(a,b) {
	    if ( a['title']< b['title'] )
	        return -1;
	    if ( a['title']> b['title'] )
	        return 1;
	    return 0;
	} );
	
	var MovieListTableBodyElement = jQuery("#movie_list_table_body");
	for (var i = 0; i < Math.min(15, results.length); i++) {
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
=======
	if (resultDataArray[0]['errmsg'] == "success"){
		var url = new URL(window.location.href);
		
		var MovieListTableBodyElement = jQuery("#movie_list_table_body");
		for (var i = 1; i < resultDataArray.length; i++) {
			var rowHTML = "";
			rowHTML += "<tr>";
			rowHTML += "<th>"  + resultDataArray[i]["movieid"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["title"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["director"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["year"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["genres"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["stars"] + "</th>";
			rowHTML += "</tr>";
			MovieListTableBodyElement.append(rowHTML);
		}
>>>>>>> 4a5f97a7f90f41617c72f53b682f6c5fba57d616
	}
}

var url = new URL( window.location.href);
var title = url.searchParams.get("title");
console.log("printing title from js file")
console.log(title);
<<<<<<< HEAD

// makes the HTTP GET request and registers on success callback function handleStarResult
jQuery.ajax({
	  dataType: "json",
	  method: "GET",
	  url: "/Fabflix/MovieList?&browse=" + url.searchParams.get("browse") + "&genre="+ url.searchParams.get("genre") +"&title="+ title + "&year=" + 
=======
console.log("printing button val from js file");

var val = $('button').val();
console.log(val);
// makes the HTTP GET request and registers on success callback function handleStarResult

jQuery.ajax({
	  dataType: "json",
	  method: "GET",
	  url: "/Fabflix/MovieList?title="+ title + "&year=" + 
>>>>>>> 4a5f97a7f90f41617c72f53b682f6c5fba57d616
	  url.searchParams.get("year") + "&director=" +  url.searchParams.get("director")+
	  "&starfn="+ url.searchParams.get("starfn") + "&starln="+ url.searchParams.get("starln"),
	  success: (resultData) => handleStarResult(resultData)
});


<<<<<<< HEAD
=======




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
            	var remainingquery = q[1];
            	var url_full = 	"/Fabflix/MovieList.html?" + remainingquery + "&order=" + val;
            	window.location.replace(url_full);
});




$('.pagination').click(function (event){
	event.preventDefault();
    	//formSubmitEvent.preventDefault();
    	var limit = $("#records").val();
    	console.log(limit);
    	var q = window.location.href.split('?');
    	var remainingquery = q[1];
    	
    	var url_full = 	"/Fabflix/MovieList.html?" + remainingquery + "&limit=" + limit;
    	window.location.replace(url_full);
   
});

>>>>>>> 4a5f97a7f90f41617c72f53b682f6c5fba57d616
