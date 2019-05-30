package com.example.rickandmorty.Fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.rickandmorty.CharacterListFavourite;
import com.example.rickandmorty.ClassesObjDoProjeto.Character;
import com.example.rickandmorty.ClassesObjDoProjeto.CharactersInLocation;
import com.example.rickandmorty.FragsInterface;
import com.example.rickandmorty.R;
import com.example.rickandmorty.helpers.RecicleAdapter;
import com.example.rickandmorty.helpers.ReciclerAdapterEpisodes;
import com.example.rickandmorty.helpers.Services;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Fragment4 extends Fragment {
    private ReciclerAdapterEpisodes adapter;
    private Character personagem;
    private RecyclerView recyclerView;
    private View rootView;
    private List<Character> listaChar = new ArrayList<>();

    private String nameLoc,nameType,nameDim;
    private int age;

    private TextView mNameTextView;
    private TextView mAgeTextView;

    FragsInterface delegate = null;
    public static Fragment4 newInstance(Character a) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Personagem", a);

        Fragment4 fragment = new Fragment4();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            personagem = (Character) bundle.get("Personagem");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment4, container, false);




        RelativeLayout lastlocation = rootView.findViewById(R.id.lastlocation);
        RelativeLayout origintolastlocation = rootView.findViewById(R.id.originforLastlocation);

        ImageView imageview = rootView.findViewById(R.id.colocarImagem);
        final ImageView favoritos = rootView.findViewById(R.id.favorito);
        TextView episodios = rootView.findViewById(R.id.episodios);
        TextView nomePers = rootView.findViewById(R.id.nomeperso);
        TextView status = rootView.findViewById(R.id.status);
        TextView species = rootView.findViewById(R.id.species);
        TextView gender = rootView.findViewById(R.id.gender);
        TextView origin = rootView.findViewById(R.id.origin);
        TextView location = rootView.findViewById(R.id.location);
        readBundle(getArguments());
        nomePers.setText(personagem.name);

        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/Calligraphr-Regular.ttf");

        episodios.setTypeface(custom_font);

        Picasso.get().load(personagem.image).into(imageview);
        //imageview.setImageBitmap(personagem.bitmap);
        status.setText(personagem.status);
        species.setText(personagem.species);
        gender.setText(personagem.gender);
        origin.setText(personagem.origin.name);
        location.setText(personagem.location.name);


        listaChar = CharacterListFavourite.getInstance(getContext()).getCharacterList();
        for(int i=0;i<listaChar.size();i++){
            if (personagem.id == listaChar.get(i).id){
                favoritos.setBackground(getContext().getResources().getDrawable(R.drawable.ic_star_full));
            }
        }


        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favoritos.getBackground().getConstantState() == ( getContext().getResources().getDrawable(R.drawable.ic_star_full).getConstantState())){
                    favoritos.setBackground(getContext().getResources().getDrawable(R.drawable.ic_star_border));
                    int aux=0;
                    for(int i =0;i<listaChar.size();i++){
                        if(listaChar.get(i).id==personagem.id){
                            aux=i;
                        }
                    }
                    CharacterListFavourite.getInstance(getContext()).removeCharacter(listaChar.get(aux));
                }
                else{
                    favoritos.setBackground(getContext().getResources().getDrawable(R.drawable.ic_star_full));
                    CharacterListFavourite.getInstance(getContext()).addCharacter(personagem);
                }
            }
        });


//onclicklistener para localização

        lastlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoLoc();
            }
        });
        origintolastlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoLoc();
            }
        });


        prepare();
        return rootView;
    }

    public void infoLoc(){
        String [] parts = personagem.location.url.split("location/");
        String loc = parts[1];
        Services.getInstance(getActivity()).getCharactersInLocation(successListener, errorListener,loc);
    }

    public void prepare(){
        recyclerView = rootView.findViewById(R.id.reciclerAdapterEpisodes);
        // comando para ficar smooth a scrollbar da recyclerview
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ReciclerAdapterEpisodes(getContext(),personagem.episode);
        recyclerView.setAdapter(adapter);
    }

    //recebe resposta sobre os characteres que estão numa localizacao
    // e dps de os filtrar para uma lista de string chama outro serviço para ir buscar a info dos characters
    Response.Listener<CharactersInLocation> successListener = new Response.Listener<CharactersInLocation>() {
        @Override
        public void onResponse(CharactersInLocation response) {
            List<String> charactersNames;
            List<String> idCharacters = new ArrayList<>();
            charactersNames = response.residents;
            nameLoc = response.name;
            nameDim = response.dimension;
            nameType = response.type;
            for(int i =0;i<charactersNames.size();i++){
                String parts[] = charactersNames.get(i).split("character/");
                idCharacters.add(parts[1]);
            }
            Services.getInstance(getActivity()).getMultipleCharacters(sucessListener,errorListener,idCharacters);
        }
    };


    Response.Listener<Character[]> sucessListener = new Response.Listener<Character[]>() {
        @Override
        public void onResponse(Character[] response) {
            for(int i =0 ; i<response.length;i++){
                System.out.println(response[i].toString());
            }

            AppCompatActivity activity = (AppCompatActivity) rootView.getContext();
            Fragment5 fragment = Fragment5.newInstance(response,nameDim,nameLoc,nameType);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        Fragment f = fragments.size() == 3 ? fragments.get(1) : fragments.get(0);
        if (f instanceof Fragment1) {
            delegate = (Fragment1) f;
            delegate.notifyBackPressed();
        }
    }
}


