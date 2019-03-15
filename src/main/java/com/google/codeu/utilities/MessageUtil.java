package com.google.codeu.utilities;



/*
This is a Utility class for anything dealing with Messages.
Feel free to add functions here to avoid having
repeated code in the servlets and other pojo files (Plain Old Java Object files) :)
*/
public class MessageUtil {

  public static final String IMAGE_REGEX = "(https?://\\S+\\.(jpg|png|gif)\\b\\S*)";
  public static final String REPLACEMENT = "<img src=\"$1\" />";

  /*
  The constructor here is private because we dont want any other class
  instantiating this class. We do this because this is a Utility class and therefore
  we do not need and object of this class, just the methods
  */
  private MessageUtil(){}


  /*
  This method takes the message entered by the user and replaces image links with
  <img> tags in the html of the page using the regular expression above (regex)
  */
  public static String formatImages(String message){
    return message.replaceAll(IMAGE_REGEX, REPLACEMENT);
  }

}
