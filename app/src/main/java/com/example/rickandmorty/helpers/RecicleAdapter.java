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


import com.example.rickandmorty.CharacterListFavourite;
import com.example.rickandmorty.ClassesObjDoProjeto.Character;
import com.example.rickandmorty.ClassesObjDoProjeto.CharacterList;
import com.example.rickandmorty.Fragments.Fragment1;
import com.example.rickandmorty.Fragments.Fragment3;
import com.example.rickandmorty.Fragments.Fragment4;
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


public class RecicleAdapter extends RecyclerView.Adapter<RecicleAdapter.MyViewHolder>
{
    protected List<Character> mList;
    protected CharacterList list;
    protected List<Character> mListFixed;
    protected List<Bitmap> mLista;
    protected ImageView a;
    protected Character[] array;
    protected List<Character> listaArray;
    View view;
    Context context;
    String filter;


    public RecicleAdapter(Context context, CharacterList list,String filter){
        this.context=context;
        this.list=list;
        this.mList=list.getResults();
        //this.mList= mList;
        this.mLista=new ArrayList<>();
        this.mListFixed=new ArrayList<>();
        a=new ImageView(context);
        this.filter=filter;

        filtralista();
    }

    public RecicleAdapter(Context context, CharacterList list){
        this.context=context;
        this.list=list;
        this.mList=list.getResults();
        //this.mList= mList;
        this.mLista=new ArrayList<>();
        a=new ImageView(context);
        filter="";
    }

    public RecicleAdapter(Context context, Character[] array,String filter){
        this.context=context;
        this.array=array;
        listaArray=new ArrayList<>();
        for(int i=0;i<array.length;i++){
            listaArray.add(array[i]);
        }
        a=new ImageView(context);
        this.filter=filter;
    }

    public void atualiazaLista(){
        List<Character> auxi = CharacterListFavourite.getInstance(context).getCharacterList();
        for(int i=0;i<auxi.size();i++){
            array[i]=auxi.get(i);
        }
        listaArray=auxi;
    }

    public void filtralista(){
        for(int i =0;i<mList.size();i++){
            if(mList.get(i).name.startsWith(filter)){
                mListFixed.add(mList.get(i));
            }
        }
        mList=mListFixed;
    }


