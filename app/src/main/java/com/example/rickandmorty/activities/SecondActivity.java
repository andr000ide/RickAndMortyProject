package com.example.rickandmorty.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rickandmorty.CharacterListFavourite;
import com.example.rickandmorty.ClassesObjDoProjeto.Character;
import com.example.rickandmorty.Fragments.Fragment2;
import com.example.rickandmorty.Fragments.Fragment3;
import com.example.rickandmorty.Fragments.Fragment7;
import com.example.rickandmorty.Fragments.SectionsAdapter;
import com.example.rickandmorty.R;
import com.example.rickandmorty.ServicoHelperStatic;
import com.example.rickandmorty.helpers.EndDrawerToggle;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static SecondActivity secondActivity;
    private static int check;
    Gson gson;
    private DrawerLayout drawer;
    private NavigationView navView;
    private int aux = 1;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        String json = mPreferences.getString("MyObject", "");

        gson = new Gson();
        if (json != "") {

            List<Character> listaenviar = new LinkedList<Character>(Arrays.asList(gson.fromJson(json, Character[].class)));
            CharacterListFavourite.getInstance(this).SetCharacterList(listaenviar);
        }


        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                if (aux == 1) {
                    if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        getSupportActionBar().setDisplayShowHomeEnabled(false);
                    } else {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                    }
                } else {
                    if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        getSupportActionBar().setDisplayShowHomeEnabled(false);
                    } else {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                    }
                }

            }

        });


        setNavigationView();
        setToolBar();

        if (savedInstanceState == null) {
            startFragment();
            navView.setCheckedItem(R.id.nav_characters);
            check = R.id.nav_characters;
        }
    }

    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        EndDrawerToggle toggle = new EndDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    // quando clica no botao com a seta para voltar a tras
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && getSupportFragmentManager().getBackStackEntryCount() > 0) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    public void setNavigationView() {
        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_characters:
                if (check != R.id.nav_characters) {
                    aux = 1;
                    emptyBackStack();
                    startFragment();
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment1()).addToBackStack(null).commit();
                    check = R.id.nav_characters;
                }
                break;
            case R.id.nav_episodes:
                if (check != R.id.nav_episodes) {
                    aux = 2;
                    emptyBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment2()).addToBackStack(null).commit();
                    check = R.id.nav_episodes;
                }
                break;
            case R.id.nav_location:
                if (check != R.id.nav_location) {
                    aux = 3;
                    emptyBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment3()).addToBackStack(null).commit();
                    check = R.id.nav_location;
                }
                break;
            case R.id.nav_piechar:
                if (check != R.id.nav_piechar) {
                    aux = 4;
                    emptyBackStack();
                    ViewPager viewpage = findViewById(R.id.viewpager);
                    viewpage.setAdapter(null);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment7()).addToBackStack(null).commit();
                    check = R.id.nav_piechar;
                }
                break;
            case R.id.nav_favorites:
                if (check != R.id.nav_favorites) {
                    aux = 5;
                    emptyBackStack();
                    startFragmentNovo();
                    check = R.id.nav_favorites;
                }
                break;
        }

        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    public void emptyBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; i++) {
            fm.popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        //funciona mas its weird
        //startFragment();

        // se for o fragment que tem a viewpager tem que fazer isto para o botao para tras dar bem
        if (aux == 1) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    super.onBackPressed();
                }
            }
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    super.onBackPressed();
                }
            }
        }

    }

    @Override
    protected void onStop() {
        colocarNosFavoritos();
        super.onStop();
    }

    public void colocarNosFavoritos() {

        String json = gson.toJson(CharacterListFavourite.getInstance(this).getCharacterList());
        mEditor.putString("MyObject", json);
        mEditor.commit();
        //colocar no characterlistfavourite
    }

    public void startFragment() {
        int numeroPaginas = ServicoHelperStatic.getInstance().getCharacterList().info.pages;
        SectionsAdapter fragment_adapter = new SectionsAdapter(getSupportFragmentManager(), numeroPaginas, "");
        ViewPager viewpage = findViewById(R.id.viewpager);
        viewpage.setAdapter(fragment_adapter);
    }

    public void startFragmentNovo() {
        int numpagina = ServicoHelperStatic.getInstance().getCharacterList().info.pages;
        ViewPager viewpage = findViewById(R.id.viewpager);
        ArrayList<Integer> array = new ArrayList<>();
        if (CharacterListFavourite.getInstance(this).getCharacterList() != null) {
            List<Character> aux = CharacterListFavourite.getInstance(this).getCharacterList();
            for (int i = 0; i < aux.size(); i++) {
                array.add(aux.get(i).id);
            }
        }
        SectionsAdapter fragment_adapter = new SectionsAdapter(getSupportFragmentManager(), numpagina, "", array);
        viewpage.setAdapter(fragment_adapter);
    }


    //---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
    public void updateFragment(int numpagina, String filter) {
        //int numeroPaginas= ServicoHelperStatic.getInstance().getCharacterList().info.pages;
        ViewPager viewpage = findViewById(R.id.viewpager);
        if (((SectionsAdapter) viewpage.getAdapter()).getCount() != numpagina && (!((SectionsAdapter) viewpage.getAdapter()).getFilter().equals(filter))) {
            SectionsAdapter fragment_adapter = new SectionsAdapter(getSupportFragmentManager(), numpagina, filter);
            viewpage.setAdapter(fragment_adapter);
        }
    }

    public void updateFragmentNovo(int numpagina, String filter) {
        ViewPager viewpage = findViewById(R.id.viewpager);
        ArrayList<Integer> array = new ArrayList<>();
        if (CharacterListFavourite.getInstance(this).getCharacterList() != null) {
            List<Character> aux = CharacterListFavourite.getInstance(this).getCharacterList();
            for (int i = 0; i < aux.size(); i++) {
                array.add(aux.get(i).id);
            }
        }
        if (((SectionsAdapter) viewpage.getAdapter()).getCount() != numpagina && (!((SectionsAdapter) viewpage.getAdapter()).getFilter().equals(filter))) {
            SectionsAdapter fragment_adapter = new SectionsAdapter(getSupportFragmentManager(), numpagina, filter, array);
            viewpage.setAdapter(fragment_adapter);
        }
    }

}
