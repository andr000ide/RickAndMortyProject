package com.example.rickandmorty.Fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.rickandmorty.ClassesObjDoProjeto.CharacterList;
import com.example.rickandmorty.ClassesObjDoProjeto.EpisodeList;
import com.example.rickandmorty.ClassesObjDoProjeto.LocationList;
import com.example.rickandmorty.R;
import com.example.rickandmorty.ServicoHelperStatic;
import com.example.rickandmorty.helpers.EpisodeAdapter;
import com.example.rickandmorty.helpers.LocationsAdapter;
import com.example.rickandmorty.helpers.RecicleAdapter;
import com.example.rickandmorty.helpers.Services;

import java.util.concurrent.TimeUnit;

import static android.support.v4.content.ContextCompat.getSystemService;


public class Fragment2 extends Fragment {
    RecyclerView recyclerView;
    InputMethodManager imm ;
    EpisodeAdapter adapter;
    private  EditText searchTo;
    private View rootView;
    private ScrollView sv ;
    private Button next,previous;
    public Fragment2(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment2,container,false);

        imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        sv = (ScrollView) rootView.findViewById(R.id.fragmento2);

        //declare buttons
        previous = (Button) rootView.findViewById(R.id.previous);
        next = (Button) rootView.findViewById(R.id.next);

        //teclado começa escondido
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        prepare();

        //press button next
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusup();
                //previous.setEnabled(true);
                String nextpage = adapter.next();
                if(nextpage.equals("")){
                }
                else {
                    String[] parts = nextpage.split("page=");
                    nextpage = parts[1];
                    Services.getInstance(getActivity()).getEpisodes(successListener, errorListener, nextpage);
                    //adapter.notifyDataSetChanged();
                    System.out.println("next");
                }
            }
        });



        //press button previous
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusup();
                next.setEnabled(true);
                String nextpage = adapter.previous();
                if(nextpage.equals("")){  }
                else{
                    String[] parts = nextpage.split("page=");
                    nextpage = parts[1];

                    Services.getInstance(getActivity()).getEpisodes(successListener, errorListener,nextpage);
                    //adapter.notifyDataSetChanged();
                    System.out.println("next");
                }
            }
        });

        searchTo = (EditText) rootView.findViewById(R.id.editText);
        ImageButton ib = (ImageButton) rootView.findViewById(R.id.imagePesquisa);

        //clica na cena da pesquisa e chama o serviço
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusup();
                String aux = searchTo.getText().toString();

                imm.hideSoftInputFromWindow(searchTo.getWindowToken(), 0);
                Services.getInstance(getActivity()).getEpisodeFilter(successListener, errorListener,aux);
            }
        });

        // se o user clicar no enter quando tem a edit text aberta, corre o serviço para procurar pelo nome
        searchTo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    focusup();
                    String aux = searchTo.getText().toString();
                    imm.hideSoftInputFromWindow(searchTo.getWindowToken(), 0);
                    Services.getInstance(getActivity()).getEpisodeFilter(successListener, errorListener,aux);
                }
                return false;
            }
        });

        return rootView;
    }

    //coloca no inicio da pagina / scrollview
    public void focusup(){
        sv.fullScroll(sv.FOCUS_UP);
    }

    public void prepare(){
        recyclerView = rootView.findViewById(R.id.episodeAdapter);
        // comando para ficar smooth a scrollbar da recyclerview
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Services.getInstance(getActivity()).getEpisodes(successListener, errorListener,"1");
    }


    Response.Listener<EpisodeList> successListener = new Response.Listener<EpisodeList>() {
        @Override
        public void onResponse(EpisodeList response) {
            adapter = new EpisodeAdapter(getActivity(),response);
            if(response.info.next != null &&response.info.next.equals("")){
                next.setEnabled(false);
            }
            else next.setEnabled(true);
            if(response.info.prev != null && response.info.prev.equals("")){
                previous.setEnabled(false);
            }
            else previous.setEnabled(true);

            recyclerView.setAdapter(adapter);
        }
    };


    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            System.out.println("ERROR");
        }
    };

    //esconde teclado
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


}
