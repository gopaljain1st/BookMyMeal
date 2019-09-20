package com.example.bookmymeal;

import android.app.NotificationManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookmymeal.adapter.CartAdapter;
import com.example.bookmymeal.bean.Cart;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity
{
    Button btnOrder;
    RecyclerView rvCart;
    RecyclerView.LayoutManager manager;
    RecyclerView.Adapter<CartAdapter.CartViewHolder>adapter;
    ArrayList<Cart>al;
    ArrayList<Cart>cartOrderList;
    int total=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_list);
        initComponent();
        DatabaseHelper helper=new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase=helper.getReadableDatabase();
        Cursor c=sqLiteDatabase.rawQuery("select * from cart",null);
        while(c.moveToNext())
        {
            Cart cart=new Cart(c.getInt(0),c.getInt(1),c.getString(2),c.getInt(3),c.getInt(4));
            al.add(cart);
        }
        adapter=new CartAdapter(this,al);
        rvCart.setLayoutManager(manager);
        rvCart.setAdapter(adapter);
        sqLiteDatabase.close();
        btnOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder ab=new AlertDialog.Builder(CartActivity.this);
                ab.setTitle("Delivery Details");
                View v= LayoutInflater.from(CartActivity.this).inflate(R.layout.delivery_address,null);
                ab.setView(v);
                final EditText etContactNumber=v.findViewById(R.id.etContactNumber);
                final EditText etAddress=v.findViewById(R.id.etAddress);
                ab.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        DatabaseHelper helper = new DatabaseHelper(CartActivity.this);
                        SQLiteDatabase database = helper.getReadableDatabase();
                        String sql = "select * from cart";
                        Cursor c = database.rawQuery(sql,null);
                        cartOrderList = new ArrayList<>();
                        while(c.moveToNext())
                        {
                            int cid = c.getInt(0);
                            int foodItemId = c.getInt(1);
                            String foodName = c.getString(2);
                            int price = c.getInt(3);
                            int qty = c.getInt(4);
                            total+=price*qty;
                            Cart cart = new Cart(cid,foodItemId,foodName,price,qty);
                            cartOrderList.add(cart);
                        }
                        try {
                            HashMap<String,ArrayList<Cart>> hm = new HashMap<>();
                            hm.put("order",al);
                            Gson gson = new Gson();
                            final String jsonData = gson.toJson(hm);
                            Log.e("JsonData", "onClick: "+jsonData );
                            final String userId = "1";//sp.getString("id","0");
                            String api = ServerAddress.SERVER_ADDRESS+"/save_order.jsp";
                            StringRequest request = new StringRequest(Request.Method.POST, api, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response)
                                {
                                    if(response.contains("success"))
                                    {
                                        Toast.makeText(CartActivity.this, "Succesfully order placed", Toast.LENGTH_SHORT).show();
                                        NotificationCompat.Builder nb = new NotificationCompat.Builder(CartActivity.this);
                                        nb.setContentTitle("BookMyMeal");
                                        nb.setContentText("Order Placed");
                                        nb.setSmallIcon(R.mipmap.ic_launcher);
                                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                        manager.notify(1,nb.build());
                                    }
                                    else
                                        Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String>hm = new HashMap<>();
                                    hm.put("order_list",jsonData);
                                    hm.put("userid",userId);
                                    hm.put("contactPerson",etContactNumber.getText().toString());
                                    hm.put("address",etAddress.getText().toString());
                                    hm.put("total",""+total);
                                    return hm;
                                }
                            };
                            RequestQueue queue = Volley.newRequestQueue(CartActivity.this);
                            queue.add(request);
                        }
                        catch (Exception e)
                        {

                        }

                    }
                });
             ab.show();
            }
        });
    }
    private void initComponent()
    {
        btnOrder=findViewById(R.id.btnOrder);
        rvCart=findViewById(R.id.rvCart);
        al=new ArrayList<>();
        manager=new LinearLayoutManager(this);
    }
}
