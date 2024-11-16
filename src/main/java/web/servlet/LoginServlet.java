package web.servlet;

import java.io.IOException;
import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.DBUtil;

public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
	protected static final String SECRET_KEY = "websecretkeywebsecretkeywebsecretkeywebsecretkey"; // 72 bit
	protected byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
	protected Key key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

	// Backed: Handle POST request (from the login form)
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		logger.info("Login attempt for user: {}", username);

		// Validate credentials
		boolean isValid = validateUser(username, password);
		if (isValid) {
			// Generate JWT token
			String jwtToken = Jwts.builder().setSubject(username).setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 minutes
					.signWith(key, SignatureAlgorithm.HS256).compact();
			response.setContentType("application/json");
			response.getWriter().write("{\"token\": \"" + jwtToken + "\"}");
			//response.sendRedirect("/hello");
			logger.info("User {} logged in successfully.", username);

		} else {
			logger.warn("Invalid login attempt for user:{}.", username);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("{\"message\": \" Invalid credentials\"} ");
			
		}
	}

	private boolean validateUser(String username, String password) {
		// TODO: to be refined.
		String query = "SELECT * FROM sys_user WHERE user_name = ? AND password = ? ";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, username);
			stmt.setString(2, password); // TODO: In production, passwords should be hashed!

			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next(); // Returns true if a matching user is found
			}

		} catch (SQLException e) {
			logger.error("Database error during user validation", e);
			return false;
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// For simplicity, redirect GET to login page.
		response.sendRedirect("index_log_in.html");
	}

}
