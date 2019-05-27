package com.example.grosso.retrofit2tutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.grosso.retrofit2tutorial.okhttp.Comment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JsonConverterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_converter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        //모델을 활용하려면 반드시 GsonConverterFactory를 추가해야한다.
        api.getCommentById(1).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Log.d("Retrofit2 Example", response.body().getEmail());
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });

    }
}
