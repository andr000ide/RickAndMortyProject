package com.example.rickandmorty;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.rickandmorty.ClassesObjDoProjeto.Character;
import com.example.rickandmorty.ClassesObjDoProjeto.CharacterList;
import com.example.rickandmorty.helpers.AppSingleton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CharacterListFavourite {
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private static CharacterListFavourite listFavourite;
    private static List<Character> chaList;


    private CharacterListFavourite(Context context){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();
        chaList=new ArrayList<>();
    }

    public static synchronized CharacterListFavourite getInstance(Context context){
        if(listFavourite == null){
            listFavourite = new CharacterListFavourite(context);
        }
        return listFavourite;
    }

    public List<Character> getCharacterList() {
        if (chaList == null){
            chaList= new ArrayList();
        }
        return chaList;
    }

    public void SetCharacterList(List<Character> a){
        chaList = a;
    }

    public void addCharacter(Character a){
        System.out.println("adiciona character");
        if(chaList==null) chaList=new ArrayList<>();
        Boolean verificador=false;
        for(int i=0;i<chaList.size();i++){
            if(chaList.get(i).id.equals(a.id)){
                verificador=true;
            }
        }
        if(!verificador){
            chaList.add(a);
                Gson gson = new Gson();
                String json = gson.toJson(getCharacterList());
                mEditor.putString("MyObject", json);
                mEditor.commit();
        }
        System.out.println("adicionou aos favoritos");
    }

    public void removeCharacter(Character a){
        System.out.println("remove character");
        Boolean verificador=false;
        int aux=-1;
        for(int i=0;i<chaList.size();i++){
            if(chaList.get(i).id.equals(a.id)){
                aux=i;
                verificador=true;
            }
        }
        if(verificador && aux != -1){
            chaList.remove(aux);
            Gson gson = new Gson();
            String json = gson.toJson(getCharacterList());
            mEditor.putString("MyObject", json);
            mEditor.commit();
        }
        System.out.println("removeu dos favoritos");
    }


}
