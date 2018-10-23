package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/core")
public class CoreWS extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@POST
	@Path("/login")
	@Consumes({ (MediaType.APPLICATION_JSON) })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response loginUser(@Context HttpServletRequest request, LoginRequest loginRequest) {
		Response res = null;
		LoginResponse loginResponse = new LoginResponse();

		try {
			loginResponse.setEmail(loginRequest.email);
			loginResponse.setPassword(loginRequest.password);
			loginResponse = UserDB.queryUserLogin(loginResponse);
			res = Response.status(loginResponse.status).entity(loginResponse).build();
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
			res = Response.noContent().build();
		} finally {

		}
		return res;
	}

	@POST
	@Path ("/authorize")
	@Consumes({(MediaType.APPLICATION_JSON)})
	@Produces({MediaType.APPLICATION_JSON})
	public Response authorizeUser(@Context HttpServletRequest request, LoginRequest loginRequest) {
		LoginResponse loginResponse = new LoginResponse();
		TokenResponse tokenResponse = new TokenResponse();
		Response res = null;
		
		try {
		loginResponse.setEmail(loginRequest.email);
		tokenResponse =  JWT.authorizeJWT(loginRequest.token, loginResponse);
		res = Response.status(tokenResponse.status).entity(tokenResponse).build();
		} catch (Exception ex) {
			
		} finally {
		}
		return res;
	}
	
	@POST
	@Path ("/logout")
	@Consumes({(MediaType.APPLICATION_JSON)})
	@Produces({MediaType.APPLICATION_JSON})
	public Response deauthorizeUser(@Context HttpServletRequest request, LoginRequest loginRequest) {
		return null;
	}

	@POST
	@Path("/version")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response version(@Context HttpServletRequest request, VersionRequest versionRequest) {
		Response res = null;
		try {
			VersionResponse versionResponse = new VersionResponse();
			versionResponse.setVersion("0.01");
			res = Response.status(200).entity(versionResponse).build();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			res = Response.noContent().build();
		} finally {
		}
		return res;
	}
}
