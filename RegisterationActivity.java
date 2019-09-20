package com.example.bookmymeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.bookmymeal.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterationActivity extends AppCompatActivity
{
    EditText etName,etPassword,etEmail,etMobile;
    Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        initComponent();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String api=ServerAddress.SERVER_ADDRESS+"/registeration.jsp";
                boolean flag;
               if(etName.getText().toString().length()==0)
               {
                   etName.setError("Username Required");
                   flag=false;
               }
                if(etEmail.getText().toString().length()==0)
                {
                    etEmail.setError("Email Required");
                    flag=false;
                }
                if(etMobile.getText().toString().length()==0)
                {
                    etMobile.setError("Mobile Required");
                    flag=false;
                }
                if(etPassword.getText().toString().length()==0)
                {
                    etPassword.setError("Password Required");
                    flag=false;
                }
                StringRequest request=new StringRequest(Request.Method.POST, api, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success"))
                        {
                            Toast.makeText(RegisterationActivity.this, "Registeration Successful", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(RegisterationActivity.this, Login.class);
                            startActivity(in);
                            finish();
                        } else
                            Toast.makeText(RegisterationActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterationActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String>hm=new HashMap<>();
                        hm.put("name",etName.getText().toString());
                        hm.put("email",etEmail.getText().toString());
                        hm.put("mobile",etMobile.getText().toString());
                        hm.put("password",etPassword.getText().toString());

                        return hm;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(RegisterationActivity.this);
                queue.add(request);
            }
        });
    }
    private void initComponent()
    {
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etMobile=findViewById(R.id.etMobile);
        btnRegister=findViewById(R.id.btnsingup);
    }
}
