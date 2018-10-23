package core;

import javax.ws.rs.core.Response.Status;

public class LoginResponse {
	public String email;
	public String userName;
	public String password;
	public String token;
	public Integer status;
	
	public String getEmail() {
		return email;
	}
	public String getuserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getToken() {
		return token;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void setuserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}