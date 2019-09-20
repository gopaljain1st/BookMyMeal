package com.example.bookmymeal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity
{
    EditText etMobile,etPassword;
    TextView tvForgetPassword;
    Button btnSingIn;
    SharedPreferences sp=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        initComponent();
        ActionBar ab=getSupportActionBar();
        // ab.setBackgroundDrawable(new ColorDrawable(getColor(R.color.dark)));
        sp=getSharedPreferences("user",MODE_PRIVATE);
        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=true;
                if(etMobile.getText().toString().length()==0)
                {
                    etMobile.setError("Mobile No Required");
                    flag=false;
                }
                if(etPassword.getText().toString().length()==0)
                {
                    etPassword.setError("Password Required");
                    flag=false;
                }
                if(flag) {
                    final ProgressDialog pd = new ProgressDialog(Login.this);
                    pd.setMessage("Authenticating...");
                    pd.show();
                    Pattern p = Pattern.compile("(0|91)?[6789][0-9]{9}");
                    Matcher m = p.matcher(etMobile.getText().toString().trim());

                    Pattern p1 = Pattern.compile("[0-9a-zA-Z@!#$%&^*?.+_*/]{4,12}");
                    Matcher m1 = p1.matcher(etPassword.getText().toString().trim());
                    if ((m.find() && m.group().equals(etMobile.getText().toString().trim()))&&(m1.find() && m1.group().equals(etPassword.getText().toString().trim())))
                    {
                        String api = ServerAddress.SERVER_ADDRESS + "/loginUsingMobile.jsp";
                        StringRequest request = new StringRequest(Request.Method.POST, api, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                if (response.contains("success"))
                                {   String arr[] = response.split(" ");
                                    Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor=sp.edit();
                                    editor.putString("id",arr[1]);
                                    editor.putString("userName",arr[2]);
                                    editor.putString("userEmail",arr[3]);
                                    editor.putString("userMobile",arr[4]);
                                    editor.commit();
                                    Intent in=new Intent(Login.this,HomeActivity.class);
                                    startActivity(in);
                                } else
                                    Toast.makeText(Login.this, "" + response, Toast.LENGTH_SHORT).show();
                            }
                        }, null) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("mobile", etMobile.getText().toString());
                                hm.put("password", etPassword.getText().toString());
                                return hm;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(Login.this);
                        queue.add(request);
                    }
                    else if (!(m.find() && m.group().equals(etMobile.getText().toString().trim())))
                        etMobile.setError("Inavlid Mobile No");

                    else if(!(m1.find() && m1.group().equals(etPassword.getText().toString().trim())))
                        etPassword.setError("Invalid Password");

                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        String id=sp.getString("id","0");
        if(!id.equals("0"))
        {
            Intent in=new Intent(this,HomeActivity.class);
            startActivity(in);
        }
    }

    private void initComponent()
    {
        etMobile=findViewById(R.id.etMobile);
        etPassword=findViewById(R.id.etPassword);
        tvForgetPassword=findViewById(R.id.tvForgetPassword);
        btnSingIn=findViewById(R.id.btnsingin);
    }
}
