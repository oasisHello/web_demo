package web.servlet;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProtectedServlet extends HttpServlet {

	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	private String SECRET_KEY = "websecretkeywebsecretkeywebsecretkeywebsecretkey";
	private byte[] secretKeyBytes = Base64.getDecoder().decode(SECRET_KEY);
	protected Key key = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7); // Remove "Bearer " prefix
			
			try {
				Claims claims = Jwts.parserBuilder()
						            .setSigningKey(key)
						            .build()
						            .parseClaimsJws(token)
					                .getBody();
			}catch(Exception e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("{\"message\": \"Invalid token\"}");
			}
		}else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("{\"message\": \"Invalid token\"}");
		}
	}

}
