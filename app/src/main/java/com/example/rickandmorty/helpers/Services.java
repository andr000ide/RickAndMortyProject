package com.example.rickandmorty.helpers;

import android.content.Context;

import com.android.volley.Response;
import com.example.rickandmorty.ClassesObjDoProjeto.Character;
import com.example.rickandmorty.ClassesObjDoProjeto.CharacterList;
import com.example.rickandmorty.ClassesObjDoProjeto.CharactersInLocation;
import com.example.rickandmorty.ClassesObjDoProjeto.EpisodeList;
import com.example.rickandmorty.ClassesObjDoProjeto.LocationList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class Services {
    private static Services serviceInstance;
    private static Context context;
    private static  Gson gson;

    public Services(Context context){
        this.context=context;
        this.gson =  new GsonBuilder().create();
    }

    public static synchronized Services getInstance(Context context){
        if (serviceInstance== null){
            serviceInstance=new Services(context);
        }
        return serviceInstance;
    }

    //criar igual mas sem o page para procurar em todos os characteres
    public void getCaracters(Response.Listener<CharacterList> sucessoListener, Response.ErrorListener errorListener, String page){
        String endpoint = Endpoints.ENDPOINT + "character/?page=" + page;
        GsonRequest<CharacterList> request = new GsonRequest<>(gson, endpoint,CharacterList.class,sucessoListener,errorListener);
        AppSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getCaractersbyName(Response.Listener<CharacterList> sucessoListener, Response.ErrorListener errorListener, String nome){
        String endpoint = Endpoints.ENDPOINT + "character/?name=" + nome;
        GsonRequest<CharacterList> request = new GsonRequest<>(gson, endpoint,CharacterList.class,sucessoListener,errorListener);
        AppSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getCaractersNamePage(Response.Listener<CharacterList> sucessoListener, Response.ErrorListener errorListener, String page,String filter){
        String endpoint = Endpoints.ENDPOINT + "character/?page=" + page +"&name="+filter;
        GsonRequest<CharacterList> request = new GsonRequest<>(gson, endpoint,CharacterList.class,sucessoListener,errorListener);
        AppSingleton.getInstance(context).addToRequestQueue(request);
    }

    public  void getCharactersInLocation(Response.Listener<CharactersInLocation> sucessoListener, Response.ErrorListener errorListener, String numberLocation){
        String endpoint = Endpoints.ENDPOINT + "location/" + numberLocation;
        GsonRequest<CharactersInLocation> request = new GsonRequest<>(gson,endpoint,CharactersInLocation.class,sucessoListener,errorListener);
        AppSingleton.getInstance(context).addToRequestQueue(request);
    }

    public  void getLocations(Response.Listener<LocationList> sucessoListener, Response.ErrorListener errorListener, String pageLocation){
        String endpoint = Endpoints.ENDPOINT + "location/?page=" + pageLocation;
        GsonRequest<LocationList> request = new GsonRequest<>(gson,endpoint,LocationList.class,sucessoListener,errorListener);
        AppSingleton.getInstance(context).addToRequestQueue(request);
    }

    public  void getLocationsFilter(Response.Listener<LocationList> sucessoListener, Response.ErrorListener errorListener, String nameLocation){
        String endpoint = Endpoints.ENDPOINT + "location/?name=" + nameLocation;
        GsonRequest<LocationList> request = new GsonRequest<>(gson,endpoint,LocationList.class,sucessoListener,errorListener);
        AppSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getMultipleCharacters(Response.Listener<Character[]> sucessListener , Response.ErrorListener errorListener, List<String> ids){
        String endpoint = Endpoints.ENDPOINT + "character/";
        for(int i =0;i<ids.size();i++){
            endpoint=endpoint+(ids.get(i)+",");
        }
        GsonRequest<Character[]> request = new GsonRequest<>(gson,endpoint,Character[].class,sucessListener,errorListener);
        AppSingleton.getInstance(context).addToRequestQueue(request);
    }

    public  void getEpisodes(Response.Listener<EpisodeList> sucessoListener, Response.ErrorListener errorListener, String page){
        String endpoint = Endpoints.ENDPOINT + "episode/?page=" + page;
        GsonRequest<EpisodeList> request = new GsonRequest<>(gson,endpoint,EpisodeList.class,sucessoListener,errorListener);
        AppSingleton.getInstance(context).addToRequestQueue(request);
    }

    public  void getEpisodeFilter(Response.Listener<EpisodeList> sucessoListener, Response.ErrorListener errorListener, String filtro){
        int a =Integer.parseInt(filtro);
        String endpoint;
        if(a<32 && a >0){
            endpoint = Endpoints.ENDPOINT + "episode/?episode=" + a;
        }
        else{
            endpoint = Endpoints.ENDPOINT + "episode/?name=" + filtro;
        }
        GsonRequest<EpisodeList> request = new GsonRequest<>(gson,endpoint,EpisodeList.class,sucessoListener,errorListener);
        AppSingleton.getInstance(context).addToRequestQueue(request);
    }

}
