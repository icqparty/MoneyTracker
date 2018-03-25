package ru.icqparty.moneytracker.models;

/**
 * Created by icqparty on 25.03.2018.
 */

import com.google.gson.annotations.SerializedName;

public class AuthResult {
    public String status;
    public int id;
    @SerializedName("auth_token")
    public String token;
}
