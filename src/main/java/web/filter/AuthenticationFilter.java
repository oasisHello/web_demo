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
import jakarta.servlet.http.HttpSession;

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

		// Get the current session, do not create a new one.
		HttpSession session = httpRequest.getSession(false);

		// Get the requested URI
		String uri = httpRequest.getRequestURI();

		// TODO: why need this ?
		// Allow access to login and static resources
		if (uri.endsWith("index_log_in.html") || uri.endsWith("login")) {
			chain.doFilter(request, response);// Proceed to the requested resource
			return;
		}

		// TODO: why need this ?
		// Check if user is logged in
		if (session != null && session.getAttribute("username") != null) {
			chain.doFilter(request, response);
		} else {
			httpResponse.sendRedirect("index_log_in.html");
		}
	}
}
