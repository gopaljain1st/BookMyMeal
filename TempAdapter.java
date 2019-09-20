package com.example.bookmymeal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class TempAdapter extends RecyclerView.Adapter<TempAdapter.TempViewHolder>
{
    Context context;
    ArrayList<Images>al;
    TempAdapter(Context context,ArrayList al)
    {
        this.context=context;
        this.al=al;
    }
    @NonNull
    @Override
    public TempViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v= LayoutInflater.from(context).inflate(R.layout.temp,viewGroup,false);
        return new TempViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TempViewHolder tempViewHolder, int i)
    {
        Images e=al.get(i);
        tempViewHolder.iv1.setImageResource(e.id1);
        tempViewHolder.iv2.setImageResource(e.id2);
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    class TempViewHolder extends RecyclerView.ViewHolder
    {  ImageView iv1,iv2;
       public TempViewHolder(View v)
        {
            super(v);
           iv1=v.findViewById(R.id.iv1);
           iv2=v.findViewById(R.id.iv2);
        }
    }
}
