package com.google.codeu.utilities;



/*
This is a Utility class for anything dealing with Messages.
Feel free to add functions here to avoid having
repeated code in the servlets and other pojo files (Plain Old Java Object files) :)
*/
public class MessageUtil {

  public static final String IMAGE_REGEX = "(https?://\\S+\\.(jpg|png|gif))";
  public static final String IMAGE_REPLACEMENT = "<img src=\"$1\" />";
  
  /* Text formatting replacement for using Markdown inputs by the user.
   * The following input turn into following outputs:
   * 1) ** TEXT ** -> <b> TEXT </b>
   * 2) __ TEXT __ -> <i> TEXT </i>
   * 3) ~~ TEXT ~~ -> <strike> TEXT </strike>
   * 4) -- TEXT -- -> <u> TEXT </u>
   */
  public static final String BOLD_REGEX = "##(.+)##";
  public static final String BOLD_REPLACEMENT = "<strong> $1 </strong>";
  public static final String ITALIC_REGEX = "__(.+)__";
  public static final String ITALIC_REPLACEMENT = "<i> $1 </i>";
  public static final String STRIKE_REGEX = "~~(.+)~~";
  public static final String STRIKE_REPLACEMENT = "<strike> $1 </strike>";
  public static final String UNDERLINE_REGEX = "--(.+)--";
  public static final String UNDERLINE_REPLACEMENT = "<u> $1 </u>";


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
    return message.replaceAll(IMAGE_REGEX, IMAGE_REPLACEMENT);
  }

  /*
   * This method takes the message entered by the user and replaces markdown with
   * html tags in the html of the page using the regular expressions above (regex)
   */
  public static String formatText(String message) {
	  String bold = message.replaceAll(BOLD_REGEX, BOLD_REPLACEMENT);
	  String italic = bold.replaceAll(ITALIC_REGEX, ITALIC_REPLACEMENT);
	  String strike = italic.replaceAll(STRIKE_REGEX, STRIKE_REPLACEMENT);
	  String underline = strike.replaceAll(UNDERLINE_REGEX, UNDERLINE_REPLACEMENT);
	  return underline;
  }



}
