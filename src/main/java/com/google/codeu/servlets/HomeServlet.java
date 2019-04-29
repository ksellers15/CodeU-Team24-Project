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
import com.google.codeu.utilities.VariableUtil;



@WebServlet("/")
public class HomeServlet extends HttpServlet {

  @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

      HttpSession session = request.getSession();
      boolean isUserLoggedIn = false;

      if(session != null && session.getAttribute(VariableUtil.LOGGED_IN) != null)
        isUserLoggedIn = (boolean) session.getAttribute(VariableUtil.LOGGED_IN);

      request.setAttribute(VariableUtil.LOGGED_IN, isUserLoggedIn);

      if(isUserLoggedIn)
        request.setAttribute(VariableUtil.EMAIL, session.getAttribute(VariableUtil.EMAIL));

      request.getRequestDispatcher("WEB-INF/jsp/home.jsp").forward(request, response);
    }
}
