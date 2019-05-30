package com.example.rickandmorty.ClassesObjDoProjeto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharactersInLocation {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("dimension")
    @Expose
    public String dimension;
    @SerializedName("residents")
    @Expose
    public List<String> residents = null;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("created")
    @Expose
    public String created;
}



