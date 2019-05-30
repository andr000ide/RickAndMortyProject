package com.example.rickandmorty.ClassesObjDoProjeto;


import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("air_date")
    @Expose
    public String airDate;
    @SerializedName("episode")
    @Expose
    public String episode;
    @SerializedName("characters")
    @Expose
    public List<String> characters = null;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("created")
    @Expose
    public String created;

}