package com.example.rickandmorty.ClassesObjDoProjeto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Origin {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("url")
    @Expose
    public String url;


}