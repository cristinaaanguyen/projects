var url = new URL( window.location.href);
//var title = url.searchParams.get("title");
var q = window.location.href.split('?');
console.log(q[1]);
var remainingquery = q[1];




jQuery.ajax({
	  dataType: "json",
	  method: "GET",
	  url: "/Fabflix/ShoppingCart?" + q[1],
	  success: (resultData) => handleStarResult(resultData)
});