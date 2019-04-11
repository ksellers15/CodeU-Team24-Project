package com.google.codeu.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@WebServlet("/home")
public class HomeServlet extends HttpServlet {

  @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

      HttpSession session = request.getSession();
      boolean isUserLoggedIn = false;

      if(session != null && session.getAttribute("logged_in") != null)
        isUserLoggedIn = (boolean) session.getAttribute("logged_in");

      request.setAttribute("isUserLoggedIn", isUserLoggedIn);

      if(isUserLoggedIn)
        request.setAttribute("username", session.getAttribute("email"));

      request.getRequestDispatcher("WEB-INF/jsp/home.jsp").forward(request, response);
    }
}
