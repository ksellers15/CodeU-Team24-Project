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
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.codeu.utilities.*;
import com.google.codeu.data.User;
import com.google.codeu.data.Forum;

/** Handles fetching and saving {@link Message} instances. */
@WebServlet("/forums")
public class ForumServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    List<Forum> forums = datastore.getAllForums();
    response.getWriter().println(forums.size());

    // for (Forum f: forums){
    //   response.getWriter().println(f.toString() +"\n\n");
    // }
  }


  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    HttpSession session = request.getSession();

    if(session == null || session.getAttribute("loggedIn") == null) {
      response.sendRedirect("/login");
      return;
    }

    User owner = datastore.getUser((String) session.getAttribute("email"));

    if(owner == null){
      response.sendRedirect("/login");
      return;
    }


    String text = Jsoup.clean(request.getParameter("text"), Whitelist.basic());
    boolean isPrivate = request.getParameter("isPrivate") != null;

    Forum forum = new Forum(owner, isPrivate, MessageUtil.formatText(MessageUtil.formatImages(text)));

    datastore.storeForum(forum);

    response.sendRedirect("/forums");
  }
}
