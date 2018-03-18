package ru.icqparty.moneytracker.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.icqparty.moneytracker.models.Item;

/**
 * Created by icqparty on 17.03.2018.
 */

public interface Api {


    @GET("items/{type}")
    Call<List<Item>> get(@Path("type") String type);


    @POST("items")
    Call<Item> post(@Body Item item);

    @DELETE("items/{id}")
    Call<Item> delete(@Path("type") Integer id);
}
