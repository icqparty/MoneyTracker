package ru.icqparty.moneytracker.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by icqparty on 27.03.2018.
 */

public class BalanceResult {
    public String status;
    @SerializedName("total_expenses")
    public Integer expense;
    @SerializedName("total_income")
    public Integer income;
}
