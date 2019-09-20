package com.example.bookmymeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity
{

    Button btnSingin,btnSingup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        btnSingup=findViewById(R.id.signup);
        btnSingin=findViewById(R.id.singin);
        btnSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Welcome.this,RegisterationActivity.class);
                startActivity(in);
            }
        });
        btnSingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Welcome.this,Login.class);
                startActivity(in);
            }
        });
    }
}
