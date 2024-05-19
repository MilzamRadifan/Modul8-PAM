package com.example.modul8;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.security.PublicKey;
import java.util.List;
@Dao
public interface SongDao {
  @Insert
  public void addSong(SongModel song);

  @Update
  public void updateSong(SongModel song);

  @Delete
  public void deleteSong(SongModel song);

  @Query("SELECT * FROM song")
  List<SongModel> getAllSongs();

  @Query("select * from song where id ==:id")
  public SongModel getSong(int id);
}