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
import com.example.rickandmorty.ClassesObjDoProjeto.LocationList;
import com.example.rickandmorty.Fragments.Fragment1;
import com.example.rickandmorty.Fragments.Fragment3;
import com.example.rickandmorty.Fragments.Fragment4;
import com.example.rickandmorty.Fragments.Fragment5;
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


public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.MyViewHolder>
{
    protected LocationList mList;
    Context context;


    public LocationsAdapter(Context context, LocationList list){
        this.context=context;
        this.mList=list;
    }



    @NonNull
    @Override
    //inflate the row layout from xml when needed
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.locations_adapter_row, viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

                holder.nameLocation.setText(mList.results.get(i).name);
                holder.typeLocation.setText(mList.results.get(i).type);
                holder.dimLocation.setText(mList.results.get(i).dimension);
    }

    @Override
    public int getItemCount() {
        return mList.results.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameLocation;
        private TextView typeLocation;
        private TextView dimLocation;

        public MyViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            this.nameLocation  = itemView.findViewById(R.id.nome_location);
            this.typeLocation = itemView.findViewById(R.id.type_location);
            this.dimLocation = itemView.findViewById(R.id.dim_location);
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/Calligraphr-Regular.ttf");

            nameLocation.setTypeface(custom_font);
            typeLocation.setTypeface(custom_font);
            dimLocation.setTypeface(custom_font);
        }
        @Override
        public void onClick(View view){
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            //mandar como parametro a lista dos characteres e o nome do character clickado
            int aux=0;
            for(int i =0;i<mList.results.size();i++){
                if(mList.results.get(i).name.equals(nameLocation.getText())){
                    aux=i;
                }
            }

            List<String> idCharacters = new ArrayList<>();
            for(int i =0;i<mList.results.get(aux).residents.size();i++){
                String parts[] = mList.results.get(aux).residents.get(i).split("character/");
                idCharacters.add(parts[1]);
            }
            Services.getInstance(context).getMultipleCharacters(sucessListener,errorListener,idCharacters);


        }
        Response.Listener<Character[]> sucessListener = new Response.Listener<Character[]>() {
            @Override
            public void onResponse(Character[] response) {
                AppCompatActivity activity = (AppCompatActivity) context;
                Fragment5 fragment = Fragment5.newInstance(response,dimLocation.getText().toString(),nameLocation.getText().toString(),typeLocation.getText().toString());
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR");
            }
        };

    }

    public String next(){
        return mList.info.next;
    }
    public String previous(){
        return mList.info.prev;
    }




}



