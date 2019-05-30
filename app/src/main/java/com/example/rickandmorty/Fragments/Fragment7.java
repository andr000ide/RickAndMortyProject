package com.example.rickandmorty.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.rickandmorty.CharacterListFavourite;
import com.example.rickandmorty.ClassesObjDoProjeto.Character;
import com.example.rickandmorty.ClassesObjDoProjeto.Episode;
import com.example.rickandmorty.ClassesObjDoProjeto.EpisodeList;
import com.example.rickandmorty.ClassesObjDoProjeto.LocationList;
import com.example.rickandmorty.LegendView;
import com.example.rickandmorty.R;
import com.example.rickandmorty.helpers.LocationsAdapter;
import com.example.rickandmorty.helpers.Services;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fragment7 extends Fragment {
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private List<Episode> season1 = new ArrayList<>();
    private List<Episode> season2 = new ArrayList<>();
    private List<Episode> season3 = new ArrayList<>();
    private int[] cores = new int[] { R.color.color1, R.color.color2, R.color.color3, R.color.color4,
            R.color.color5,R.color.color6,R.color.color7,R.color.color8,R.color.color9,R.color.color10,R.color.color11};
    private View rootView;
    public Fragment7(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       rootView = inflater.inflate(R.layout.fragment7, container, false);

        Services.getInstance(getActivity()).getEpisodes(successListener, errorListener,"");

        RadioGroup radioGroup =  rootView.findViewById(R.id.radiobutton);
        RadioButton button = rootView.findViewById(R.id.s01);
        button.setChecked(true);
        season1.clear();
        season2.clear();
        season3.clear();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                System.out.println("dededej");
                switch(checkedId) {
                    case R.id.s01:
                       fazergrafico(season1);
                       tabela(season1);
                        break;
                    case R.id.s02:
                        fazergrafico(season2);
                        tabela(season2);
                        break;
                    case R.id.s03:
                        fazergrafico(season3);
                        tabela(season3);
                        break;
                }
            }
        });

        return rootView;
    }

    Response.Listener<EpisodeList> successListener = new Response.Listener<EpisodeList>() {
        @Override
        public void onResponse(EpisodeList response) {

            for(int i =0;i< response.results.size();i++){
                String aux = response.results.get(i).episode;
                String[] parts = aux.split("E");
                aux=parts[0];
                switch (aux){
                    case "S01" : season1.add(response.results.get(i)); break;
                    case "S02" : season2.add(response.results.get(i)); break;
                    case "S03" : season3.add(response.results.get(i)); break;
                }
            }

            System.out.println("ejdejed");
            if(response.info.next.equals("")){
                fazergrafico(season1);
                tabela(season1);
            }
            else{
                String nextpage = response.info.next;
                String[] parts = nextpage.split("page=");
                nextpage = parts[1];
                Services.getInstance(getActivity()).getEpisodes(successListener, errorListener,nextpage);
            }
        }
    };
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            System.out.println("ERROR");
        }
    };


    public void tabela(List<Episode> lista){
        TableLayout table = rootView.findViewById(R.id.tablelayout);
        table.removeAllViews();
        table.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        table.setShrinkAllColumns(true);
        table.setStretchAllColumns(true);
        int columnNumber =4;
        int rowNumber=3 ;


        for(int i=0; i<rowNumber; i++) {
            TableRow row = new TableRow(getContext());
            row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            for(int j=0; j<columnNumber; j++) {
               /* TextView tv = new TextView(getContext());
                LinearLayout imagem = new LinearLayout(getContext());
                imagem.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
                if((i*4+j) < lista.size()){
                    tv.setText(lista.get(i*4+j).episode);
                    imagem.setBackgroundColor(cores[i*4+j]);
                   // imagem.setImageResource(R.drawable.ic_pie_chart_black_24dp);
                    row.addView(imagem);
                    //row.addView(tv);
                }*/
                if((i*4+j) < lista.size()) {
                    LegendView legendView = new LegendView(getContext(), cores[i * 4 + j], lista.get(i * 4 + j).episode);
                    row.addView(legendView);
                }
            }
            table.addView(row);
        }
        //List<Character> a= (List<Character>) CharacterListFavourite.getInstance(getActivity()).getCharacterList();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mPreferences.edit();
        Gson gson = new Gson();
        String json = mPreferences.getString("MyObject", "");
        List<Character> a = Arrays.asList(gson.fromJson(json, Character[].class));
        TextView ab = rootView.findViewById(R.id.testar1);
        TextView abc = rootView.findViewById(R.id.testar2);
        if(a.size()>0){
            String aux = a.get(0).name;
            //ab.setText(aux);
            //abc.setText(a.get(1).name);
        }
    }


    public void fazergrafico(List<Episode> lista){
        PieChart piechart = rootView.findViewById(R.id.piechart);
        piechart.clear();
        //piechart.setUsePercentValues(true);
        piechart.getDescription().setEnabled(false);


        // tipo padding i think
        piechart.setExtraOffsets(20,30,20,30);

        // rodar a cena
        piechart.setDragDecelerationFrictionCoef(0);
        piechart.setRotationEnabled(false);

        piechart.setDrawHoleEnabled(true);
        piechart.setHoleRadius(40);
        //piechart.setHoleColor(Color.WHITE);
        piechart.setTransparentCircleRadius(10);

        ArrayList<PieEntry> yValues2 = new ArrayList<>();
        ArrayList<PieEntry> yValues = new ArrayList<>();
        for(int i=0;i<lista.size();i++){
            yValues2.add(new PieEntry(lista.get(i).characters.size(),lista.get(i).episode));
            yValues.add(new PieEntry(lista.get(i).characters.size(),String.valueOf(lista.get(i).characters.size())));
        }
        //yValues.add(new PieEntry(34f,"Bangladesh"));
        //yValues.add(new PieEntry(23f,"USA"));
        //yValues.add(new PieEntry(35,"UK"));

        PieDataSet dataSet = new PieDataSet(yValues,"Episodes");

        //PieDataSet dataSet2 = new PieDataSet(yValues2,"Episodes");
        //dataSet.setSliceSpace(3f);
        //dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setColors(cores,getContext());

        //dataSet.setValues(yValues2);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(0.2f);

        PieData data = new PieData(dataSet);
        //data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(0f);
        data.setValueTextColor(Color.BLACK);

        piechart.setData(data);
        piechart.setEntryLabelColor(Color.BLACK);
        piechart.setEntryLabelTextSize(8f);
        piechart.getLegend().setEnabled(false);
        // Allows to disable all possible touch-interactions with the chart
        piechart.setTouchEnabled(false);
        //piechart.getLegend().setWordWrapEnabled(true);

    }
}
