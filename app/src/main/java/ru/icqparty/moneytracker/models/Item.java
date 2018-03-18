package ru.icqparty.moneytracker.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by icqparty on 17.03.2018.
 */

public class Item {

    public static final String TYPE_UNKNOWN = "unknown";
    public static final String TYPE_INCOMES = "incomes";
    public static final String TYPE_EXPENSES = "expenses";
    public static final String TYPE_BALANCE = "balance";

    public Integer id;
    public String type;
    public String name;
    @SerializedName("price")
    public String value;
    public String date;


    public Item(String type, String name, String value, String date) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.date = date;
    }
}
