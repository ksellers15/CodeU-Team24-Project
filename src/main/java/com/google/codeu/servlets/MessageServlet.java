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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.codeu.utilities.*;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import com.google.appengine.api.images.ImagesServiceFailureException;


/** Handles fetching and saving {@link Message} instances. */
@WebServlet("/messages")
public class MessageServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with a JSON representation of {@link Message} data for a specific user. Responds with
   * an empty array if the user is not provided.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("application/json");

    String user = request.getParameter("user");

    if (user == null || user.equals("")) {
      // Request is invalid, return empty array
      response.getWriter().println("[]");
      return;
    }

    List<Message> messages = datastore.getMessages(user);
    Gson gson = new Gson();
    String json = gson.toJson(messages);

    response.getWriter().println(json);

    String targetLanguageCode = request.getParameter("language");

  	if(targetLanguageCode != null) {
  		translateMessages(messages, targetLanguageCode);
  	}
  }

  /** Stores a new {@link Message}. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    HttpSession session = request.getSession();

    if(session.getAttribute(VariableUtil.LOGGED_IN) == null){
      response.sendRedirect("/");
      return;
    }

    String user = (String) session.getAttribute(VariableUtil.EMAIL);
    String text = Jsoup.clean(request.getParameter(VariableUtil.MESSAGE_TEXT), Whitelist.basic());
    String recipient = request.getParameter(VariableUtil.RECIPIENT);
    float sentimentScore = getSentimentScore(text);

    //Message message = new Message(user, MessageUtil.formatImages(text), recipient);
	Message message = new Message(user, MessageUtil.formatImages(text), recipient, sentimentScore);

  try{
    MessageUtil.checkIfImagesUploaded(request, message);
  }catch(ImagesServiceFailureException unused){
    //Do nothing here - this means a message without a image was posted
  }
    datastore.storeMessage(message);

    response.sendRedirect("/user-page.html?user=" + recipient);
  }

  //takes a List of Message instances, iterates over them, and translates their text to the target language
  private void translateMessages(List<Message> messages, String targetLanguageCode) {
	Translate translate = TranslateOptions.getDefaultInstance().getService();

    for(Message message : messages) {
      String originalText = message.getText();

      Translation translation = translate.translate(originalText, TranslateOption.targetLanguage(targetLanguageCode));
      String translatedText = translation.getTranslatedText();

      message.setText(translatedText);
    }
  }

  //a helper function that takes a String value and returns a score
  private float getSentimentScore(String text) throws IOException {
	float score = 0.0f;
	try (LanguageServiceClient language = LanguageServiceClient.create()) {
      // The text to analyze
      Document doc = Document.newBuilder()
          .setContent(text).setType(Type.PLAIN_TEXT).build();

      // Detects the sentiment of the text
      Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();

	  score = sentiment.getScore();
    }catch (Exception e){
		e.printStackTrace(System.err);
	}
	return score;
  }
}
