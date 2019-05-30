package com.example.rickandmorty.ClassesObjDoProjeto;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EpisodeList {

    @SerializedName("info")
    @Expose
    public Info info;
    @SerializedName("results")
    @Expose
    public List<Episode> results = null;

}