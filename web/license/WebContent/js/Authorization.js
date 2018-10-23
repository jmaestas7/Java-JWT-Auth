function authorizePage(page) {
	var url = window.location.href;
	function getCookie(name) {
		var value = "; " + document.cookie;
		var parts = value.split("; " + name + "=");
		if (parts.length == 2)
			return parts.pop().split(";").shift();
	};
	
	var token = getCookie("token");
	var email = getCookie("username");
	
	data = {};
	data.token = token;
	data.email = email;
	data = JSON.stringify(data);
	res = {};
	res.status = null

	$.post({
				url : url.replace(page, 'ws/core/authorize'),
				data : data,
				success : function(res) {
					if(token == null) {
						alert("Timed out. Please log in again");
						var redirect = window.location.href.replace(page,
						"login.html");
						window.location.href = redirect;
					}
					else {
						if (res.status == 200) {
						} else if (res.status == 204) {
							var redirect = window.location.href.replace(page,
									"login.html");
							window.location.href = redirect;
						} else if (res.status == 205 || res.status == null) {
							var redirect = window.location.href.replace(page,
									"login.html");
							window.location.href = redirect;
						}
					}
				},
				error : function(err) {
					var redirect = window.location.href.replace(page,
					"login.html");
					window.location.href = redirect;
				},
				dataType : 'json',
				contentType : 'application/json'
			});
};