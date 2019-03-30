package com.google.codeu.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@WebServlet("/home")
public class HomeServlet extends HttpServlet {

  @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

      UserService userService = UserServiceFactory.getUserService();
      boolean isUserLoggedIn = userService.isUserLoggedIn();
      request.setAttribute("isUserLoggedIn", isUserLoggedIn);

      if(isUserLoggedIn)
        request.setAttribute("username", userService.getCurrentUser().getEmail());

      try{
        request.getRequestDispatcher("WEB-INF/jsp/home.jsp").forward(request, response);
      }catch(ServletException e){
        e.printStackTrace();
      }
    }
}
