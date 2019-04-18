/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.User;
import com.google.codeu.utilities.VariableUtil;

/**
 * Redirects the user to the Google login page or their page if they're already logged in.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    HttpSession session = request.getSession();

    //if user tries to login again just redirect them to their pages
    // if they aren't in the database yet add them to the database
    if(session.getAttribute(VariableUtil.LOGGED_IN) != null){
      String email = (String) session.getAttribute(VariableUtil.EMAIL);
      if(!email.equals("")){
        User user = datastore.getUser(email);
        if(user == null){
          datastore.storeUser(user);
        }

        response.sendRedirect("user-page.html?user=" + email);
        return;
      }
    }

    request.setAttribute("error", false);

    String queryParam = request.getQueryString();
    if(queryParam != null && queryParam.length() > 0){
      int errorNumber = Integer.parseInt(request.getParameter("err"));
      request.setAttribute("error", true);

      switch(errorNumber){
        case SignUpServlet.NULL_EMAIL_ERROR:
          request.setAttribute("error_message", "Email cannot be null");
          break;
        case SignUpServlet.NULL_PASSWORD_ERROR:
          request.setAttribute("error_message", "Password cannot be null");
          break;
        case SignUpServlet.INVALID_PASSWORD_ERROR:
          request.setAttribute("error_message", "Invalid password try again");
          break;
        default:
          break;
      }
    }

    request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String email = request.getParameter(VariableUtil.EMAIL);
    String password = request.getParameter(VariableUtil.PASSWORD);


    if(email.equals("")){
      response.sendRedirect("/login?err=1");
      return;
    }else if(password.equals("")){
      response.sendRedirect("/login?err=2");
      return;
    }

    User user = datastore.getUser(email);

    //No an account to this email yet. User must sign up
    if(user == null){
      response.sendRedirect("/signup");
      return;
    }

    //if the password doesn't match whats in the database
    if(!datastore.isPasswordCorrect(user, password)){
      request.getSession().invalidate();
      response.sendRedirect("/login?err=5");
      return;
    }

    request.getSession().setAttribute(VariableUtil.LOGGED_IN, true);
    request.getSession().setAttribute(VariableUtil.EMAIL, email);

    response.sendRedirect("user-page.html?user=" + email);
  }
}
