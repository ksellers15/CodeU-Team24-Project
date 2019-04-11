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
import com.google.gson.JsonObject;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.lang.Integer;


@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

  private final int NULL_EMAIL_ERROR = 1; // email cannot be null
  private final int NULL_PASSWORD_ERROR = 2; // password cannot be null
  private final int NULL_ACCCOUNT_TYPE_ERROR = 3; //both account types cannot be selected
  private final int BOTH_ACCCOUNT_TYPE_ERROR = 4; //at least one account types should be selected

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    request.setAttribute("error", false);

    String queryParam = request.getQueryString();
    if(queryParam != null && queryParam.length() > 0){
      int errorNumber = Integer.parseInt(request.getParameter("err"));
      request.setAttribute("error", true);

      switch(errorNumber){
        case NULL_EMAIL_ERROR:
          request.setAttribute("error_message", "Email cannot be null");
          break;
        case NULL_PASSWORD_ERROR:
          request.setAttribute("error_message", "Password cannot be null");
          break;
        case NULL_ACCCOUNT_TYPE_ERROR:
          request.setAttribute("error_message", "At least one account type should be checked");
          break;
        case BOTH_ACCCOUNT_TYPE_ERROR:
        request.setAttribute("error_message", "Both account types cannnot be checked");
          break;
        default:
          break;
      }
    }

    request.getRequestDispatcher("WEB-INF/jsp/signup.jsp").forward(request, response);
  }


  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String isStudent[] = request.getParameterValues("account_type");

    if(email.equals("")){
      response.sendRedirect("/signup" + "?err=1");
    }else if(password.equals("")){
      response.sendRedirect("/signup" + "?err=2");
    }else if(isStudent == null){
      response.sendRedirect("/signup" + "?err=3");
    }else if(isStudent.length > 1){
      response.sendRedirect("/signup" + "?err=4");
    }

    request.getSession().setAttribute("logged_in", true);
    request.getSession().setAttribute("email", email);
    request.getSession().setAttribute("username", email);

  }
}
