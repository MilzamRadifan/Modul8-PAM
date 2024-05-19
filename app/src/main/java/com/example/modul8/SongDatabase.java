package com.example.modul8;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SongModel.class}, version = 1)
public abstract class SongDatabase extends RoomDatabase {
  public abstract SongDao getSongDAO();
}