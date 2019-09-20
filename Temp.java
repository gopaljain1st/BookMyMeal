package com.example.bookmymeal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Temp extends AppCompatActivity
{
    RecyclerView rv;
    RecyclerView.LayoutManager manager;
    ArrayList<Images>al;
    RecyclerView.Adapter<TempAdapter.TempViewHolder>adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp2);
        rv=findViewById(R.id.rv);
        manager= new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());
        al=new ArrayList<>();
        Images i=new Images(R.drawable.splash,R.drawable.logo);
        al.add(i);
        i=new Images(R.drawable.bg,R.drawable.ic_menu_gallery);
        al.add(i);
        i=new Images(R.mipmap.ic_launcher,R.drawable.logo);
        al.add(i);
        i=new Images(R.drawable.ic_launcher_background,R.drawable.bg);
        al.add(i);
        adapter=new TempAdapter(this,al);
        rv.setAdapter(adapter);
    }
}
