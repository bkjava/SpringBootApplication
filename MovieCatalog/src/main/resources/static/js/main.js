$(document).ready(function () {
	
	$('#healthCheck').on("click",function(event) {
		event.preventDefault();		
        healthCheck_submit();

		});
	
	$('#moviesearch').on("click",function(event) {
		event.preventDefault();		
        movieSearch_submit();

		});
		
	$('#usersearch').on("click",function(event) {
		event.preventDefault();
       userSearch_submit();

		});
});

function healthCheck_submit(){
	
    $("#moviesearch").prop("disabled", true);
	$("#usersearch").prop("disabled", true);
	$("#healthCheck").prop("disabled", true);
	
	
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/catalog/healthCheck",
        data: "",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#moviesearch").prop("disabled", false);
			$("#usersearch").prop("disabled", false);	
			$("#healthCheck").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#moviesearch").prop("disabled", false);
			$("#usersearch").prop("disabled", false);
			$("#healthCheck").prop("disabled", false);

        }
    });
    
}

function userSearch_submit() {

    $("#moviesearch").prop("disabled", true);
	$("#usersearch").prop("disabled", true);
	$("#healthCheck").prop("disabled", true);
	
	var user = $("#userId").val();
	if( user!= ""){	
		$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/catalog/userCatalog/"+user,
        data: "",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#moviesearch").prop("disabled", false);
			$("#usersearch").prop("disabled", false);
			$("#healthCheck").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#moviesearch").prop("disabled", false);
			$("#usersearch").prop("disabled", false);
			$("#healthCheck").prop("disabled", false);

        }
    });	
	}
	else {
		
		alert("Enter User Id");
		$("#moviesearch").prop("disabled", false);
			$("#usersearch").prop("disabled", false);
			$("#healthCheck").prop("disabled", false);
	}
	
	
}

function movieSearch_submit() {

    $("#moviesearch").prop("disabled", true);
	$("#usersearch").prop("disabled", true);
	$("#healthCheck").prop("disabled", true);
	
	var movie = $("#movieId").val();
	var requestURL ="";
	if( movie!= ""){		
		requestURL ="/catalog/movieCatalog/"+movie;		
	}
	else {
		
		requestURL ="/catalog/movieCatalogs";
	}
	
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: requestURL,
        data: "",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#moviesearch").prop("disabled", false);
			$("#usersearch").prop("disabled", false);
			$("#healthCheck").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#moviesearch").prop("disabled", false);
			$("#usersearch").prop("disabled", false);
			$("#healthCheck").prop("disabled", false);
        }
    });
    

}