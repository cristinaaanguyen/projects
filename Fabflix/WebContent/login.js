function handleLoginResult(resultData) {
	console.log("handleResult: Getting login info");

	// populate the star table
	var LoginElement = jQuery("#login");
	console.log(resultData);

}

// makes the HTTP GET request and registers on success callback function handleStarResult
function login(username, password){
	jQuery.ajax({
		  dataType: "json",
		  method: "POST",
		  url: "/Fabflix/loginServlet?username=" + username + "&" + "password=" + password,
		  success: (resultData) => handleLoginResult(resultData)
	});
}

$('#sumbit').on('click', function(event) {
	  event.preventDefault(); // To prevent following the link (optional)
	  var username = $('#email').val();
	  var password = $('#password').val();
	  login(username, password);
	});