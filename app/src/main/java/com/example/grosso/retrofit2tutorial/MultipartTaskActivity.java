package com.example.grosso.retrofit2tutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MultipartTaskActivity extends AppCompatActivity {

    final String OCR_URL = "https://api.ocr.space/parse/image/";
    final String API_KEY = "4e2bff836988957";
    final String LANGUAGE = "eng";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multipart_task);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();

        Api api = retrofit.create(Api.class);

        try {

            InputStream input = getAssets().open("image2.jpg");
            File file = new File(getCacheDir(), "image2.jpg");

            try {
                OutputStream output = new FileOutputStream(file);
                try {
                    try {
                        byte[] buffer = new byte[1024];
                        int read;

                        while ((read = input.read(buffer)) != -1) {
                            output.write(buffer, 0, read);
                        }
                        output.flush();
                    } finally {
                        output.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                input.close();
            }

            RequestBody apiKey = RequestBody.create(MediaType.parse("multipart/form-data"), API_KEY);

            RequestBody language = RequestBody.create(MediaType.parse("multipart/form-data"), LANGUAGE);

            final RequestBody requestBodyWithFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestBodyWithFile);

            api.ocr(OCR_URL, apiKey, language, filePart).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Log.d("RetrofitExample", response.body().string());
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



//    Gson gson = new GsonBuilder()
//            .setLenient()
//            .create();
//
//    Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("https://jsonplaceholder.typicode.com/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build();
//
//    Api api = retrofit.create(Api.class);
//
////이미지 파일을 가져옴
//        try {
//                //읽기
//                InputStream input = getAssets().open("image2.jpg");
//        File file = new File(getCacheDir(), "image2.jpg");
//        Log.d("이미지 파일 잘들어옴?", file.toString());
//        //It takes image from 'Asset' Folder and copy it to 'Cache Directory' in order to create an instance of a file
//
//        //쓰기
//        OutputStream output = new FileOutputStream(file);
//
//        byte[] buffer = new byte[1024]; //아마도 이미지 파일을 읽을 때는 byte 어레이를 사용하나보다. byte[1024]를 기억하자.
//        int read; //이미지는 정수(int)값이므로.
//
//        while((read = input.read(buffer)) != -1){
//        output.write(buffer, 0, read);
//        }
//        output.flush();
//
//        RequestBody apiKey = RequestBody.create(MediaType.parse("multipart/form-data"), API_KEY);
//        RequestBody requestBodyWithFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        RequestBody language = RequestBody.create(MediaType.parse("multipart/form-data"), LANGUAGE);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBodyWithFile); //특별하게 파일만 part로 바꿔주면 된다!
//
//        api.ocr(OCR_URL, apiKey, language ,part).enqueue(new Callback<ResponseBody>() {
//        @Override
//        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//        try {
//        Log.d("RetrofitTag", response.body().string());
//        } catch (IOException e) {
//        e.printStackTrace();
//        }
//        }
//
//        @Override
//        public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//        }
//        });
//
//        } catch (IOException e) {
//        e.printStackTrace();
//        }
