
function handleCartResult(resultDataArray) {
	console.log("handleStarResult: populating star table from resultData");

		var MovieCartTableBodyElement = jQuery("#listcart");
			for (var i = 0; i < resultDataArray.length; i++) {
				var rowHTML = "";
				rowHTML += "<tr>";
				rowHTML += "<th>"  + resultDataArray[i]["title"] + "</th>";
				rowHTML += "<th>" + resultDataArray[i]["quantity"] + "</th>";
				rowHTML += "<td>" + "<div>" +
				  "<input class=\"input-group-field\" type=\"number\" name=\"quantity"+ resultDataArray[i]["movieid"]+"\" value=\""+ 
				  resultDataArray[i]["quantity"]+ "\">" + 
				"</div>" + "<button class=\"btn addCart\" value=\""+ resultDataArray[i]["movieid"] +"\">Edit Cart</button>" + "</td>" ;		
				rowHTML += "</tr>";
				console.log(rowHTML);
				MovieCartTableBodyElement.append(rowHTML);
			}
			addCartFunc();
		

}


jQuery.ajax({
	  dataType: "json",
	  method: "GET",
	  url: "/Fabflix/ShoppingCart",
	  success: (resultData) => handleCartResult(resultData)
});



function addCartFunc() {
	$(".addCart").click(function (event) {
		event.preventDefault();
		console.log("prevented default in addcart func in shopping cart page");
		const mid = $(".addCart").val();
		console.log("printing movieid");
		console.log(mid);
		var currentVal = 0;
		currentVal = parseInt($('input[name=quantity'+mid+']').val());
		if (currentVal < 0 ){
			currentVal = 0;
		} 
	$.post('ShoppingCart',{
		add: "true",
		movieid: mid,
		quantity: currentVal
	})
	
	window.location.replace("/Fabflix/ShoppingCart.html");

	});
	}