package com.gameplaycoder.cartrell.musicalstructure.subActivities;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;

import com.gameplaycoder.cartrell.musicalstructure.utilities.ActivityStarter;
import com.gameplaycoder.cartrell.musicalstructure.utilities.IBackgroundManagerCallbacks;
import com.gameplaycoder.cartrell.musicalstructure.MainActivity;
import com.gameplaycoder.cartrell.musicalstructure.R;
import com.gameplaycoder.cartrell.musicalstructure.data.SongItem;
import com.gameplaycoder.cartrell.musicalstructure.adapters.ActiveQueueSongAdapter;

import java.util.ArrayList;

public class ActiveQueueActivity extends AppCompatActivity implements IBackgroundManagerCallbacks {

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // public
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // backgroundManagerOnUpdate
  //==============================================================================================
  /**
   * Called each time the bacground manager has updated its drawable
   * @param drawable the drawable that was updated
   */
  @Override
  public void backgroundManagerOnUpdate(GradientDrawable drawable) {
    //update the background of this activity with the new drawable
    ImageView backgroundImage = findViewById(R.id.imgBackground);
    backgroundImage.setBackground(drawable);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // protected
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // onCreate
  //==============================================================================================
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_active_queue);
    setupListView();
    setupButtons();
    updateStatus();
  }

  //==============================================================================================
  // onPostResume
  //==============================================================================================
  @Override
  protected void onPostResume() {
    super.onPostResume();
    setupBackground();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // private
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // removeSelectedSongs
  //==============================================================================================
  /**
   * Removes the selected songs from the active queue.
   */
  private void removeSelectedSongs() {
    ListView listView = (ListView) findViewById(R.id.list_songs);
    ActiveQueueSongAdapter adapter = (ActiveQueueSongAdapter) listView.getAdapter();

    //determine the first and last visible items within the queue
    int firstListItemPosition = listView.getFirstVisiblePosition();
    int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

    //Iterate, in REVERSE order, starting with the last index to the first, removing each
    // song. Because we're modifying an array as we're iterating through it, going in reverse
    // prevents the need to modify loop counters.
    for (int itemIndex = lastListItemPosition; itemIndex >= firstListItemPosition; itemIndex--) {
      View listItem = listView.getChildAt(itemIndex);
      CheckBox checkBox = (CheckBox)listItem.findViewById(R.id.chk_delete);
      if (!checkBox.isChecked()) {
        //sanity check - all list view items should have a checkbox, but if not, we get
        // to "show off" our use of the continue statement (:
        continue;
      }

      //remove the song from the active queue
      MainActivity.ActiveQueueSongs.removeAt(itemIndex);

      //remove the song from the adapter
      SongItem songItem = adapter.getItem(itemIndex);
      adapter.remove(songItem);
    }
  }

  //==============================================================================================
  // setupBackground
  //==============================================================================================
  /**
   * Sets up the background manager with this activity's background
   */
  private void setupBackground() {
    ImageView backgroundImage = findViewById(R.id.imgBackground);
    if (backgroundImage == null) {
      return;
    }

    MainActivity.BackgroundMgr.setCallbacks(this);
    MainActivity.BackgroundMgr.setDrawable((GradientDrawable)backgroundImage.getBackground());
  }

  //==============================================================================================
  // setCurrentPlayingSongAt
  //==============================================================================================
  /**
   * Sets the currently active song at the specified position
   * @param position The position within the active queue of the song to activate
   */
  private void setCurrentPlayingSongAt(int position) {
    MainActivity.ActiveQueueSongs.setCurrentIndex(position);
  }

  //==============================================================================================
  // setStatusNoSongs
  //==============================================================================================
  private void setStatusNoSongs() {
    findViewById(R.id.txtActiveQueueNoSongs).setVisibility(View.VISIBLE);
    findViewById(R.id.list_songs).setVisibility(View.INVISIBLE);
    findViewById(R.id.btnRemove).setVisibility(View.GONE);
  }

  //==============================================================================================
  // setStatusSomeSongs
  //==============================================================================================
  private void setStatusSomeSongs() {
    findViewById(R.id.txtActiveQueueNoSongs).setVisibility(View.INVISIBLE);
    findViewById(R.id.list_songs).setVisibility(View.VISIBLE);
    findViewById(R.id.btnRemove).setVisibility(View.VISIBLE);
  }

  //==============================================================================================
  // setupButtons
  //==============================================================================================
  /**
   * Setup the nav buttons
   */
  private void setupButtons() {
    final Activity activity = this;

    Button button = (Button)findViewById(R.id.btnNowPlaying);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ActivityStarter.NowPlaying(activity);
      }
    });

    button = (Button)findViewById(R.id.btnPlaylists);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ActivityStarter.Playlists(activity);
      }
    });

    button = (Button)findViewById(R.id.btnRemove);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        removeSelectedSongs();
      }
    });
  }

  //==============================================================================================
  // setupListView
  //==============================================================================================
  /**
   * Setup the list view
   */
  private void setupListView() {
    ArrayList<SongItem> sourceSongs = MainActivity.ActiveQueueSongs.getSongs();
    ArrayList<SongItem> songs = (ArrayList<SongItem>)sourceSongs.clone();
    ActiveQueueSongAdapter adapter = new ActiveQueueSongAdapter(this, songs);

    ListView listView = findViewById(R.id.list_songs);
    listView.setAdapter(adapter);

    //set up a click listener on list view items
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        setCurrentPlayingSongAt(position);
      }
    });
  }

  //==============================================================================================
  // updateStatus
  //==============================================================================================
  /**
   *
   */
  private void updateStatus() {
    ListView listView = findViewById(R.id.list_songs);
    ActiveQueueSongAdapter adapter = (ActiveQueueSongAdapter)listView.getAdapter();
    if (adapter.getCount() > 0) {
      setStatusSomeSongs();
    } else {
      setStatusNoSongs();
    }
  }
}
