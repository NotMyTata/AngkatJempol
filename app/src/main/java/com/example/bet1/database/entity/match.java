package com.example.bet1.database.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "match",
        foreignKeys = {
        @ForeignKey(
                entity = player.class,
                parentColumns = "id",
                childColumns = "player_id",
                onDelete = CASCADE,
                onUpdate = CASCADE
        )
})
public class match {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "playerWin")
    private boolean playerWin;
    @ColumnInfo(name = "player_id")
    private int player_id;

    public match(boolean playerWin, int player_id){
        this.playerWin = playerWin;
        this.player_id = player_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPlayerWin() {
        return playerWin;
    }

    public void setPlayerWin(boolean playerWin) {
        this.playerWin = playerWin;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }
}

