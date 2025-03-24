package com.example.bet1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bet1.database.DB;
import com.example.bet1.database.dao.matchDao;
import com.example.bet1.database.dao.playerDao;
import com.example.bet1.database.entity.player;

public class Home_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        DB db = DB.getInstance(getApplicationContext());
        playerDao pDao = db.playerDao();
        matchDao mDao = db.matchDao();

        updateDataDisplay(pDao, mDao);

        ImageButton home_edit_button = (ImageButton) findViewById(R.id.home_edit_button);
        home_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
                View v = li.inflate(R.layout.name_edit_layout,null);
                dialog
                        .setCancelable(false)
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText dialog_edit_name = (EditText) v.findViewById(R.id.name_edit);
                                String input = dialog_edit_name.getText().toString();

                                if(input.length() > 50){
                                    Toast.makeText(getApplicationContext(), "Name is too long (>50 characters)", Toast.LENGTH_LONG).show();
                                } else if (input.length() < 3){
                                    Toast.makeText(getApplicationContext(), "Name is too short (<3 characters)", Toast.LENGTH_LONG).show();
                                } else {
                                    pDao.updatePlayerName(input);
                                    Toast.makeText(view.getContext(), "Update successful", Toast.LENGTH_SHORT).show();
                                    dialogInterface.dismiss();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
            }
        });

        ImageButton home_start_button = (ImageButton) findViewById(R.id.home_start_button);
        home_start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMatch = new Intent(getApplicationContext(), Match_Activity.class);
                startActivity(goToMatch);
                finish();
            }
        });

        ImageButton home_reset_button = (ImageButton) findViewById(R.id.home_reset_button);
        home_reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDao.reset();
                Toast.makeText(getApplicationContext(), "Stats has been reset", Toast.LENGTH_SHORT).show();
                updateDataDisplay(pDao, mDao);
            }
        });
    }

    private void updateDataDisplay(playerDao pDao, matchDao mDao){
        player player = pDao.getPlayer();

        TextView player_name = (TextView) findViewById(R.id.home_player_name);
        player_name.setText(player.getName());

        TextView player_level = (TextView) findViewById(R.id.home_current_level);
        player_level.setText(String.valueOf(player.getLevel()));

        TextView player_xp = (TextView) findViewById(R.id.home_current_xp);
        player_xp.setText(String.valueOf(player.getXp()));

        TextView player_total_match = (TextView) findViewById(R.id.home_total_match);
        player_total_match.setText(String.valueOf(mDao.getTotalMatchFromPlayerId(player.getId())));

        TextView player_total_win = (TextView) findViewById(R.id.home_total_win);
        player_total_win.setText(String.valueOf(mDao.getTotalWinFromPlayerId(player.getId())));

        TextView player_total_loss = (TextView) findViewById(R.id.home_total_loss);
        player_total_loss.setText(String.valueOf(mDao.getTotalLossFromPlayerId(player.getId())));
    }
}
