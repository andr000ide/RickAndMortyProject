package com.example.rickandmorty.helpers;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rickandmorty.ClassesObjDoProjeto.Character;
import com.example.rickandmorty.ClassesObjDoProjeto.CharacterList;
import com.example.rickandmorty.Fragments.Fragment1;
import com.example.rickandmorty.Fragments.Fragment3;
import com.example.rickandmorty.Fragments.Fragment4;
import com.example.rickandmorty.R;
import com.example.rickandmorty.activities.SecondActivity;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ReciclerAdapterEpisodes extends RecyclerView.Adapter<ReciclerAdapterEpisodes.MyViewHolder>
{

    List<String> list;
    Context context;

    public ReciclerAdapterEpisodes(Context context, List<String> list){
        this.context=context;
        this.list=list;
    }


    @NonNull
    @Override
    //inflate the row layout from xml when needed
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_episodes, viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        String [] parts =list.get(i).split("episode/");
        String aux = parts[1];
        holder.numberepisode.setText("Episode "+aux);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView numberepisode;

        public MyViewHolder(View itemView){
            super(itemView);
            this.numberepisode  = itemView.findViewById(R.id.number_episode);
        }

    }


}



