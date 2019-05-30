package com.example.rickandmorty.ClassesObjDoProjeto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("pages")
    @Expose
    public Integer pages;
    @SerializedName("next")
    @Expose
    public String next;
    @SerializedName("prev")
    @Expose
    public String prev;


}