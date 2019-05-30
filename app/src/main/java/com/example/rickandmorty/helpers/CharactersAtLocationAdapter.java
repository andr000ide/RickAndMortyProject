package com.example.rickandmorty.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rickandmorty.ClassesObjDoProjeto.Character;
import com.example.rickandmorty.Fragments.Fragment4;
import com.example.rickandmorty.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class CharactersAtLocationAdapter extends BaseAdapter {

    private final Context mContext;
    private final Character[] pers;

    // 1
    public CharactersAtLocationAdapter(Context context, Character[] personagens) {
        this.mContext = context;
        this.pers = personagens;
    }

    // 2
    @Override
    public int getCount() {
        return pers.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final Character personagem = pers[position];


        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.grid_fotos, null);
        }

        // 3
        final ImageView imageChar = (ImageView)convertView.findViewById(R.id.imageview_character);
        final TextView nameChar = (TextView)convertView.findViewById(R.id.textview_character_name);


        // 4
        //imageChar.setImageResource(personagem.image);
        nameChar.setText(personagem.name);



        if(pers[position].bitmap==null){
            //new DownloadImage((ImageView) imageChar,position).execute(pers[position].image);

        }
        else { //imageChar.setImageBitmap(pers[position].bitmap);
             }

        Picasso.get().load(pers[position].image).into(imageChar);


        imageChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                System.out.println(nameChar.getText());
                Fragment4 fragment = Fragment4.newInstance(personagem);
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return convertView;
    }


}
