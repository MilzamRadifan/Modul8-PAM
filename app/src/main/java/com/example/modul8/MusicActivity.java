package com.example.modul8;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class MusicActivity extends AppCompatActivity {
  private static SongDatabase songDatabase;
  private RecyclerView recyclerView;
  public static List<SongModel> songModels = new ArrayList<>();
  public static SongAdapter songAdapter;
  Button btTambah, bt;

  public static SongAdapter getSongAdapter() {
    return songAdapter;
  }
  public static List<SongModel> getSongModel() {
    return songModels;
  }
  public static SongDatabase getAppDatabase() {
    return songDatabase;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_music);

    RoomDatabase.Callback myCallback = new RoomDatabase.Callback() {
      @Override
      public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
      }

      @Override
      public void onOpen(@NonNull SupportSQLiteDatabase db) {
        super.onOpen(db);
      }
    };

    songDatabase = Room.databaseBuilder(getApplicationContext(),
            SongDatabase.class, "songDB").addCallback(myCallback).build();

    loadSongsFromDatabase();

    recyclerView = findViewById(R.id.rvSong);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    songAdapter = new SongAdapter(MusicActivity.this, songModels);
    recyclerView.setAdapter(songAdapter);

    btTambah = findViewById(R.id.btTambah);
    btTambah.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MusicActivity.this, AddSong.class);
        startActivity(intent);
      }
    });
  }

  private void loadSongsFromDatabase() {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.execute(new Runnable() {
      @Override
      public void run() {
        List<SongModel> songs = songDatabase.getSongDAO().getAllSongs();

        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            songModels.clear();
            songModels.addAll(songs);
            songAdapter.notifyDataSetChanged();
          }
        });
      }
    });
  }

  protected void onResume() {
    super.onResume();
    loadSongsFromDatabase();
  }
}