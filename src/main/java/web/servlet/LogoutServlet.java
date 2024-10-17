package web.servlet;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(LogoutServlet.class);

	// Handle GET requests for logout
	// TODO: how to use httpServlet request and respond ?
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Invalidate the session if exists.
		HttpSession session = request.getSession(false);

		if (session != null) {
			String username = (String) session.getAttribute("username");
			session.invalidate();
			logger.info("User {} has logged out.", username);
		}
		// TODO: what does it mean?
		response.sendRedirect("index_log_in.html");
	}

}
