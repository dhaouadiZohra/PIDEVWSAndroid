package com.example.zahra.pidevwsandroid.remote;

import com.example.zahra.pidevwsandroid.model.Comment;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface CommentService {

    @GET("getc/")
    Call<List<Comment>> getComments();

    @POST("addc/")
    Call<Comment> addComment(@Body Comment comment);

    @PUT("editc/")
    Call<Comment> updateComment(@Body Comment comment);

    @DELETE("delete/{id}")
    Call<Comment> deleteComment(@Path("id") Integer id);
}
