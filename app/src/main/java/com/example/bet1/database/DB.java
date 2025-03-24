package com.example.bet1.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bet1.database.dao.matchDao;
import com.example.bet1.database.dao.playerDao;
import com.example.bet1.database.entity.match;
import com.example.bet1.database.entity.player;

@Database(entities = {player.class, match.class}, version = 1)
public abstract class DB extends RoomDatabase {
    public abstract playerDao playerDao();
    public abstract matchDao matchDao();
    private static volatile DB INSTANCE;

    public static DB getInstance(Context context){
        if(INSTANCE == null){
            synchronized (DB.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DB.class, "DB")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
