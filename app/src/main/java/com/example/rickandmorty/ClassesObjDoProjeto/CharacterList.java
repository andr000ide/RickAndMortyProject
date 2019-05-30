package com.example.rickandmorty.ClassesObjDoProjeto;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CharacterList {

    @SerializedName("info")
    @Expose
    public Info info;
    @SerializedName("results")
    @Expose
    public List<Character> results = null;

    public Info getInfo() {
        return info;
    }


    public List<Character> getResults() {
        return results;
    }


}