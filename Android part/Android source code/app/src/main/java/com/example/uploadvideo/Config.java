package com.example.uploadvideo;

public class Config {
    // File upload url (replace the ip with your server address)
    public static final String FILE_UPLOAD_URL = "http://192.168.43.6/AndroidFileUpload/fileUpload.php";

    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";

    public static String condition;

    public static String calculate(boolean X){
     String s=null;
     if (X){
         return "Resting";
     }else{
         return "Exercise";
     }

    }
}