package ru.icqparty.moneytracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by icqparty on 17.03.2018.
 */

public class Item implements Parcelable {

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

    protected Item(Parcel in) {
        type = in.readString();
        name = in.readString();
        value = in.readString();
        date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeInt(null);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(value);
        dest.writeString(date);
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
