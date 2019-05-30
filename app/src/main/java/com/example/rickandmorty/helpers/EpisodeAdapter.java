package com.example.rickandmorty.helpers;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
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


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.rickandmorty.ClassesObjDoProjeto.Character;
import com.example.rickandmorty.ClassesObjDoProjeto.CharacterList;
import com.example.rickandmorty.ClassesObjDoProjeto.EpisodeList;
import com.example.rickandmorty.ClassesObjDoProjeto.LocationList;
import com.example.rickandmorty.Fragments.Fragment1;
import com.example.rickandmorty.Fragments.Fragment3;
import com.example.rickandmorty.Fragments.Fragment4;
import com.example.rickandmorty.Fragments.Fragment5;
import com.example.rickandmorty.Fragments.Fragment6;
import com.example.rickandmorty.R;
import com.example.rickandmorty.activities.SecondActivity;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.MyViewHolder> {
    protected EpisodeList mList;
    Context context;


    public EpisodeAdapter(Context context, EpisodeList list) {
        this.context = context;
        this.mList = list;
    }

    @NonNull
    @Override
    //inflate the row layout from xml when needed
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.episodes_adapter_row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.airdate.setText(mList.results.get(i).airDate);
        holder.episodeName.setText(mList.results.get(i).name);
        holder.season_episode.setText(mList.results.get(i).episode);
    }

    @Override
    public int getItemCount() {
        return mList.results.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView airdate;
        private TextView episodeName;
        private TextView season_episode;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.airdate = itemView.findViewById(R.id.air_date);
            this.episodeName = itemView.findViewById(R.id.name_episode);
            this.season_episode = itemView.findViewById(R.id.season_episode);
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/Calligraphr-Regular.ttf");

            airdate.setTypeface(custom_font);
            episodeName.setTypeface(custom_font);
            season_episode.setTypeface(custom_font);
        }

        @Override
        public void onClick(View view) {


            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            //mandar como parametro a lista dos characteres e o nome do character clickado
            int aux=0;
            for(int i =0;i<mList.results.size();i++){
                if(mList.results.get(i).name.equals(episodeName.getText())){
                    aux=i;
                }
            }


            Fragment6 fragment = Fragment6.newInstance(mList.results.get(aux));
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }


        }


        public String next() {
            return mList.info.next;
        }

        public String previous() {
            return mList.info.prev;
        }


}



