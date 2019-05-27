package com.example.grosso.retrofit2tutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();

        Api api = retrofit.create(Api.class);

        try {
            //일단 파일 생성
            File file = new File(getCacheDir(), "hello.txt");
            FileWriter writer = new FileWriter(file);
            writer.append("hello");
            writer.flush();
            writer.close();

            //Multipart 즉 part를 만들어주는 과정 -- 서버에서는 어떻게 처리할까? 의문..
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

            api.uploadFile("https://file.io/", part).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Log.d("RetrofitTag", response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
