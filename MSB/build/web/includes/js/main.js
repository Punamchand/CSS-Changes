/*price range*/
/*
 $('#sl2').slider();

	var RGBChange = function() {
	  $('#RGB').css('background', 'rgb('+r.getValue()+','+g.getValue()+','+b.getValue()+')')
	};	
		
/*scroll to top*/

$(document).ready(function(){
	$(function () {
		$.scrollUp({
	        scrollName: 'scrollUp', // Element ID
	        scrollDistance: 300, // Distance from top/bottom before showing element (px)
	        scrollFrom: 'top', // 'top' or 'bottom'
	        scrollSpeed: 300, // Speed back to top (ms)
	        easingType: 'linear', // Scroll to top easing (see http://easings.net/)
	        animation: 'fade', // Fade, slide, none
	        animationSpeed: 200, // Animation in speed (ms)
	        scrollTrigger: false, // Set a custom triggering element. Can be an HTML string or jQuery object
					//scrollTarget: false, // Set a custom target element for scrolling to the top
	        scrollText: '<i class="fa fa-angle-up"></i>', // Text for element, can contain HTML
	        scrollTitle: false, // Set a custom <a> title if required.
	        scrollImg: false, // Set true to use image
	        activeOverlay: false, // Set CSS color to display scrollUp active point, e.g '#00FFFF'
	        zIndex: 2147483647 // Z-Index for the overlay
		});
	});
});
jQuery.validator.setDefaults({
debug: true,
success: "valid"
});
$( "#loginForm" ).validate({
rules: {
logiId: {
required: true,
email: true
}
}
});


// Add by Aklakh
function checkEmailIdExistance(){
    $("resultMessage").html(" ");
    $("resetMessage").html(" ");
    var emailId=document.getElementById("email").value;
  //  alert(document.getElementById('email').value);
    var url=CONTENXT_PATH+'/general/resetEmailVerify.action?emailId='+emailId;
    // alert(url);
    var req=initRequest(url);
    req.onreadystatechange = function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                      //          alert(req.responseText);
                //  alert("2");
                emailResponse(req.responseText);
               
            } 
            else
            {
                            
            }
        }
    };
    
    req.open("GET",url,"true");
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send(null);
}
// Add by Aklakh
function emailResponse(response){
    if(response=="success"){
        $("resetMessage").html("  <font color='red'>No user with this email !</font>");
        email.value='';
        return false;
    }
    else{
        return true;
    }
}


