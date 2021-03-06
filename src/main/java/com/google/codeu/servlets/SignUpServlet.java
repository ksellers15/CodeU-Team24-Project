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
import com.google.codeu.data.User;
import com.google.codeu.data.User.AccountType;
import com.google.codeu.utilities.VariableUtil;
import com.google.codeu.data.Datastore;



@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

  private Datastore datastore;

  public static final int NULL_EMAIL_ERROR = 1; // email cannot be null
  public static final int NULL_PASSWORD_ERROR = 2; // password cannot be null
  public static final int NULL_ACCCOUNT_TYPE_ERROR = 3; //both account types cannot be selected
  public static final int BOTH_ACCCOUNT_TYPE_ERROR = 4; //at least one account types should be selected
  public static final int INVALID_PASSWORD_ERROR = 5; // used in login servlet

  @Override
  public void init() {
    datastore = new Datastore();
  }

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

    String email = request.getParameter(VariableUtil.EMAIL);
    String password = request.getParameter(VariableUtil.PASSWORD);
    String accountType = request.getParameter(VariableUtil.ACCOUNT_TYPE);



    if(email.equals("")){
      response.sendRedirect("/signup?err=1");
      return;
    }else if(password.equals("")){
      response.sendRedirect("/signup?err=2");
      return;
    }else if(accountType == null){
      response.sendRedirect("/signup?err=3");
      return;
    }

    //Already an account to this email
    if(datastore.getUser(email) != null){
      response.sendRedirect("/login");
      return;
    }

    request.getSession().setAttribute(VariableUtil.LOGGED_IN, true);
    request.getSession().setAttribute(VariableUtil.EMAIL, email);

    AccountType type = null;

    try{
      type = AccountType.valueOf(accountType.toUpperCase());
    }catch(IllegalArgumentException e){
        response.sendRedirect("/signup?err5");
        return;
    }


    datastore.storeUser(new User(email, "", password, type));

    response.sendRedirect("user-page.html?user=" + email);
  }
}
