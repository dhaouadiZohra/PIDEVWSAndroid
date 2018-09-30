package com.example.zahra.pidevwsandroid.remote;


public class APIUtils {
    private APIUtils(){};

    public static final String API_URL="http://d64582eb.ngrok.io/pidevjee-web/rest/comments/";
    public static CommentService getCommentService(){
        return RetrofitClient.getClient(API_URL).create(CommentService.class);
    }

}