    @NonNull
    @Override
    //inflate the row layout from xml when needed
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recicler_row, viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        if(array!=null){
            atualiazaLista();
            holder.nameCharacter.setText(listaArray.get(i).name);
            holder.nameSpecies.setText(listaArray.get(i).species);
            holder.nameStatus.setText(listaArray.get(i).status);
            switch(array[i].status){
                case "Alive" : holder.nameStatus.setTextColor(Color.parseColor("#3f9b14")); break;
                case "unknown" : holder.nameStatus.setTextColor(Color.parseColor("#FFFF00"));break;
                case "Dead" : holder.nameStatus.setTextColor(Color.parseColor("#FF0000"));break;
                default : break;
            }
            holder.originname.setText(listaArray.get(i).origin.name);
            Picasso.get().load(listaArray.get(i).image).into(holder.imagemCharacter);


            List<Character> auxi = CharacterListFavourite.getInstance(context).getCharacterList();
            holder.imagemFavorito.setBackground(context.getResources().getDrawable(R.drawable.ic_star_border));
            for(int y =0;y<auxi.size();y++){
                if(auxi.get(y).id == listaArray.get(i).id){
                    holder.imagemFavorito.setBackground(context.getResources().getDrawable(R.drawable.ic_star_full));
                }
            }

        }
        else{
            if(filter.equals("")){
                holder.nameCharacter.setText(mList.get(i).name);
                holder.nameSpecies.setText(mList.get(i).species);
                holder.nameStatus.setText(mList.get(i).status);
                switch(mList.get(i).status){
                    case "Alive" : holder.nameStatus.setTextColor(Color.parseColor("#3f9b14")); break;
                    case "unknown" : holder.nameStatus.setTextColor(Color.parseColor("#FFFF00"));break;
                    case "Dead" : holder.nameStatus.setTextColor(Color.parseColor("#FF0000"));break;
                    default : break;
                }
                holder.originname.setText(mList.get(i).origin.name);
                Picasso.get().load(mList.get(i).image).into(holder.imagemCharacter);
            }
            else{
                if(mList.get(i).name.startsWith(filter)){
                    holder.nameCharacter.setText(mList.get(i).name);
                    holder.nameSpecies.setText(mList.get(i).species);
                    holder.nameStatus.setText(mList.get(i).status);
                    holder.originname.setText(mList.get(i).origin.name);
                    //holder.imagemCharacter.s
                    //holder.imagemCharacter.setImageBitmap(mLista.get(i));
                    Picasso.get().load(mList.get(i).image).into(holder.imagemCharacter);
                }
            }

            List<Character> auxi = CharacterListFavourite.getInstance(context).getCharacterList();
            holder.imagemFavorito.setBackground(context.getResources().getDrawable(R.drawable.ic_star_border));
            for(int y =0;y<auxi.size();y++){
                if(auxi.get(y).id == mList.get(i).id){
                    holder.imagemFavorito.setBackground(context.getResources().getDrawable(R.drawable.ic_star_full));
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        if(array!=null){
            return listaArray.size();
        }
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameCharacter;
        private TextView nameSpecies;
        private TextView nameStatus;
        private TextView originname;
        private ImageView imagemFavorito;

        private ImageView imagemCharacter;

        public MyViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            this.nameCharacter  = itemView.findViewById(R.id.nome_personagem);
            this.nameSpecies = itemView.findViewById(R.id.species_personagem);
            this.nameStatus = itemView.findViewById(R.id.status_personagem);
            this.originname = itemView.findViewById(R.id.origin_name);
            this.imagemCharacter = itemView.findViewById(R.id.colocarImagem);
            this.imagemFavorito = itemView.findViewById(R.id.favorito);
            imagemFavorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("edde");

                    if(array!=null){
                        if(imagemFavorito.getBackground().getConstantState() == ( context.getResources().getDrawable(R.drawable.ic_star_full).getConstantState())){
                            imagemFavorito.setBackground(context.getResources().getDrawable(R.drawable.ic_star_border));
                            int aux=0;
                            for(int i =0;i<array.length;i++){
                                if(array[i].name.equals(nameCharacter.getText())){
                                    aux=i;
                                }
                            }
                            CharacterListFavourite.getInstance(context).removeCharacter(array[aux]);
                        }
                        listaArray = CharacterListFavourite.getInstance(context).getCharacterList();
                        notifyDataSetChanged();
                    }
                    else{

                        if(imagemFavorito.getBackground().getConstantState() == ( context.getResources().getDrawable(R.drawable.ic_star_border).getConstantState())){
                            imagemFavorito.setBackground(context.getResources().getDrawable(R.drawable.ic_star_full));
                            int aux=0;
                            for(int i =0;i<mList.size();i++){
                                if(mList.get(i).name.equals(nameCharacter.getText())){
                                    aux=i;
                                }
                            }
                            CharacterListFavourite.getInstance(context).addCharacter(mList.get(aux));
                        }
                        else{
                            imagemFavorito.setBackground(context.getResources().getDrawable(R.drawable.ic_star_border));
                            int aux=0;
                            for(int i =0;i<mList.size();i++){
                                if(mList.get(i).name.equals(nameCharacter.getText())){
                                    aux=i;
                                }
                            }
                            //criar o remove
                            CharacterListFavourite.getInstance(context).removeCharacter(mList.get(aux));
                        }
                    }
                }
            });
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/Calligraphr-Regular.ttf");
            nameCharacter.setTypeface(custom_font);
            nameSpecies.setTypeface(custom_font);
            nameStatus.setTypeface(custom_font);
            originname.setTypeface(custom_font);
        }



        @Override
        public void onClick(View view){
            //hideSoftKeyboard(context,view);
            AppCompatActivity activity = (AppCompatActivity) view.getContext();

            //mandar como parametro a lista dos characteres e o nome do character clickado
            int aux=0;
            for(int i =0;i<mList.size();i++){
                if(mList.get(i).name.equals(nameCharacter.getText())){
                    aux=i;
                }
            }
            Fragment4 fragment = Fragment4.newInstance(mList.get(aux));
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    public String next(){
        return list.info.next;
    }
    public String previous(){
        return list.info.prev;
    }




}



