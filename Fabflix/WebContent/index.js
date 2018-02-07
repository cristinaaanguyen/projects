/*$(document).ready(function(){
    $('#btn-click').click(function() {
        switch(this.id) {
            case 'search':window.location.replace = "/Fabflix/search.html";
            	console.log("pressed search");break;
            case 'browse': window.location.href="http://yahoo.com"; break;
        }
    });
})
*/

function redirectTo(sUrl) {
	window.location.replace(sUrl);
}