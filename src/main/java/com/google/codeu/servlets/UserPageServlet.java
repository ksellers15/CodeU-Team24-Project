package com.google.codeu.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.codeu.data.User;
import java.util.List;



@WebServlet("/user/*")
public class UserPageServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

      String requestURI = request.getRequestURI();
      String user = requestURI.substring("/user/".length());//;, requestURI.length());
      response.getWriter().println(user);
      //
      // //if anybody requests just "/user/" then redirect them to their own page if logged in
      // //else redirect them to login/sign in page
      if(user.equals("")){
        UserService userService = UserServiceFactory.getUserService();
        if(userService.isUserLoggedIn()){
          user = userService.getCurrentUser().getEmail();
          response.sendRedirect("/user/" + user);
        }else
          response.sendRedirect("/login");
      }
      //
      User currentUser = datastore.getUser(user);
      if(currentUser != null){
        List<Message> messages = datastore.getMessages(user);
        response.getWriter().println("number of messages: " + messages.size());
        request.setAttribute("user", currentUser);
        request.setAttribute("messages", messages);
      }

        // response.getWriter().println("Hello World");
        // request.setAttribute("username", "test");

      request.getRequestDispatcher("WEB-INF/jsp/user.jsp").forward(request, response);
      return;
    }
}
