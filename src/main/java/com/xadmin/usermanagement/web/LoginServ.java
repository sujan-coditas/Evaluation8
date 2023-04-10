package com.xadmin.usermanagement.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xadmin.usermanagement.Connectivity;

@WebServlet("/LoginServ")
public class LoginServ extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Connection conn = Connectivity.connection();
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        String email = req.getParameter("email");
       // String password = req.getParameter("password");

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String dbEmail = rs.getString("email");
                HttpSession session= req.getSession();
                session.setAttribute("sname",dbEmail);
                //String dbPassword = rs.getString("password");
                if ("admin@gmail.com".equals(email) ) {
                    if(session ==null){
                        resp.sendRedirect("index.jsp");
                    }

                    resp.sendRedirect("UserServlet");
                } else if (email.equals(dbEmail) ) {
                    if(session ==null){
                        resp.sendRedirect("index.jsp");
                    }
                    out.println("<table border='solid'>"
                            + "<th>ID</th>"
                            + "<th>NAME</th>"
                            + "<th>EMAIL</th>"
                            + "<th>COUNTRY</th>"
                            + "<th>IMAGE</th>");

                    out.println("<tr><td>" + rs.getString("id") + "</td>"
                            + "<td>" + rs.getString("name") + "</td>"
                            + "<td>" + rs.getString("email") + "</td>"
                            + "<td>" + rs.getString("country") + "</td>"
                            + "<td>" + rs.getString("image") + "</td></tr>");
                    out.println("</table>");

                    out.println("<a href=index.jsp>Back</a>");
                    out.println("<a href=LogoutServ>Logout</a>");

                } else {
                    RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
                    rd.include(req, resp);
                }
            } else {
                RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
                rd.include(req, resp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
