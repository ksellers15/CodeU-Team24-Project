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
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import com.google.codeu.utilities.*;

/** Handles fetching and saving {@link Message} instances. */
@WebServlet("/messagechart")
public class ChartServlet extends HttpServlet {

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

	List<Message> msgList = datastore.getMessages(null);
	Gson gson = new Gson();
    String json = gson.toJson(msgList);
    response.getWriter().println(json);
	
    //List<Message> messages = datastore.getMessages(user);
    //Gson gson = new Gson();
    //String json = gson.toJson(messages);

    //response.getWriter().println(json);
	
	//response.setContentType("application/json");
    //response.getWriter().println("slowly but surely");
  }
}
