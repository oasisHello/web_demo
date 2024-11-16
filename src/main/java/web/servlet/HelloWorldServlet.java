package web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import web.DBUtil;

/**
 * Servlet implementation class HelloWorldServlet
 */
public class HelloWorldServlet extends jakarta.servlet.http.HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(HelloWorldServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().println("Hello, World!");
//		response.setContentType("text/html");
//
//		// Get the current session, do not create a new one.
//		HttpSession session = request.getSession(false);
//		PrintWriter out = response.getWriter();
//
//		if (session != null && session.getAttribute("username") != null) {
//			String username = (String) session.getAttribute("username");
//			logger.info("Displaying welcome page for user: { }", username);
//			out.println("<!DOCTYPE html>");
//			out.println("<html><head><title>Welcome</title></head><body>");
//			out.println("<h1>Welcome, " + username + "!</h1>");
//			out.println("<a href='logout'>Logout</a>");
//			out.println("</body></html>");
//		}else {
//			logger.warn("Unauthorized access to welcome page.");
//			response.sendRedirect("index_log_in.html");
//		}

		// TODO: what if I want to access the database?
//		try (Connection conn = DBUtil.getConnection()) {
//			Statement stmt = conn.createStatement();
//
//			out.println("<html><body>");
//			out.println("<h1>Hello, World!</h1>");
//			ResultSet rs = stmt.executeQuery("SELECT KAI_CODE, DEP_NAME FROM BMN_MST ");
//			while (rs.next()) {
//				out.println("<h2>" + rs.getString("KAI_CODE") + " " + rs.getString("DEP_NAME") + "</h2>");
//			}
//			out.println("</body></html>");
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//		}
	}

}