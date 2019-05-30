package com.example.rickandmorty;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.rickandmorty.ClassesObjDoProjeto.Character;
import com.example.rickandmorty.ClassesObjDoProjeto.CharacterList;
import com.example.rickandmorty.helpers.AppSingleton;

import java.util.ArrayList;

public class ServicoHelperStatic {
    private static ServicoHelperStatic serviceHelper;
    public static CharacterList chaList;


    private ServicoHelperStatic(){
    }

    public static synchronized ServicoHelperStatic getInstance(){
        if(serviceHelper == null){
            serviceHelper = new ServicoHelperStatic();
        }
        return serviceHelper;
    }

    public CharacterList getCharacterList() {
        if (chaList == null){
            chaList= new CharacterList();
        }
        return chaList;
    }

    public void setCharacterList(CharacterList a) {
        chaList = a;
    }

}
