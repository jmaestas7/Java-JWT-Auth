var url = window.location.href;

function getData() {
	var email = $('#emailInput').val().trim().toLowerCase();
	var password = SHA256($('#passInput').val());
	var data = {};
	data.email = email;
	data.password = password;
	data = JSON.stringify(data);
	return data;
};

function postData(json) {
	$.post({
		method : 'POST',
		url : url.replace('login.html', 'ws/core/login'),
		data : json,
		success : function(res, status, jqXHR) {
			if (res.status == 200) {
				data = {
					"token" : res.token
				};
				data.email = res.email;
				data = JSON.stringify(data);
				$.post({
					url : url.replace('login.html', 'ws/core/authorize'),
					data : data,
					success : function(res, status, jqXHR) {
						if (res.status == 200) {
							var d = new Date(res.expiration);
							var tokenCookie = "token=" + res.token
									+ "; expires=" + createExp(createDate(d))
									+ "; path=/";
							var userCookie = " username=" + res.user
									+ "; path=/";
							document.cookie = tokenCookie;
							document.cookie = userCookie;
							var redirect = window.location.href.replace(
									"login.html", "dashboard.html");
							window.location.href = redirect;

						} else if (res.status == 204) {
							alert("Authorization Error");
						} else if (res.status == 205 || res.status == null) {
							alert("Token is invalid, try again");
						}
					},
					error : function(err) {
						console.log(err);
					},
					dataType : 'json',
					contentType : 'application/json'
				});
			} else if (res.status == 201) {
				alert("Could Not Connect. Pleas try again later.");
			} else if (res.status == 202) {
				alert("Password does not match username");
			} else if (res.status == 203) {
				alert("User does not exist");
			} else if (res.status == 204) {
				alert("Authorization Error");
			}
		},
		error : function(err) {
			console.log(err);
		},
		dataType : 'json',
		contentType : 'application/json'
	});
};