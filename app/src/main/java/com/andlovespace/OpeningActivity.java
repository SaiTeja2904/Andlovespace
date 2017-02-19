package com.andlovespace;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OpeningActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        drawerLayout=(DrawerLayout)findViewById(R.id.myDrawer);
        mToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nv=(NavigationView)findViewById(R.id.navigation);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               // openActivity(item.getTitle().toString());
                Toast.makeText(OpeningActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public void goToRegistration(View view) {
        EditText editText=(EditText)findViewById(R.id.editText);
        Intent intent = new Intent(OpeningActivity.this,HomePage.class);
        intent.putExtra("Phone",editText.getText().toString());
        startActivity(intent);
    }
    public void openGroup(View view) {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://chat.whatsapp.com/5eLRBC0latEBjneZbg5ede"));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
