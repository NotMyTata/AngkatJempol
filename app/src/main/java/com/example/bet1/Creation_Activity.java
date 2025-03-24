package com.example.bet1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bet1.database.dao.playerDao;
import com.example.bet1.database.DB;
import com.example.bet1.database.entity.player;

public class Creation_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation_layout);

        // Find and configure check button in creation_layout
        ImageButton creation_check_button = (ImageButton) findViewById(R.id.creation_check_button);
        creation_check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate user input in edit text
                EditText creation_input = (EditText) findViewById(R.id.creation_input);
                String input = creation_input.getText().toString();

                // Add more requirement if wanted
                if(input.length() > 50) {
                    showToastLong("Name is too long (>50 characters)");
                } else if (input.length() < 3) {
                    showToastLong("Name is too short (<3 characters)");
                } else {
                    SharedPreferences preferences = getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE);
                    preferences.edit().putBoolean(getString(R.string.preference_existing_profile_key), true).apply();

                    DB db = DB.getInstance(getApplicationContext());
                    playerDao pDao = db.playerDao();
                    pDao.insertPlayer(new player(input));

                    Intent continueToHome = new Intent(getApplicationContext(), Home_Activity.class);
                    startActivity(continueToHome);
                    finish();
                }

            }
        });
    }

    private void showToastLong(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
