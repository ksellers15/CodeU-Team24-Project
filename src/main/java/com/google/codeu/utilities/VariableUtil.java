package com.google.codeu.utilities;


import com.google.codeu.data.Message;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.images.Transform;


public class VariableUtil {

  public static final String MESSAGE = "message";
  public static final String USER = "user";
  public static final String EMAIL = "email";
  public static final String PASSWORD = "password";
  public static final String ABOUT_ME = "about_me";
  public static final String MESSAGE_TEXT = "text";
  public static final String TIMESTAMP = "timestamp";
  public static final String RECIPIENT = "recipient";
  public static final String IMAGE_URL = "imageUrl";
  public static final String ACCOUNT_TYPE = "accountType";

  public static final String LOGGED_IN = "loggedIn";


  private VariableUtil(){}


}
