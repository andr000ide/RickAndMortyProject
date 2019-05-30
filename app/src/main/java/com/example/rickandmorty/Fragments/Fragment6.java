package com.example.rickandmorty.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.rickandmorty.ClassesObjDoProjeto.Character;
import com.example.rickandmorty.ClassesObjDoProjeto.Episode;
import com.example.rickandmorty.R;
import com.example.rickandmorty.helpers.CharactersAtEpisodeAdapter;
import com.example.rickandmorty.helpers.CharactersAtLocationAdapter;
import com.example.rickandmorty.helpers.Services;

import java.util.ArrayList;
import java.util.List;

public class Fragment6 extends Fragment {
    private Episode episode;
    private View rootView;
    private List<String>  idCharacters;


    public static Fragment6 newInstance(Episode a) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Episodio",a);

        Fragment6 fragment = new Fragment6();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            episode = (Episode) bundle.get("Episodio");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment6, container, false);

        idCharacters=new ArrayList<>();
        readBundle(getArguments());

        //LinearLayout mudarTexto = rootView.findViewById(R.id.layout_texto);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/Calligraphr-Regular.ttf");


        TextView ep_name = rootView.findViewById(R.id.episode_name);
        ep_name.setText(episode.name);
        ep_name.setTypeface(custom_font);
        TextView air_date = rootView.findViewById(R.id.air_date);
        air_date.setText(episode.airDate);
        air_date.setTypeface(custom_font);
        TextView nEp = rootView.findViewById(R.id.numberEpisode);
        nEp.setText(episode.episode);
        nEp.setTypeface(custom_font);




        for(int i =0;i<episode.characters.size();i++){
            String parts[] = episode.characters.get(i).split("character/");
            idCharacters.add(parts[1]);
        }
        Services.getInstance(getActivity()).getMultipleCharacters(sucessListener,errorListener,idCharacters);

        return rootView;
    }


    Response.Listener<Character[]> sucessListener = new Response.Listener<Character[]>() {
        @Override
        public void onResponse(Character[] response) {
            for(int i =0 ; i<response.length;i++){
                System.out.println(response[i].toString());
            }

            GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
            gridView.setNestedScrollingEnabled(false);
            CharactersAtEpisodeAdapter adapter = new CharactersAtEpisodeAdapter(getContext(),response);
            gridView.setAdapter(adapter);
            setGridViewHeightBasedOnChildren(gridView, 3);

        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            System.out.println("ERROR");
        }
    };

    //coloca tamanho na gridview ( para o scroll view funcionar)
    public void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = gridView.getAdapter().getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        int verticalSpacing = gridView.getVerticalSpacing() + 4;
        totalHeight = listItem.getMeasuredHeight() + verticalSpacing;

        float x = 1;
        if( items > columns ){
            x = items/columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }



}