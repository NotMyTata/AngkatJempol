package com.example.bet1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Main_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        // Find and configure the play button on main_layout
        ImageButton main_play_button = (ImageButton) findViewById(R.id.main_play_button);
        main_play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE);
                boolean profileExisted = preferences.getBoolean(getString(R.string.preference_existing_profile_key), false);

                if(profileExisted){
                    Intent goToHome = new Intent(getApplicationContext(), Home_Activity.class);
                    startActivity(goToHome);
                    finish();
                } else {
                    Intent goToCreation = new Intent(getApplicationContext(), Creation_Activity.class);
                    startActivity(goToCreation);
                    finish();
                }
            }
        });

        // Find and configure the cancel button on main_layout
        ImageButton main_cancel_button = (ImageButton) findViewById(R.id.main_cancel_button);
        main_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }
}
