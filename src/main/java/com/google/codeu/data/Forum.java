package com.google.codeu.data;

import java.util.UUID;
import com.google.codeu.utilities.*;

/** A forum created by professors for students to subscribe to */
public class Forum {

  private UUID id;
  private User owner;
  private List<User> subscribers;
  private boolean private;
  private String text;
  private long timestamp;

  public Forum(User owner, boolean private, String text){
    this.Forum(UUID.randomUUID(), owner, priv, text, System.currentTimeMillis());
  }

  private Forum(UUID id, User owner, boolean priv, String text, long timestamp){
    this.id = id;
    this.owner = owner;
    this.private = priv;
    this.text = text;
    this.timestamp = timestamp;
  }

  void addSubscriber(User user){
    this.subscribers.add(user);
  }


}
