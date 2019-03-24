package com.google.codeu.utilities;



/*
This is a Utility class for anything dealing with Messages.
Feel free to add functions here to avoid having
repeated code in the servlets and other pojo files (Plain Old Java Object files) :)
*/
public class MessageUtil {

  public static final String IMAGE_REGEX = "(https?://\\S+\\.(jpg|png|gif)\\b\\S*)";
  public static final String YOUTUBE_REGEX = "(https?://www.youtube.com/watch\\?v\\=\\S+)";
  public static final String REPLACEMENT = "<img src=\"$1\" />";
  public static final String YOUTUBE_REPLACEMENT = "<iframe width=\"560\" height=\"315\" "
  + "src=\"https://www.youtube.com/embed/%s\" frameborder=\"0\" allow=\"accelerometer; autoplay; "
  + "encrypted-media; gyroscope; picture-in-picture\" allowfullscreen style=\"display: block;\"\"></iframe>";

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
  // https://www.youtube.com/watch?v=xbg8SR0HsgU
  public static String formatImages(String message){
    String textWithImages = message.replaceAll(IMAGE_REGEX, REPLACEMENT);

    if(textWithImages.contains("www.youtube.com/watch?v=")){
      String videoID = "";
      int indexOfYoutube = textWithImages.indexOf("?v=") + 3;
      if(!textWithImages.substring(indexOfYoutube).contains(" "))
        videoID = textWithImages.substring(indexOfYoutube);
      else{
        int indexOfEndOfID = textWithImages.substring(indexOfYoutube).indexOf(" ");
        videoID = textWithImages.substring(indexOfYoutube, indexOfEndOfID+indexOfYoutube);
      }

      String youtubeReplacement = String.format(YOUTUBE_REPLACEMENT, videoID);
      return textWithImages.replaceAll(YOUTUBE_REGEX, youtubeReplacement);
    }

    return textWithImages;
  }
}
