package com.example.rickandmorty.Fragments;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionsAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    String filter="";
    int count = 0;
    ArrayList<Integer> array= new ArrayList<>();
    public SectionsAdapter(FragmentManager fm, int count,String filter){
        super(fm);
        this.count = count;
        this.filter=filter;
    }
    public SectionsAdapter(FragmentManager fm, int count,String filter,ArrayList<Integer> array){
        super(fm);
        this.count = count;
        this.filter=filter;
        this.array=array;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new Fragment1();
        ((Fragment1) fragment).setPage(String.valueOf(i+1));
        ((Fragment1) fragment).setFilter(filter);
        if(array.size()>0){
            ((Fragment1) fragment).setArray(array);
        }
        return fragment;
    }

    //-------------------------------------
    // para "ouvir" o notifidatasetChanged
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
    //-------------------------------------
    //-------------------------------------

    @Override
    public int getCount() {
        //return mFragmentList.size();
        return count;
    }

    public void setCount(int page){
        this.count=page;
    }

    public void setFilter(String filter){
        this.filter=filter;
    }

    public String getFilter(){
        return filter;
    }
}
