package com.example.grosso.retrofit2tutorial;


import com.example.grosso.retrofit2tutorial.okhttp.Comment;
import com.example.grosso.retrofit2tutorial.xml_converter.BreakfastMenu;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface Api {

    /*

    나의 주석. - new concept는 @n으로 표현

     */

    //일반적으로 REST API에서 GET은 정보를 가져올 때 쓴다.
    //POST는 정보를 넣을 때 쓴다.

    //@n - 1
    @GET("/posts")
    Call<ResponseBody> getPosts(); //ResponseBody하면 다 긁어온다.

    //@n - 2
    @POST("/users")  //포스트 형식으로 보내면.. 보통 값을 넣는 것이다.
    Call<ResponseBody> postUser(@Body RequestBody requestBody); //근데 requestBody를 파라미터로 넣는 이유가 뭘까? - 보낼 데이터.
    //RequestBody는 Json 형태여야한다. - 더 정확히 말하면, Json형태룰 RequestBody로 만든다.

    //@n - 3
    //Single Parameter
    @GET("/posts")
    Call<ResponseBody> getPostsByUserId(@Query("userId") int userId); //싱글 파라미터일때는 @Query annotation을 쓴다.

    //@n - 4
    //Multiple Parameters with the same name
    @GET("/posts")
    Call<ResponseBody> getPostsByIds(@Query("id") List<Integer> ids); //같은 이름의 여러 개의 파라미터를 보내야할 때는 자료형 '리스트'를 활용하자.
    //예를들어 유저 id가 1, 2, 3인 정보를 가져오고 싶을 때 이렇게 쓸 수 있다.

    //@n - 5
    //Query: null parameter
    @GET("/posts")
    Call<ResponseBody> getPostsByUserIdAndPostId(@Query("userId") String userId, @Query("id") String postId); //*Get을 쓸 때는 Query로 여러 값을 보낼 수 있다 !
    //파라미터에 값대신 null을 보낼 수 있다! null 이면 userId만 간다.

    //@n - 6
    //Use Map  - 여러 파라미터를 사용하고 싶을 때.. (특히, 이름이 다른 파라미터들)
    @GET("/posts")
    Call<ResponseBody> getPostsByParams(@QueryMap Map<String, String> params);



    //@n - 7
    @GET("/posts/{id}")
    Call<ResponseBody> getPostById(@Path("id") int id);

    //@n - 8
    //Different URL from base URL
    @GET("https://api.ipify.org")
    Call<ResponseBody> getIp();

    @GET //Get은 파라미터 안받아도 됨.
    Call<ResponseBody> sendRequest(@Url String url);

    //@n - 9
    @Multipart //파일 업로드할 때, 반드시 명시해줘야한다.
    @POST //포스트할 때도 굳이 파라미터를 안받아도 된다.
    Call<ResponseBody> uploadFile(@Url String url, @Part MultipartBody.Part part);
    //BaseUrl이 있는데 왜 굳이? - 상관없음
    //근데 Multipart를 쓰려면 무조건 url을 적어야하나? 아마도 그런 것 같다.. 왜냐면 BaseUrl에다가 보내는 것이 아니니까.

    //@n - 9
    @Multipart //혹은 FormUrlEncoded
    @POST
    Call<ResponseBody> ocr(@Url String url,
                           @Part("apikey") RequestBody apiKey,
                           @Part("language") RequestBody language,
                           @Part MultipartBody.Part file);
    //여기서 의문점. RequestBody를 서버에서 받으면 어떻게 처리할까?
    //아마도 ocr라는 서비스는 apikey를 받은 상태의 유저에게 파일 업로드를 허용해주는 api인가보다. - 아마도 외부저장소?
    //OCR은 이미지를 텍스트나 PDF로 변환해주는 서비스다.

    //@n - 10
    @FormUrlEncoded
    @POST("/posts")
    Call<ResponseBody> postPost(@Field("id") String id,
                                @Field("userId") String userId,
                                @Field("title") String title,
                                @Field("body") String body);


    //@n - 11 - static header
//    @Headers({"Content-Type: application/json",
//            "User-Agent: RetrofitExample"})//Header정보를 보내기 위해서 Header annotation을 사용한다.
//    @GET("http://httpbin.org/get")
//    Call<ResponseBody> sendRequestWithHeaders();
//    이 코드는 OkHttpActivity에도 쓰인다.
    @Headers({"Content-Type: application/json",
            "User-Agent: RetrofitExample"})
    @GET("http://httpbin.org/get")
    Call<ResponseBody> sendRequestWithHeaders();

    //@n - 12 - dynamic header
    @GET("http://httpbin.org/get")
    Call<ResponseBody> sendRequestWithHeaders(@Header("Content-Type") String contentType, @Header("User-Agent") String userAgent);




    //GsonConverter
    @GET("/comments/{id}")
    Call<Comment> getCommentById(@Path("id") int id);

    //XMLConverter
    @GET("https://www.w3schools.com/xml/simple.xml")
    Call<BreakfastMenu> getMenu();


}
