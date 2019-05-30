package com.example.rickandmorty.helpers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppSingleton {
    private  static AppSingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context context;


    private AppSingleton(Context context){
        this.context=context;

    }

    public static synchronized AppSingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new AppSingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null){
            mRequestQueue= Volley.newRequestQueue(context.getApplicationContext());
            mRequestQueue.start();
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}
