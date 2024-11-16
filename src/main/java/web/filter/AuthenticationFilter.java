package web.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.util.JwtUtil;

public class AuthenticationFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	//TODO: how does it invoked?
	// the doFilter() method is invoked automatically by servlet container when the request match the specified URL pattern.
	//request -> url-pattern matched the /* -> AutheFilter(filter-name), could be any -> locate web.filter.AuthenticationFilter(implementing Filter) -> the servlet container will call the doFilter().
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Cast to HttpServletRequest and HttpServletResponse
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// Get the requested URI
		String uri = httpRequest.getRequestURI();

		// TODO: why need this ?
		// Allow access to login and static resources freely.
		if (uri.endsWith("index_log_in.html") || uri.endsWith("login")) {
			chain.doFilter(request, response);// Proceed to the requested resource
			return;
		}
		
		// Extract the token from the Authorization header.(expected format: "Bearer: <token> ")
		String authHeader = httpRequest.getHeader("Authorization");
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			httpResponse.sendRedirect("index_log_in.html");
			return;
		}
		
		// Extract the token from the Authorization header
		String token = authHeader.substring(7); // Remove "Bearer " prefix
		String userName = JwtUtil.extractUsername(token);

		// TODO: why need this ?
		// Check if user is logged in
		try {
			// Validate the JWT token(custom validation method from your JWT utility class)
			if(JwtUtil.validateToken(token,userName)) {
				// If the token is valid, allow the request to proceed to the next filter/servlet
				chain.doFilter(request,response);
			}else {
				httpResponse.sendRedirect("index_log_in.html");
			}
		} catch (Exception e) {
			// Validation fails, redirect to login
			httpResponse.sendRedirect("index_log_in.html");
		}

	}
}
