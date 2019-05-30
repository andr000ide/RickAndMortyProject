package com.example.rickandmorty.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.rickandmorty.ClassesObjDoProjeto.Character;
import com.example.rickandmorty.ClassesObjDoProjeto.CharacterList;
import com.example.rickandmorty.FragsInterface;
import com.example.rickandmorty.R;
import com.example.rickandmorty.activities.SecondActivity;
import com.example.rickandmorty.helpers.RecicleAdapter;
import com.example.rickandmorty.helpers.Services;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.getSystemService;

public class Fragment1 extends Fragment implements FragsInterface {
    RecyclerView recyclerView;
    InputMethodManager imm;
    RecicleAdapter adapter;
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            System.out.println("ERROR");
        }
    };
    private EditText searchTo;
    private View rootView;
    private ScrollView sv;
    private String page;
    private String filter;
    private ArrayList<Integer> array = new ArrayList<>();
    private Button next, previous, search;
    Response.Listener<CharacterList> successListener = new Response.Listener<CharacterList>() {
        @Override
        public void onResponse(CharacterList response) {

            if (getActivity() != null) {
                adapter = new RecicleAdapter(getActivity(), response);
                if (response.info.next.equals("")) {
                    next.setEnabled(false);
                } else next.setEnabled(true);
                if (response.info.prev.equals("")) {
                    previous.setEnabled(false);
                } else previous.setEnabled(true);

                recyclerView.setAdapter(adapter);
                ((SecondActivity) getActivity()).updateFragment(response.info.pages, filter);
                //System.out.println(response.toString());
            }

        }
    };
    Response.Listener<Character[]> sucessListener = new Response.Listener<Character[]>() {
        @Override
        public void onResponse(Character[] response) {

            if (getActivity() != null) {
                ArrayList<Character> aux = new ArrayList<>();
                for (int i = 0; i < response.length; i++) {
                    aux.add(response[i]);
                }
                adapter = new RecicleAdapter(getActivity(), response, filter);
                next.setEnabled(false);
                previous.setEnabled(false);

                recyclerView.setAdapter(adapter);
                //System.out.println(response.toString());
            }

        }
    };

    public static Fragment1 newInstance(String page) {
        Bundle bundle = new Bundle();
        bundle.putString("pagina", page);

        Fragment1 fragment = new Fragment1();
        fragment.setArguments(bundle);

        return fragment;
    }

    public void setArray(ArrayList<Integer> array) {
        this.array = array;
    }

    public void setPage(String pagina) {
        this.page = pagina;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            this.page = (String) bundle.get("pagina");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment1, container, false);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        readBundle(getArguments());

        searchTo = (EditText) rootView.findViewById(R.id.editText);
        searchTo.setText(filter);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        sv = (ScrollView) rootView.findViewById(R.id.fragmento1);


        //NestedScrollView teste3 = rootView.findViewById(R.id.teste3);
        //teste3.setOnClickListener(click);

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //press button next
        previous = (Button) rootView.findViewById(R.id.previous);
        next = (Button) rootView.findViewById(R.id.next);
        prepare();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusup();
                //previous.setEnabled(true);
                String nextpage = adapter.next();
                if (nextpage.equals("")) {
                } else {
                    String[] parts = nextpage.split("page=");
                    nextpage = parts[1];
                    Services.getInstance(getActivity()).getCaracters(successListener, errorListener, nextpage);
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
                if (nextpage.equals("")) {
                } else {
                    String[] parts = nextpage.split("page=");
                    nextpage = parts[1];

                    Services.getInstance(getActivity()).getCaracters(successListener, errorListener, nextpage);
                    //adapter.notifyDataSetChanged();
                    System.out.println("next");
                }
            }
        });


        //setupUI(rootView);
        ImageButton ib = (ImageButton) rootView.findViewById(R.id.imagePesquisa);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusup();
                String aux = searchTo.getText().toString();
                filter = aux;
                imm.hideSoftInputFromWindow(searchTo.getWindowToken(), 0);
                Services.getInstance(getActivity()).getCaractersNamePage(successListener, errorListener, "1", filter);
            }
        });

        // se o user clicar no enter quando tem a edit text aberta, corre o serviço para procurar pelo nome
        searchTo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    focusup();
                    String aux = searchTo.getText().toString();
                    filter = aux;
                    imm.hideSoftInputFromWindow(searchTo.getWindowToken(), 0);
                    Services.getInstance(getActivity()).getCaractersNamePage(successListener, errorListener, "1", filter);
                }
                return false;
            }
        });

        return rootView;
    }

    public void focusup() {
        sv.fullScroll(sv.FOCUS_UP);
    }

    public void prepare() {
        recyclerView = rootView.findViewById(R.id.recicleAdapter);
        // comando para ficar smooth a scrollbar da recyclerview
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (array.size() > 0) {
            List<String> a = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                a.add(array.get(i).toString());
            }
            Services.getInstance(getActivity()).getMultipleCharacters(sucessListener, errorListener, a);
        } else {
            Services.getInstance(getActivity()).getCaractersNamePage(successListener, errorListener, page, filter);
        }
    }

    @Override
    public void notifyBackPressed() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
