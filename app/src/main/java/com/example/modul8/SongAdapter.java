package com.example.modul8;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>
  {
    private Context context;
    private List<SongModel> songModel;
    private static ClickListener clickListener;
    public SongAdapter(Context context, List<SongModel> songModel){
      this.context = context;
      this.songModel = songModel;
    }

    public void
    setOnItemClickListener(ClickListener clickListener) {
      SongAdapter.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View vh  = LayoutInflater.from(this.context).inflate(R.layout.item_song, parent, false);
      return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      SongModel song = this.songModel.get(position);

      holder.tvTitle.setText(song.getTitle());
      holder.tvArtist.setText(song.getArtist());
      holder.tvAlbum.setText(song.getAlbum());
      holder.tvDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          int adapterPosition = holder.getAdapterPosition();
          deleteItem(adapterPosition);
        }
      });
    }
    @Override
    public int getItemCount() {
      return songModel.size();
    }

    public interface ClickListener {
      void onItemClick(int position, View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
      TextView tvTitle, tvArtist, tvAlbum, tvDelete;
      public ViewHolder(@NonNull View itemView) {
        super(itemView);

        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvArtist = itemView.findViewById(R.id.tvArtist);
        tvAlbum = itemView.findViewById(R.id.tvAlbum);
        tvDelete = itemView.findViewById(R.id.tvDelete);
      }
    }
    private void deleteItem(int position) {
      SongModel deletedSong = songModel.get(position);
      songModel.remove(position);
      notifyItemRemoved(position);

      ExecutorService executorService = Executors.newSingleThreadExecutor();
      executorService.execute(new Runnable() {
        @Override
        public void run() {
          MusicActivity.getAppDatabase().getSongDAO().deleteSong(deletedSong);
        }
      });
    }
  }