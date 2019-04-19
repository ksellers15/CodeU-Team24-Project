package com.google.codeu.data;

import java.util.UUID;
import java.util.List;
import com.google.codeu.data.User;
import com.google.codeu.utilities.*;

/** A forum created by professors for students to subscribe to */
public class Forum {

  private UUID id;
  private User owner;
  private List<User> subscribers;
  private boolean isPrivate;
  private String text;
  private long timestamp;

  public Forum(User owner, boolean isPrivate, String text) {
    this(UUID.randomUUID(), owner, isPrivate, text, System.currentTimeMillis());
  }

  public Forum(UUID id, User owner, boolean priv, String text, long timestamp) {
    this.id = id;
    this.owner = owner;
    this.isPrivate = priv;
    this.text = text;
    this.timestamp = timestamp;
  }

  void addSubscriber(User user) {
    this.subscribers.add(user);
  }

  public UUID getId(){
    return id;
  }
  
  public User getOwner(){
    return this.owner;
  }

  public String getText(){
    return this.text;
  }

  public long getTimestamp(){
    return this.timestamp;
  }

  public boolean isPrivate(){
    return this.isPrivate;
  }

  public String toString(){
    return "Owner: " + owner.getEmail() + "\n Text: " + text + "\nPrivate: " + isPrivate
    + "\nID: " + id + "\n timestamp: " + timestamp;

  }

}
