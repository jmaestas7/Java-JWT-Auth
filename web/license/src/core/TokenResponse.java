package core;

import java.sql.Date;

public class TokenResponse {
	public String token;
	public java.util.Date expiration;
	public String user;
	public Integer status;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public java.util.Date getExpiration() {
		return expiration;
	}
	public void setExpiration(java.util.Date expiration) {
		this.expiration = expiration;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
