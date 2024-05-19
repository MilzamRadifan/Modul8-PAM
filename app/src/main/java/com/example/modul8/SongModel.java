package com.example.modul8;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "song")
public class SongModel {
  @ColumnInfo(name = "id")
  @PrimaryKey(autoGenerate = true)
  private int id;
  @ColumnInfo(name = "title")
  private String title;
  @ColumnInfo(name = "artist")
  private String artist;
  @ColumnInfo(name = "album")
  private String album;
  @Ignore
  public SongModel (){};

  public SongModel(String title, String artist, String album) {
    this.title = title;
    this.artist = artist;
    this.album = album;
    this.id = 0;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

}