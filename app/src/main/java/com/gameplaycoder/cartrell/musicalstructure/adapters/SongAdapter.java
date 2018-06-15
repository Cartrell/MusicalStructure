package com.gameplaycoder.cartrell.musicalstructure.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gameplaycoder.cartrell.musicalstructure.R;
import com.gameplaycoder.cartrell.musicalstructure.data.SongItem;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<SongItem> {
  ////////////////////////////////////////////////////////////////////////////////////////////////
  // public
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // ctor
  //==============================================================================================
  /**
   * Array adapter for the active queue. Displays a list of songs.
   * @param context The context environment in which this adapter will be used
   * @param songItems The array of song items to manage
   */
  public SongAdapter(Context context, ArrayList<SongItem> songItems) {
    super(context, 0, songItems);
  }

  //==============================================================================================
  // getView
  //==============================================================================================
  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    // Check if the existing view is being reused, otherwise inflate the view
    View listItemView = convertView;
    if (listItemView == null) {
      listItemView = LayoutInflater.from(getContext()).inflate(
        R.layout.song_list_item, parent, false);
    }

    SongItem songItem = getItem(position);

    TextView txtName = (TextView) listItemView.findViewById(R.id.txt_name);
    txtName.setText(songItem.getName());

    TextView txtLength = (TextView) listItemView.findViewById(R.id.txt_length);
    txtLength.setText(songItem.toTimeFormat());

    return(listItemView);
  }
}
