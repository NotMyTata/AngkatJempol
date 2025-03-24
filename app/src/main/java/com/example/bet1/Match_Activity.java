package com.example.bet1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bet1.database.DB;
import com.example.bet1.database.dao.matchDao;
import com.example.bet1.database.dao.playerDao;
import com.example.bet1.database.entity.match;
import com.example.bet1.database.entity.player;

import java.util.Random;

public class Match_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_layout);

        DB db = DB.getInstance(getApplicationContext());
        playerDao pDao = db.playerDao();
        matchDao mDao = db.matchDao();
        player player = pDao.getPlayer();

        ImageButton player_left_thumb = (ImageButton) findViewById(R.id.match_player_left_thumb);
        // True - Thumb up, False - Thumb down
        player_left_thumb.setTag(false);
        player_left_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((boolean)player_left_thumb.getTag()){
                    player_left_thumb.setImageResource(R.drawable.left_thumb_up);
                    player_left_thumb.setTag(false);
                } else {
                    player_left_thumb.setImageResource(R.drawable.left_thumb_down);
                    player_left_thumb.setTag(true);
                }
            }
        });

        ImageButton player_right_thumb = (ImageButton) findViewById(R.id.match_player_right_thumb);
        player_right_thumb.setTag(false);
        player_right_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((boolean)player_right_thumb.getTag()){
                    player_right_thumb.setImageResource(R.drawable.right_thumb_down);
                    player_right_thumb.setTag(false);
                } else {
                    player_right_thumb.setImageResource(R.drawable.right_thumb_up);
                    player_right_thumb.setTag(true);
                }
            }
        });

        ImageView opponent_left_thumb = (ImageView) findViewById(R.id.match_opponent_left_thumb);
        ImageView opponent_right_thumb = (ImageView) findViewById(R.id.match_opponent_right_thumb);
        ImageButton check_button = (ImageButton) findViewById(R.id.match_check_button);
        // True - Player turn, False - Opponent turn
        check_button.setTag(true);
        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView turn_text = (TextView) findViewById(R.id.match_turn_text);
                TextView turn_desc = (TextView) findViewById(R.id.match_turn_desc);
                EditText number_guess = (EditText) findViewById(R.id.match_guess_number);

                if(number_guess.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter your guess!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if((boolean)check_button.getTag()){
                    // 0 - Thumb down, 1 - Thumb up
                    int opponent_left = 0;
                    if(opponent_left_thumb.getVisibility() != View.INVISIBLE){
                        opponent_left = new Random().nextInt(2);
                        ImageView opponent_left_thumb = (ImageView) findViewById(R.id.match_opponent_left_thumb);
                        if(opponent_left == 1){
                            opponent_left_thumb.setImageResource(R.drawable.left_thumb_up);
                        } else {
                            opponent_left_thumb.setImageResource(R.drawable.left_thumb_down);
                        }
                    }

                    int opponent_right = new Random().nextInt(2);
                    ImageView opponent_right_thumb = (ImageView) findViewById(R.id.match_opponent_right_thumb);
                    if(opponent_right == 1){
                        opponent_right_thumb.setImageResource(R.drawable.right_thumb_up);
                    } else {
                        opponent_right_thumb.setImageResource(R.drawable.right_thumb_down);
                    }

                    int sum = opponent_right+opponent_left;
                    if(player_left_thumb.getVisibility() != View.INVISIBLE && (boolean)player_left_thumb.getTag()){
                        sum++;
                    }
                    if((boolean)player_right_thumb.getTag()){
                        sum++;
                    }

                    int player_guess = Integer.parseInt(number_guess.getText().toString());

                    if(sum == player_guess){
                        Toast.makeText(getApplicationContext(), "You guessed right!", Toast.LENGTH_SHORT).show();
                        if(player_left_thumb.getVisibility() != View.INVISIBLE){
                            player_left_thumb.setVisibility(View.INVISIBLE);
                        } else {
                            player_right_thumb.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "You Won!", Toast.LENGTH_LONG).show();

                            mDao.insertMatch(new match(true, player.getId()));
                            if(player.getXp() + 10 >= 100){
                                pDao.updatePlayerLevel(player.getLevel() + 1);
                                pDao.updatePlayerXp((player.getXp() + 10) % 100);
                            } else {
                                pDao.updatePlayerXp(player.getXp() + 10);
                            }

                            Intent goBackToHome = new Intent(getApplicationContext(), Home_Activity.class);
                            startActivity(goBackToHome);
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "You guessed wrong!", Toast.LENGTH_SHORT).show();
                    }

                    turn_text.setText(getText(R.string.match_turn_text_opponent).toString());
                    turn_desc.setText(getText(R.string.match_turn_desc_opponent).toString());
                    number_guess.setVisibility(View.GONE);

                    check_button.setTag(false);
                } else {
                    int notInvisible = 2;

                    int opponent_left = 0;
                    if(opponent_left_thumb.getVisibility() != View.INVISIBLE){
                        notInvisible++;

                        opponent_left = new Random().nextInt(2);
                        ImageView opponent_left_thumb = (ImageView) findViewById(R.id.match_opponent_left_thumb);
                        if(opponent_left == 1){
                            opponent_left_thumb.setImageResource(R.drawable.left_thumb_up);
                        } else {
                            opponent_left_thumb.setImageResource(R.drawable.left_thumb_down);
                        }
                    }

                    int opponent_right = new Random().nextInt(2);
                    ImageView opponent_right_thumb = (ImageView) findViewById(R.id.match_opponent_right_thumb);
                    if(opponent_right == 1){
                        opponent_right_thumb.setImageResource(R.drawable.right_thumb_up);
                    } else {
                        opponent_right_thumb.setImageResource(R.drawable.right_thumb_down);
                    }

                    int sum = opponent_right+opponent_left;
                    if(player_left_thumb.getVisibility() != View.INVISIBLE && (boolean)player_left_thumb.getTag()){
                        sum++;
                        notInvisible++;
                    }
                    if((boolean)player_right_thumb.getTag()){
                        sum++;
                    }

                    int opponent_guess = new Random().nextInt(notInvisible);

                    if(sum == opponent_guess){
                        Toast.makeText(getApplicationContext(), "Opponent guessed right!", Toast.LENGTH_SHORT).show();
                        if(opponent_left_thumb.getVisibility() != View.INVISIBLE){
                            opponent_left_thumb.setVisibility(View.INVISIBLE);
                        } else {
                            opponent_right_thumb.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "You Lost!", Toast.LENGTH_LONG).show();

                            mDao.insertMatch(new match(false, player.getId()));
                            if(player.getXp() + 5 >= 100){
                                pDao.updatePlayerLevel(player.getLevel() + 1);
                                pDao.updatePlayerXp((player.getXp() + 5) % 100);
                            } else {
                                pDao.updatePlayerXp(player.getXp() + 5);
                            }

                            Intent goBackToHome = new Intent(getApplicationContext(), Home_Activity.class);
                            startActivity(goBackToHome);
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Opponent guessed wrong!", Toast.LENGTH_SHORT).show();
                    }

                    turn_text.setText(getText(R.string.match_turn_text_player).toString());
                    turn_desc.setText(getText(R.string.match_turn_desc_player).toString());
                    number_guess.setVisibility(View.VISIBLE);

                    check_button.setTag(true);
                }
            }
        });
    }
}
