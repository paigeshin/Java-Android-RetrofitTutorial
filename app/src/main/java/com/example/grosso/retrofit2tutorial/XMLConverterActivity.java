package com.example.grosso.retrofit2tutorial;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.grosso.retrofit2tutorial.xml_converter.BreakfastMenu;
import com.example.grosso.retrofit2tutorial.xml_converter.Food;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class XMLConverterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmlconverter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        api.getMenu().enqueue(new Callback<BreakfastMenu>() {
            @Override
            public void onResponse(Call<BreakfastMenu> call, Response<BreakfastMenu> response) {
                BreakfastMenu breakfastMenu = response.body();
                for(Food food : breakfastMenu.getFoodList()){
                    Log.d("RetrofitExample", food.getName());
                }
            }

            @Override
            public void onFailure(Call<BreakfastMenu> call, Throwable t) {

            }
        });

    }
}
