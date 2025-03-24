package com.example.bet1.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bet1.database.entity.player;

@Dao
public interface playerDao {
    @Query("SELECT * FROM player LIMIT 1")
    player getPlayer();

    @Query("UPDATE player SET name = :name")
    void updatePlayerName(String name);

    @Query("UPDATE player SET level = :level")
    void updatePlayerLevel(int level);

    @Query("UPDATE player SET xp = :xp")
    void updatePlayerXp(int xp);

    @Insert void insertPlayer(player p);

}
