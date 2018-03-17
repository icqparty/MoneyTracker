package ru.icqparty.moneytracker;

/**
 * Created by icqparty on 17.03.2018.
 */

public class Item {

    private String name;
    private Integer value;
    private String comment;


    public Item(String name, Integer value, String comment) {
        this.name = name;
        this.value = value;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
