package com.maduabughichi.android.alc4phase1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Button aboutAndella = findViewById(R.id.aboutAndela);
        aboutAndella.setOnClickListener(this);
        Button myProfile = findViewById(R.id.myProfile);
        myProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.aboutAndela:
                Intent goToAboutACL = new Intent(v.getContext(), AboutALC.class);
                startActivity(goToAboutACL);
                break;
            case R.id.myProfile:
                Intent myProfile = new Intent(v.getContext(), MyProfile.class);
                startActivity(myProfile);
                break;
            default:
                System.out.println("Hello!");
        }
    }
}
