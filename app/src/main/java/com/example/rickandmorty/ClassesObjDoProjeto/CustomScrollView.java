package com.example.rickandmorty.ClassesObjDoProjeto;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {
    boolean isKeyboardShowing ;
    public CustomScrollView(Context context) {
        super(context);
        init();
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }



    public void init(){
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isAcceptingText()){
            isKeyboardShowing=true;
        }else isKeyboardShowing=false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("something");
        init();
        if(isKeyboardShowing){
            hideSoftKeyboard(getContext());
            return false;
        }
        return super.onInterceptTouchEvent(ev);

    }
    public void hideSoftKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager)  context.getSystemService(context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }

}
