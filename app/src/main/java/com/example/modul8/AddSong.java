package com.example.modul8;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddSong extends AppCompatActivity {

  private SongModel songModel;
  EditText etTitle, etArtist, etAlbum;
  Button btSave;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_song);

    etTitle = findViewById(R.id.etTitle);
    etArtist = findViewById(R.id.etArtist);
    etAlbum = findViewById(R.id.etAlbum);
    btSave = findViewById(R.id.btSave);

    btSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String title = etTitle.getText().toString();
        String artist = etArtist.getText().toString();
        String album = etAlbum.getText().toString();

        songModel = new SongModel(title, artist, album);

        addPersonInBackground(songModel);

        Intent intent = new Intent(AddSong.this, MusicActivity.class);
        startActivity(intent);
      }
    });
  }

  public void addPersonInBackground(SongModel songModel){
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    executorService.execute(new Runnable() {
      @Override
      public void run() {
        // background task
        MusicActivity.getAppDatabase().getSongDAO().addSong(songModel);

        MusicActivity.getSongModel().add(songModel);
        MusicActivity.getSongAdapter().notifyDataSetChanged();
      }
    });
  }
}