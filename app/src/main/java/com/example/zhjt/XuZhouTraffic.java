package com.example.zhjt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.zhjt.Utils.TitleRecyclerAdapter;
import com.example.zhjt.Bean.title;
import com.example.zhjt.XunZhouFragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;


public class XuZhouTraffic extends AppCompatActivity{
    private List<title> titles;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuzhoutraffic);
        inittitle();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_title);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        TitleRecyclerAdapter adapter=new TitleRecyclerAdapter(titles,XuZhouTraffic.this);
        recyclerView.setAdapter(adapter);
        replaceFragment(new NewsFragment(this));
    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.xunzou_content_fragment,fragment);
        transaction.commit();
    }
    public void inittitle(){
        titles=new ArrayList<>();
        title t1=new title(1,"要闻");
        titles.add(t1);
        title t2=new title(2,"军事");
        titles.add(t2);
        title t3=new title(3,"财经");
        titles.add(t3);
        title t4=new title(4,"社会");
        titles.add(t4);
        title t5=new title(5,"体育");
        titles.add(t5);
    }
}
