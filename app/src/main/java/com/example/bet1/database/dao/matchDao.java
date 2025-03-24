package com.example.bet1.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bet1.database.entity.match;

@Dao
public interface matchDao {
    @Query("SELECT COUNT(*) FROM `match` JOIN player ON (player_id = player.id) WHERE player_id = :player_id")
    int getTotalMatchFromPlayerId(int player_id);

    @Query("SELECT COUNT(*) FROM `match` JOIN player ON (player_id = player.id) WHERE player_id = :player_id AND playerWin == 1")
    int getTotalWinFromPlayerId(int player_id);

    @Query("SELECT COUNT(*) FROM `match` JOIN player ON (player_id = player.id) WHERE player_id = :player_id AND playerWin == 0")
    int getTotalLossFromPlayerId(int player_id);

    @Query("DELETE FROM `match`")
    void reset();

    @Insert
    void insertMatch(match m);
}
