package com.gameplaycoder.cartrell.musicalstructure.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gameplaycoder.cartrell.musicalstructure.R;
import com.gameplaycoder.cartrell.musicalstructure.data.SongItem;

import java.util.ArrayList;

public class ActiveQueueSongAdapter extends ArrayAdapter<SongItem> {
  ////////////////////////////////////////////////////////////////////////////////////////////////
  // members
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //An array of song IDs that have been checked in this view
  private ArrayList<Integer> mSelectedSongIds;

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // public
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // ctor
  //==============================================================================================
  /**
   * Array adapter for the active queue. Displays a list of songs, and each list view has an
   * additional check box used for selecting songs for removal from the queue.
   * @param context The context environment in which this adapter will be used
   * @param songItems The array of song items to manage
   */
  public ActiveQueueSongAdapter(Context context, ArrayList<SongItem> songItems) {
    super(context, 0, songItems);
    mSelectedSongIds = new ArrayList<>();
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
        R.layout.active_queue_list_item, parent, false);
    }

    //get the song at the current position
    SongItem songItem = getItem(position);

    //update the list view the song's information
    TextView txtName = (TextView) listItemView.findViewById(R.id.txt_name);
    txtName.setText(songItem.getName());

    TextView txtLength = (TextView) listItemView.findViewById(R.id.txt_length);
    txtLength.setText(songItem.toTimeFormat());

    //checkbox is a little different, because this information is not stored with each song -
    // it's only relevant to the view
    CheckBox checkBox = (CheckBox) listItemView.findViewById(R.id.chk_delete);
    checkBox.setTag(songItem);

    checkBox.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        CheckBox chxBox = (CheckBox)view;
        SongItem songItem = (SongItem)chxBox.getTag();
        if (songItem == null) {
          return;
        }

        Integer songId = songItem.getId();
        //toggle the addition of the song id to the array of selected song ids
        if (chxBox.isChecked()) {
          if (!mSelectedSongIds.contains(songId)) {
            mSelectedSongIds.add(songId);
          }
        } else {
          if (mSelectedSongIds.contains(songId)) {
            mSelectedSongIds.remove(songId);
          }
        }
      }
    });

    //update the checkbox status depending on if the song's id has been selected
    if (mSelectedSongIds.contains(songItem.getId())) {
      checkBox.setChecked(true);
    } else {
      checkBox.setChecked(false);
    }
    return(listItemView);
  }
}
