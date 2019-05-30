package com.example.rickandmorty.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.rickandmorty.ClassesObjDoProjeto.Character;
import com.example.rickandmorty.R;
import com.example.rickandmorty.helpers.CharactersAtLocationAdapter;

public class Fragment5 extends Fragment {
    private Character[] listpers;
    private String nameDim,nameLoc,nameType;
    private View rootView;


    public static Fragment5 newInstance(Character[] a,String nameDim, String nameLoc, String nameType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Personagens", a);
        bundle.putString("nameDim",nameDim);
        bundle.putString("nameLoc",nameLoc);
        bundle.putString("nameType",nameType);

        Fragment5 fragment = new Fragment5();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            listpers = (Character[]) bundle.get("Personagens");
            nameDim = (String) bundle.get("nameDim");
            nameLoc = (String) bundle.get("nameLoc");
            nameType = (String) bundle.get("nameType");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment5, container, false);

        readBundle(getArguments());

        //LinearLayout mudarTexto = rootView.findViewById(R.id.layout_texto);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/Calligraphr-Regular.ttf");


        TextView locName = rootView.findViewById(R.id.nomeLocal);
        locName.setText(nameLoc);
        locName.setTypeface(custom_font);
        TextView dimName = rootView.findViewById(R.id.nomeDim);
        dimName.setText(nameDim);
        dimName.setTypeface(custom_font);
        TextView typeName = rootView.findViewById(R.id.nomeTipo);
        typeName.setText(nameType);
        typeName.setTypeface(custom_font);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setNestedScrollingEnabled(false);
        CharactersAtLocationAdapter adapter = new CharactersAtLocationAdapter(getContext(),listpers);
        gridView.setAdapter(adapter);
        setGridViewHeightBasedOnChildren(gridView, 3);

        for(int i =0;i<listpers.length;i++){
            System.out.println(listpers[i].name);
        }

        return rootView;
    }


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