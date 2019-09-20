package com.example.bookmymeal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
   TabLayout tabLayout;
   FragmentTransaction transaction;
   FragmentManager manager;
   TextView profileName;
   SharedPreferences sp=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //View v=LayoutInflater.from(this).inflate(R.layout.nav_header_home,null);
       // profileName=v.findViewById(R.id.profileName);
       //findViewById(R.id.profileName);
       sp=getSharedPreferences("user",MODE_PRIVATE);
       String userName=sp.getString("userName","Gopal Jain");
        //profileName.setText("Hello");
        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Food Item"));
        tabLayout.addTab(tabLayout.newTab().setText("Packages"));
        tabLayout.addTab(tabLayout.newTab().setText("Favourite"));
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        transaction.replace(R.id.ll,new FoodFragment());
        transaction.commit();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {

                if(tab.getText().toString().equals("Food Item"))
                {
                     transaction=manager.beginTransaction();
                     transaction.replace(R.id.ll,new FoodFragment());
                     transaction.commit();
                }
                else if(tab.getText().toString().equals("Favourite"))
                {
                    transaction=manager.beginTransaction();
                    transaction.replace(R.id.ll,new FavouriteFragment());
                    transaction.commit();
                }
                else if(tab.getText().toString().equals("Packages"))
                {
                    transaction=manager.beginTransaction();
                    transaction.replace(R.id.ll,new FoodPackageFragment());
                    transaction.commit();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent in=new Intent(HomeActivity.this,CartActivity.class);
                startActivity(in);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView =navigationView.getHeaderView(0);
          profileName= headerView.findViewById(R.id.profileName);
          profileName.setText(userName);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.order)
        {
            Intent in=new Intent(this,CartActivity.class);
            startActivity(in);
                // Handle the camera action
        } else if (id == R.id.orderHistory) {

        } else if (id == R.id.offerList) {

        } else if (id == R.id.contact) {

        } else if (id == R.id.share) {

        } else if (id == R.id.logout)
        {
            SharedPreferences.Editor edit=sp.edit();
            edit.remove("id");
            edit.remove("userName");
            edit.remove("userMobile");
            edit.remove("userEmail");
            edit.commit();
            Intent in=new Intent(this,Welcome.class);
            startActivity(in);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
