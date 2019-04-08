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

/*
This is a Utility class for anything dealing with Messages.
Feel free to add functions here to avoid having
repeated code in the servlets and other pojo files (Plain Old Java Object files) :)
*/
public class MessageUtil {

  /* Text formatting replacement for using Markdown inputs by the user.
   * The following input turn into following outputs:
   * 1) ## TEXT ## -> <b> TEXT </b>
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
  This method also takes youtube links and replaces them with embedded youtube tags
  */
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

  /*
  Checks if any images were uploaded via blobstore which saves images on the server
  then the string of the image is set to the message
  */
  public static void checkIfImagesUploaded(HttpServletRequest req, Message mes){
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
    List<BlobKey> blobKeys = blobs.get("image");

    //Check to see if any images were uploaded
    if(blobKeys != null && !blobKeys.isEmpty()){
      BlobKey blobKey = blobKeys.get(0);
      ImagesService imagesService = ImagesServiceFactory.getImagesService();
      ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
      String imageUrl = imagesService.getServingUrl(options);
      mes.setImageUrl(imageUrl);
    }
  }

   /*
   This method takes the message entered by the user and replaces markdown with
   html tags in the html of the page using the regular expressions above (regex)
   */
  public static String formatText(String message) {
	  return message.replaceAll(BOLD_REGEX, BOLD_REPLACEMENT).replaceAll(ITALIC_REGEX, ITALIC_REPLACEMENT).replaceAll(STRIKE_REGEX, STRIKE_REPLACEMENT).replaceAll(UNDERLINE_REGEX, UNDERLINE_REPLACEMENT);
  }
}
