package com.example.delta_appdev_task_3;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class interfaceForAPI {
    private static final String url="https://api.thedogapi.com/v1/";
    private static final String key="68435f07-f6cc-4d33-814d-7b6da093c425";
    public static interfaceForImages inter=null;
    public static interfaceForImages getter(){
        if(inter==null){
            Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
            inter=retrofit.create(interfaceForImages.class);
        }
        return inter;
    }
    public interface interfaceForImages{
        @Headers("x-api-key:"+key)
        @GET("images/search?limit=10&order=Asc")
        Call<List<DogImage>> getInfo(@Query("page") int pageNo);

        @Multipart
        @Headers({"x-api-key:" + key})
        @POST("images/upload")
        Call<img> uploadImage(@Part MultipartBody.Part photo);

        @Headers("x-api-key:"+key)
        @GET("images/{image_id}/analysis")
        Call<List<dogAnalysis>> getAnalysis(@Path("image_id") String id);
    }
}
