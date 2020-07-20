package com.pareandroid.note_mvppattern;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notes {

    @Expose
    @SerializedName("id") private int id;
    @Expose
    @SerializedName("tittle") private String tittle;
    @Expose
    @SerializedName("note") private String note;
    @Expose
    @SerializedName("color") private int color;
    @Expose
    @SerializedName("date") private String date;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success =  success;
    }
}
