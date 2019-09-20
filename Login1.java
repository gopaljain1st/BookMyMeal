/*
*
*
*
*
* package com.example.bookmymeal;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login1 extends AppCompatActivity
{
    EditText etMobile,etPassword;
    TextView tvForgetPassword,tvNewUser;
    Button btnLogin;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initComponent();
        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(getColor(R.color.dark)));
        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent in=new Intent(Login1.this,RegisterationActivity.class);
                startActivity(in);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String api = ServerAddress.SERVER_ADDRESS + "/loginUsingEmail.jsp";
                Toast.makeText(Login1.this, "i am in email", Toast.LENGTH_SHORT).show();
                StringRequest request = new StringRequest(Request.Method.POST, api, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //             pd.dismiss();
                        if (response.contains("success")) {
                            Toast.makeText(Login1.this, "Success", Toast.LENGTH_SHORT).show();
                        } else
                        {
                            Toast.makeText(Login1.this, ""+etMobile.getText().toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(Login1.this, "" + response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, null) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("email",etMobile.getText().toString());
                        hm.put("password",etPassword.getText().toString());
                        return hm;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(Login1.this);
                queue.add(request);
            }

        });
    }
    private void initComponent()
    {
        etMobile=findViewById(R.id.etMobile);
        etPassword=findViewById(R.id.etPassword);
        tvForgetPassword=findViewById(R.id.tvForgetPassword);
        btnLogin=findViewById(R.id.btnLogin);
        tvNewUser=findViewById(R.id.tvNewUser);
    }
}
*/