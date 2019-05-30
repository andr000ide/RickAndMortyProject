package com.example.rickandmorty;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class LegendView extends LinearLayout {

    private String name;
    private int color;

    public LegendView(Context context) {
        super(context);
        init();
    }
    public LegendView(Context context, int color, String name){
        super(context);
        this.color = color;
        this.name = name;
        init();
    }
    public LegendView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LegendView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LegendView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    void init(){
        View rootView = inflate(getContext(), R.layout.legend_view,this);
        LinearLayout linearLayout = rootView.findViewById(R.id.legend_color);
        TextView textView = rootView.findViewById(R.id.legend_name);
        linearLayout.setBackgroundColor(getResources().getColor(color));
        textView.setText(this.name);
    }
}
