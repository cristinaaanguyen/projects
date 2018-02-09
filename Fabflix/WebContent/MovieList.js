

function handleStarsInMovie(resultDataArrayStar){
	var res = "";
	//console.log("trying to link the stars");
	for (var i = 0; i < resultDataArrayStar.length; i++){
		if (i != 0 ){
			res += ", ";
		}

		res += "<a href = \"\MovieStar.html?Starid=" + resultDataArrayStar[i]["starid"] + "\">" + resultDataArrayStar[i]["name"] + "</a>"
		}
	return res;
}

$('.pagination').on('click', 'li:not(.prev):not(.next)', function() {
    $('.pagination li').removeClass('active');
    $(this).not('.prev,.next').addClass('active');
    console.log($(this).val());
    makeNewURL(window.location.href, "page", $(this).val());
    
});

$('.pagination').on('click', 'li.prev', function() {
	event.preventDefault();
    $('li.active').removeClass('active').prev().addClass('active');
    //console.log($(this).val());
    var url = new URL(window.location.href);
    var urlString = url.toString();
    console.log(urlString);
    console.log("printed url string");
    var currPage = parseInt(url.searchParams.get("page")) -1;
    console.log("printing currnet page");
    console.log(currPage);
    makeNewURL(urlString, "page", currPage);

    
});

$('.pagination').on('click', 'li.next', function() {
	event.preventDefault();
    $('li.active').removeClass('active').next().addClass('active');
    var url = new URL(window.location.href);
    var urlString = url.toString();
    console.log(urlString);
    console.log("printed url string");
    var currPage = parseInt(url.searchParams.get("page")) +1;
    console.log("printing currnet page");
    console.log(currPage);    makeNewURL(urlString, "page", currPage);

   
});

function drawPagination(start, end) {
    $('#pag_nav ul').empty();
    $('#pag_nav ul').append('<li class=prev><a href=# aria-label=Previous><span aria-hidden=true>&laquo;</span></a></li>');
   for (var i = start; i <= end; i++) {
	   if (i == start)
		   $('#pag_nav ul').append('<li class = \'active\' value ="' + i + '" ><a href=#> '+ i + '</a></li>');
	   else{
		   
		   $('#pag_nav ul').append('<li value ="' + i + '" ><a href=#> '+ i + '</a></li>');
	   }
   }
   $('#pag_nav ul').append('<li class=next><a href="" aria-label=Previous><span aria-hidden=true>&raquo;</span></a></li>');
}



function handleMovieResult(resultDataArray) {
	console.log("handleStarResult: populating star table from resultData");
	
	// populate the star table
	if (resultDataArray[0]['errmsg'] == "success"){	
		var url = new URL( window.location.href);
		var currpage = parseInt(url.searchParams.get("page"));
		var maxPage = parseInt(resultDataArray[0]['pages']);
		if ((currpage+4) > maxPage){
			drawPagination(currpage, maxPage+1);
		}
		else
			drawPagination(currpage, currpage+4);
		var MovieListTableBodyElement = jQuery("#movie_list_table_body");
		for (var i = 1; i < resultDataArray.length; i++) {
			var rowHTML = "";
			rowHTML += "<tr>";
			rowHTML += "<th>"  + resultDataArray[i]["movieid"] + "</th>";
			rowHTML += "<th><a href = \"\SingleMovie.html?Movieid="+ resultDataArray[i]["movieid"]+"\">" + resultDataArray[i]["title"] + "<\a></th>";
			rowHTML += "<th>" + resultDataArray[i]["director"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["year"] + "</th>";
			rowHTML += "<th>" + resultDataArray[i]["genres"] + "</th>";
			//console.log("printing list of stars");
			//console.log(resultDataArray[i]["stars"]);
			rowHTML += "<th>" + handleStarsInMovie(resultDataArray[i]["stars"]) + "</th>";
			//console.log(rowHTML);
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

//var currpage = parseInt(url.searchParams.get("page"));
//var end = currpage + 4;
//var maxPage = 0;
//drawPagination(currpage, end);


// makes the HTTP GET request and registers on success callback function handleStarResult

jQuery.ajax({
	  dataType: "json",
	  method: "GET",
	  url: "/Fabflix/MovieList?" + q[1],
	  success: (resultData) => handleMovieResult(resultData)
});




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


$('.limit').click(function (event){
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


function getRadioValue(theRadioGroup)
{
    var elements = document.getElementsByName(theRadioGroup);
    for (var i = 0, l = elements.length; i < l; i++)
    {
        if (elements[i].checked)
        {
            return elements[i].value;
        }
    }
}

function validateRadio(order){
	console.log("printing asc or desc");
	//var ordering = $("$this").val();
	console.log(order);
	var q = window.location.href.split('?');
	var remainingquery = q[1];
	console.log("printing remaining query in validateRadio()");
	console.log(remainingquery);
	var url_full = queryStringUrlReplacement("/Fabflix/MovieList.html?" + q[1], "ordering", order);
	console.log(url_full);
	window.location.replace(url_full);
}



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

function makeNewURL(url, new_param, new_val){
	var q = url.split('?');
	var remainingquery = q[1];
	console.log("printing remaining query in makeNewURL");
	console.log(remainingquery);
	var url_full = queryStringUrlReplacement("/Fabflix/MovieList.html?" + q[1], new_param, new_val);
	console.log(url_full);
	window.location.replace(url_full);
}

