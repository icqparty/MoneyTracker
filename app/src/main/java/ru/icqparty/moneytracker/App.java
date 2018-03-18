package ru.icqparty.moneytracker;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.icqparty.moneytracker.api.Api;

/**
 * Created by icqparty on 17.03.2018.
 */

public class App extends Application {

    private static final String BASE_URL = "http://twilight-fog-3285.getsandbox.com/";

    private Api api;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("dd.MM.yyyy HH:mm:ss")
                .create();

        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
                .create(Api.class);

    }

    public Api getApi() {
        return api;
    }
}
