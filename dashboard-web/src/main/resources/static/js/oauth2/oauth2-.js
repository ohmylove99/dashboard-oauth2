		var CLIENT_ID = 'clientIdPassword';
		var CLIENT_SECRET = 'secret';
		var TOKEN_URL = 'http://localhost:9080/oauth/token';
		var USER_NAME = 'john';
		var PASS_WORD = '123';
		// Create Base64 Object

		var Base64_HEADER = Base64.encode(CLIENT_ID+':'+CLIENT_SECRET);
		
		var clientID = 'clientIdPassword';
        var clientSecret = 'secret';
        var authorizeEndpoint = 'http://localhost:9080/oauth/authorize'
        var tokenEndpoint = 'http://localhost:9080/oauth/token';

		   // Initialize library
		/*var auth = new jqOAuth({
		       //csrfToken: "token", //CSRF token,
		       clientID: clientID,
		       clientSecret: clientSecret,
		       authorizeEndpoint: authorizeEndpoint,
		       tokenEndpoint: tokenEndpoint,
		       events: {
		           logout: function(){},
		           login:  function(){
		        	   
		           },
		           tokenExpiration: function(){}
		       }	
		});*/
		$.ajax({
			   url: "http://localhost:9080/oauth/token",
			   method: "POST",
			   data: {
			       username: USER_NAME,
			       password: PASS_WORD,
			       grant_type: 'password',
			       scope: 'read'
			   },
			   beforeSend: function (xhr){ 
			        xhr.setRequestHeader('Authorization', 'Basic '+Base64_HEADER); 
			    },
			   statusCode: {
			       200: function(response) {
			           auth.login(response.access_token, response.expires_in);
			       },
			       401: function() {
			           alert("The username or password were not correct. Try again.");
			       }
			   }
			});