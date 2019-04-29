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

/*
This servlet is used to test the login LoginStatusServlet
To test login status, before you log in, navigate to this endpoint
to see if you're logged in.
Then log in. After logging in, navigate to this endpoint again and
check if you're logged in. Then log out again and check again.
Try redirecting back and forth to test login/login status integrity
*/
@WebServlet("/testservlet")
public class TestServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.getWriter().println("Is logged in: " + request.getSession().getAttribute("logged_in") +
    "\nUser email: " + request.getSession().getAttribute("email"));
  }

}
