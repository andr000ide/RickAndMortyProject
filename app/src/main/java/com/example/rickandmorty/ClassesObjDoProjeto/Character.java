package com.example.rickandmorty.ClassesObjDoProjeto;


import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Character implements Serializable {

    public Bitmap bitmap;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("species")
    @Expose
    public String species;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("origin")
    @Expose
    public Origin origin;
    @SerializedName("location")
    @Expose
    public Origin location;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("episode")
    @Expose
    public List<String> episode = null;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("created")
    @Expose
    public String created;


}