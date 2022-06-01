package com.etheur.retrofitexample;

import com.etheur.retrofitexample.constants.Constants;
import com.etheur.retrofitexample.data.models.Comment;
import com.etheur.retrofitexample.data.models.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolder {

    @GET(Constants.POSTS)
    Call<List<Post>> getPosts(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order);

    @GET(Constants.POSTS)
    Call<List<Post>> getPosts(
            @QueryMap Map<String, String> parameters
    );

    @GET(Constants.COMMENTS)
    Call<List<Comment>> getComments(@Path("id") int postId);

    @GET
    Call<List<Comment>> getComments(@Url String url);

    @POST(Constants.POSTS)
    Call<Post> createPost(@Body Post post);

    @FormUrlEncoded
    @POST(Constants.POSTS)
    Call<Post> createPost(@Field("userdId") int userId,
                          @Field("title") String title,
                          @Field("body") String text);

    @FormUrlEncoded
    @POST(Constants.POSTS)
    Call<Post> createPost(@FieldMap Map<String, String> fields);

    @Headers({"Static-Header1: 123, Static-Header2: 456"}) // Estos headers son estaticos, no pueden cambiar
    @PUT(Constants.POST_BY_ID) // Put reemplaza todo el valor existente
    Call<Post> putPost(@Header("Dynamic-Header") String header,
                       @Path("id") int id,
                       @Body Post post);

    @PATCH(Constants.POST_BY_ID) // Patch solo actualiza el dato que nosotros enviamos
    Call<Post> patchPost(@HeaderMap Map<String, String> headers,
                         @Path("id") int id,
                         @Body Post post);

    @DELETE(Constants.POST_BY_ID)
    Call<Void> deletePost(@Path("id") int id);
}
