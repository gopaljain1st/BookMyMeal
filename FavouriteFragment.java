package com.example.bookmymeal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookmymeal.adapter.CartAdapter;
import com.example.bookmymeal.adapter.FavouriteAdapter;
import com.example.bookmymeal.adapter.FoodAdapter;
import com.example.bookmymeal.bean.Cart;
import com.example.bookmymeal.bean.Food;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment
{
    RecyclerView rv;
    ArrayList<Cart>al;
    RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.food_fragment,container,false);
        rv=v.findViewById(R.id.rv);
        DatabaseHelper helper=new DatabaseHelper(getActivity());
        SQLiteDatabase sqLiteDatabase=helper.getReadableDatabase();
        Cursor c=sqLiteDatabase.rawQuery("select * from favourite",null);
        al=new ArrayList<>();
        while(c.moveToNext())
        {
            Cart cart=new Cart(c.getInt(0),c.getInt(1),c.getString(2),c.getInt(3),c.getInt(4));
            al.add(cart);
        }
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getActivity());
        adapter=new FavouriteAdapter(getActivity(),al);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        sqLiteDatabase.close();
        return v;
    }
}
