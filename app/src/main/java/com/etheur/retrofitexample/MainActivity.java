package com.etheur.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.etheur.retrofitexample.constants.Constants;
import com.etheur.retrofitexample.data.models.Comment;
import com.etheur.retrofitexample.data.models.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * This class is an example of how to implement simple request with Retrofit
 *
 * @param
 * */

public class MainActivity extends AppCompatActivity {

    private TextView tvViewResult;
    private JsonPlaceHolder jsonPlaceHolder;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvViewResult = findViewById(R.id.text_view_result);

        gson = new GsonBuilder().serializeNulls().create();

        Retrofit requestInfo = requestUserInfo();

        jsonPlaceHolder = requestInfo.create(JsonPlaceHolder.class);

        //getPosts();
        //getComments();
        //createPost();
        updatePost();
        //deletePost();
    }


    private Retrofit requestUserInfo(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private void getPosts(){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");
        //Call<List<Post>> call = jsonPlaceHolder.getPosts(new Integer[]{1,2}, "id", "desc");
        Call<List<Post>> call = jsonPlaceHolder.getPosts(parameters);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    tvViewResult.setText("Code: " + response.code());
                    // handle error
                    return;
                }

                List<Post> posts = response.body();

                for(Post post : posts){
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Response: " + post.getBody() + "\n\n\"";
                    tvViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tvViewResult.setText("Algo salio mal con la petición: " + t);
                // handle error.
            }
        });
    }

    private void getComments(){
        //Call<List<Comment>> commentCall = jsonPlaceHolder.getComments(3);
        Call<List<Comment>> commentCall = jsonPlaceHolder.getComments("posts/3/comments");
        commentCall.enqueue(
                new Callback<List<Comment>>() {
                    @Override
                    public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                        if (!response.isSuccessful()){
                            tvViewResult.setText("Code: " + response.code());
                            // handle error
                            return;
                        }

                        List<Comment> comments = response.body();

                        for(Comment comment : comments){
                            String content = "";
                            content += "ID: " + comment.getId() + "\n";
                            content += "Post ID: " + comment.getPostId() + "\n";
                            content += "Name: " + comment.getName() + "\n";
                            content += "Email: " + comment.getEmail() + "\n";
                            content += "Text: " + comment.getText() + "\n\n";
                            tvViewResult.append(content);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Comment>> call, Throwable t) {
                        tvViewResult.setText("Algo salio mal con la petición: " + t);
                        // handle error.
                    }
                }
        );
    }

    private void createPost(){
        Post post = new Post(23, "New Title", "New Text");
        Map<String, String> fields = new HashMap<>();
        fields.put("userdId", "23");
        fields.put("title", "New Title");

        //Call<Post> callPost = jsonPlaceHolder.createPost(post);
        //Call<Post> callPost = jsonPlaceHolder.createPost(101, "Alejandro", "Hola Alejandro bienvenido");
        Call<Post> callPost = jsonPlaceHolder.createPost(fields);

        callPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    tvViewResult.setText("Code: " + response.code());
                    // handle error UI
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Response: " + postResponse.getBody() + "\n\n\"";
                tvViewResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tvViewResult.setText("Algo salio mal con la petición, intentenlo de nuevo");
                // handle error UI
            }
        });
    }

    private void updatePost(){
        Post post = new Post(12, null, "Next Text");
        Map<String, String> headers = new HashMap<>();
        headers.put("Map-Header1", "abc");
        headers.put("Map-Header2", "def");

        //Call<Post> call = jsonPlaceHolder.putPost("abc",5, post);
        Call<Post> call = jsonPlaceHolder.patchPost(headers ,5, post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    tvViewResult.setText("Code: " + response.code());
                    // handle error UI
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Response: " + postResponse.getBody() + "\n\n\"";
                tvViewResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tvViewResult.setText("Algo salio mal con la petición, intentenlo de nuevo");
                // handle error UI
            }
        });
    }

    private void deletePost(){
        Call<Void> call = jsonPlaceHolder.deletePost(5);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                tvViewResult.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                tvViewResult.setText("Algo salio mal, intente de  nuevo");
                // handle error UI
            }
        });
    }
}