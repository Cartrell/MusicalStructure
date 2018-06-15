package com.gameplaycoder.cartrell.musicalstructure.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gameplaycoder.cartrell.musicalstructure.data.PlaylistItem;
import com.gameplaycoder.cartrell.musicalstructure.R;

import java.util.ArrayList;

public class PlaylistAdapter extends ArrayAdapter<PlaylistItem> {
  ////////////////////////////////////////////////////////////////////////////////////////////////
  // public
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // ctor
  //==============================================================================================
  /**
   * Array adapter for the playlists view. Displays a list of playlists.
   * @param context The context environment in which this adapter will be used
   * @param playlistItems The array of playlist items to manage
   */
  public PlaylistAdapter(Context context, ArrayList<PlaylistItem> playlistItems) {
    super(context, 0, playlistItems);
  }

  //==============================================================================================
  // getView
  //==============================================================================================
  @SuppressLint("SetTextI18n")
  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    // Check if the existing view is being reused, otherwise inflate the view
    View listItemView = convertView;
    if (listItemView == null) {
      listItemView = LayoutInflater.from(getContext()).inflate(
        R.layout.playlist_list_item, parent, false);
    }

    TextView textView;

    PlaylistItem playlistItem = getItem(position);
    if (playlistItem != null) {
      textView = listItemView.findViewById(R.id.txt_name);
      String name = playlistItem.getName();
      if (name == null) {
        name = getContext().getString(R.string.no_name_specified);
      }
      textView.setText(name);

      textView = listItemView.findViewById(R.id.txt_numSongs);
      int numSongs = playlistItem.getSongs().size();
      String format = getContext().getString(R.string.num_songs_format);
      textView.setText(String.format(format, numSongs));
    } else {
      textView = listItemView.findViewById(R.id.txt_name);
      textView.setText(R.string.invalid_playlist);

      textView = listItemView.findViewById(R.id.txt_numSongs);
      textView.setText("");
    }

    return(listItemView);
  }
}
