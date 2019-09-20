package com.example.bookmymeal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookmymeal.adapter.FoodAdapter;
import com.example.bookmymeal.bean.Food;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodFragment extends Fragment
{

    RecyclerView rv;
    LinearLayoutManager manager;
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>adapter;
    ArrayList<Food>foodList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
       View v=inflater.inflate(R.layout.food_fragment,null);
       rv=v.findViewById(R.id.rv);
      final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setTitle("Food List");
        pd.setMessage("Progressing....");
        pd.show();
       String api=ServerAddress.SERVER_ADDRESS+"/foodList.jsp";
        StringRequest request=new StringRequest(Request.Method.GET, api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
               pd.dismiss();
               foodList=new ArrayList<>();
              try
              {
                  JSONArray jsonArray=new JSONArray(response);
                  for(int i=0;i<jsonArray.length();i++)
                  {
                      JSONObject jsonObject=jsonArray.getJSONObject(i);
                      String imageUrl="http://192.168.43.172:8084/BookMyMeal/food_images/"+jsonObject.getString("image");
                      Food f=new Food(jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getInt("price"),jsonObject.getString("desc"),imageUrl,jsonObject.getInt("categoryId"));
                      foodList.add(f);
                  }
                  adapter=new FoodAdapter(getActivity(),foodList);
                  manager=new LinearLayoutManager(getActivity());
                  rv.setAdapter(adapter);
                  rv.setLayoutManager(manager);

              }
              catch (Exception e)
              {
                  Toast.makeText(getActivity(), "in exception"+e, Toast.LENGTH_LONG).show();
              }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getActivity(), "In error Listener", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        queue.add(request);
        return v;
    }
}
