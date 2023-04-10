package com.xadmin.usermanagement.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LogoutServ")
public class LogoutServ extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false); // Retrieve current session, do not create a new session if not exist
        if (session != null) {
            session.invalidate(); // Invalidate the session
        }

        // Set cache-control headers to prevent caching
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.sendRedirect("index.jsp"); // Redirect to login page after logout
    }
}
