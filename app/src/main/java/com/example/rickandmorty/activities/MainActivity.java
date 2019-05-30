package com.example.rickandmorty.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.rickandmorty.ClassesObjDoProjeto.CharacterList;
import com.example.rickandmorty.R;
import com.example.rickandmorty.ServicoHelperStatic;
import com.example.rickandmorty.helpers.RecicleAdapter;
import com.example.rickandmorty.helpers.Services;

public class MainActivity extends AppCompatActivity{

    ServicoHelperStatic serviceHelp;
    TextView tx1;
    boolean verificador = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceHelp = ServicoHelperStatic.getInstance();

        // chamar serviço
        // usar cena estatica


        Services.getInstance(this).getCaracters(successListener, errorListener,"1");

        TextView tx = (TextView)findViewById(R.id.title);
        tx1 = (TextView)findViewById(R.id.info);
        tx1.setVisibility(View.INVISIBLE);


        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/rick_morty.ttf");

        tx.setTypeface(custom_font);

        ConstraintLayout main_menu = findViewById(R.id.layout_main);
        main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificador==false) {
                }
                else{
                    hasTapped = true;
                    if (hasResponse)
                        changeActivity();
                }
            }
        });
    }
    boolean hasTapped = false;
    boolean hasResponse = false;

    public void changeActivity(){
        Intent randomIntent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(randomIntent);
        finish();
    }

    Response.Listener<CharacterList> successListener = new Response.Listener<CharacterList>() {
        @Override
        public void onResponse(CharacterList response) {
            findViewById(R.id.progressBar).setVisibility(View.GONE);
            // coiso visivel
            tx1.setVisibility(View.VISIBLE);

            hasResponse = true;
            verificador=true;
            serviceHelp.setCharacterList(response);
            if (hasTapped)
                changeActivity();
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            findViewById(R.id.progressBar).setVisibility(View.GONE);
            verificador=true;
            System.out.println("ERROR");
        }
    };

}
