package core;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import io.jsonwebtoken.*;
import java.util.Date;

public class JWT {

	// Sample method to construct a JWT
	public static String createJWT(String issuer, long ttlMillis) {

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey.getSecret());
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		try {
			// Let's set the JWT Claims
			JwtBuilder builder = Jwts.builder().setIssuedAt(now).setIssuer(issuer).signWith(signatureAlgorithm,
					signingKey);

			// if it has been specified, let's add the expiration
			if (ttlMillis >= 0) {
				long expMillis = nowMillis + ttlMillis;
				Date exp = new Date(expMillis);
				builder.setExpiration(exp);
			}

			// Builds the JWT and serializes it to a compact, URL-safe string
			return builder.compact();
		} catch (JwtException e) {
			// don't trust the JWT!
			System.err.println(e.getMessage());
			return ("0");
		}
	}

	public static TokenResponse authorizeJWT(String jwsString, LoginResponse loginResponse) {
		TokenResponse tokenResponse = new TokenResponse();
		String issuer = null;
		Date expiration = null;
		
		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey.getSecret());
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		try {
			Claims claims = Jwts.parser()
		    	   .setSigningKey(signingKey)
		    	   .parseClaimsJws(jwsString)
		    	   .getBody();
		    // we can safely trust the JWT
		    System.out.println("Key is valid");
		    issuer = claims.getIssuer();
		    expiration = claims.getExpiration();
		    
		    if(issuer.equals(loginResponse.email)) {
		    	tokenResponse.setStatus(200);
		    	tokenResponse.setUser(issuer);
		    	tokenResponse.setExpiration(expiration);
		    	tokenResponse.setToken(jwsString);
		    }
		    else {
		    	tokenResponse.setStatus(205);
		    }
		}  
		catch (JwtException ex) {       // (4)
		    System.out.println("Key is invalid");
		    // we *cannot* use the JWT as intended by its creator
		    tokenResponse.setStatus(205);
		}
		return tokenResponse;
	}
}
