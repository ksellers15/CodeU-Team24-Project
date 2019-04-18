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

package com.google.codeu.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.google.codeu.utilities.VariableUtil;
import com.google.codeu.data.User.AccountType;

/** Provides access to the data stored in Datastore. */
public class Datastore {

  private DatastoreService datastore;

  public Datastore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  /** Stores the Message in Datastore. */
  public void storeMessage(Message message) {
    Entity messageEntity = new Entity(VariableUtil.MESSAGE, message.getId().toString());
    messageEntity.setProperty(VariableUtil.USER, message.getUser());
    messageEntity.setProperty(VariableUtil.MESSAGE_TEXT, message.getText());
    messageEntity.setProperty(VariableUtil.TIMESTAMP, message.getTimestamp());
    messageEntity.setProperty(VariableUtil.RECIPIENT, message.getRecipient());
    if(message.getImageUrl() != null){
      messageEntity.setProperty(VariableUtil.IMAGE_URL, message.getImageUrl());
    }

    datastore.put(messageEntity);
  }

  /**
   * Gets messages posted by a specific user.
   *
   * @return a list of messages posted by the user, or empty list if user has never posted a
   *     message. List is sorted by time descending.
   */
  public List<Message> getMessages(String recipient) {
    List<Message> messages = new ArrayList<>();

    Query query =
        new Query(VariableUtil.MESSAGE)
            .setFilter(new Query.FilterPredicate(VariableUtil.RECIPIENT, FilterOperator.EQUAL, recipient))
            .addSort(VariableUtil.TIMESTAMP, SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        String idString = entity.getKey().getName();
        UUID id = UUID.fromString(idString);
        String user = (String) entity.getProperty(VariableUtil.USER);

        String text = (String) entity.getProperty(VariableUtil.MESSAGE_TEXT);
        long timestamp = (long) entity.getProperty(VariableUtil.TIMESTAMP);

        String image = (String) entity.getProperty(VariableUtil.IMAGE_URL);

        Message message = new Message(id, user, text, timestamp, recipient, image);
        messages.add(message);
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }

    return messages;
  }


  /** Returns the total number of messages for all users. */
  public int getTotalMessageCount(){
    Query query = new Query(VariableUtil.MESSAGE);
    PreparedQuery results = datastore.prepare(query);
    return results.countEntities(FetchOptions.Builder.withLimit(1000));
  }


  /** Stores the User in Datastore. */
 public void storeUser(User user) {
  Entity userEntity = new Entity(VariableUtil.USER, user.getEmail());
  userEntity.setProperty(VariableUtil.EMAIL, user.getEmail());
  userEntity.setProperty(VariableUtil.PASSWORD, user.getPassword());
  userEntity.setProperty(VariableUtil.ABOUT_ME, user.getAboutMe());
  userEntity.setProperty(VariableUtil.ACCOUNT_TYPE, user.getAccountType().toString());
  datastore.put(userEntity);
 }

 /**
  * Returns the User owned by the email address, or
  * null if no matching User was found.
  */
  public User getUser(String email) {

    Query query = new Query(VariableUtil.USER)
      .setFilter(new Query.FilterPredicate(VariableUtil.EMAIL, FilterOperator.EQUAL, email));
    PreparedQuery results = datastore.prepare(query);
    Entity userEntity = results.asSingleEntity();
    if(userEntity == null) {
     return null;
    }

    String aboutMe = (String) userEntity.getProperty(VariableUtil.ABOUT_ME);
    String password = (String) userEntity.getProperty(VariableUtil.PASSWORD);
    String accType = (String) userEntity.getProperty(VariableUtil.ACCOUNT_TYPE);

    AccountType type = null;
    try{
      type = AccountType.valueOf(accType);
    }catch(IllegalArgumentException e){
      return null;
    }catch(NullPointerException e2){
      return null;
    }

    User user = new User(email, aboutMe, password, type);
    return user;
  }

  public boolean isPasswordCorrect(User user, String password){
    return user.getPassword().equals(password);
  }

  public List<Message> getAllMessages() {
    List<Message> messages = new ArrayList<>();

    Query query =
        new Query(VariableUtil.MESSAGE)
            .addSort(VariableUtil.TIMESTAMP, SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        String idString = entity.getKey().getName();
        UUID id = UUID.fromString(idString);
        String text = (String) entity.getProperty(VariableUtil.MESSAGE_TEXT);
        long timestamp = (long) entity.getProperty(VariableUtil.TIMESTAMP);

        String image = (String) entity.getProperty(VariableUtil.IMAGE_URL);

        Message message = new Message(id, (String) entity.getProperty(VariableUtil.USER), text, timestamp, "", image);
        messages.add(message);
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }

    return messages;
  }


}
