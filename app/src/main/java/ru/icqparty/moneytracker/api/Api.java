package ru.icqparty.moneytracker.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.icqparty.moneytracker.models.AddItemResult;
import ru.icqparty.moneytracker.models.AuthResult;
import ru.icqparty.moneytracker.models.BalanceResult;
import ru.icqparty.moneytracker.models.Item;
import ru.icqparty.moneytracker.models.RemoveItemResult;

/**
 * Created by icqparty on 17.03.2018.
 */

public interface Api {
    @GET("auth")
    Call<AuthResult> auth(@Query("social_user_id") String userId);

    @GET("logout")
    Call<AuthResult> logout(@Query("social_user_id") String userId);


    @GET("items")
    Call<List<Item>> getItems(@Query("type") String type);

    @POST("items/add")
    Call<AddItemResult> addItem(@Query("price") Integer price, @Query("name") String name, @Query("type") String type);

    @POST("items/remove")
    Call<RemoveItemResult> remove(@Query("id") Integer id);

    @GET("balance")
    Call<BalanceResult> balance();
}
