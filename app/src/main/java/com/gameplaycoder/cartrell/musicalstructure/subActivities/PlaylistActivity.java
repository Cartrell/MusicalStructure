package com.gameplaycoder.cartrell.musicalstructure.subActivities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gameplaycoder.cartrell.musicalstructure.utilities.ActivityStarter;
import com.gameplaycoder.cartrell.musicalstructure.utilities.IBackgroundManagerCallbacks;
import com.gameplaycoder.cartrell.musicalstructure.MainActivity;
import com.gameplaycoder.cartrell.musicalstructure.data.PlaylistItem;
import com.gameplaycoder.cartrell.musicalstructure.R;
import com.gameplaycoder.cartrell.musicalstructure.data.SongItem;
import com.gameplaycoder.cartrell.musicalstructure.adapters.SongAdapter;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity implements IBackgroundManagerCallbacks {

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // members
  ////////////////////////////////////////////////////////////////////////////////////////////////
  private PlaylistItem mPlaylistItem;
  private Toast mToastMessage;

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
    setContentView(R.layout.activity_playlist);

    createPlaylistItem();
    setupListView();
    setupButtons();
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
  // addAllSongsToActiveQueue
  //==============================================================================================
  /**
   * Adds all songs in the playlist to the active queue.
   */
  private void addAllSongsToActiveQueue() {
    ArrayList<SongItem> songs = mPlaylistItem.getSongs();
    int numSongs = songs.size();

    for (int songIndex = 0; songIndex < numSongs; songIndex++) {
      MainActivity.ActiveQueueSongs.add(songs.get(songIndex));
    }

    presentToast(getString(R.string.added_all_songs));
  }

  //==============================================================================================
  // addSongToActiveQueue
  //==============================================================================================
  /**
   * Adds a song to the active queue.
   * @param songItem The song item to add.
   */
  private void addSongToActiveQueue(SongItem songItem) {
    MainActivity.ActiveQueueSongs.add(songItem);
    String format = getString(R.string.added_song_format);
    String message = String.format(format, songItem.getName());
    presentToast(message);
  }

  //==============================================================================================
  // createPlaylistItem
  //==============================================================================================
  /**
   * Creates a playlist item based on the data in the intent.
   * See ActivityStarter.Playlist for the format of the playlist data in the intent.
   */
  private void createPlaylistItem() {
    Intent intent = getIntent();
    String name = intent.getStringExtra("name");

    mPlaylistItem = new PlaylistItem(name);

    TextView headerTextView = (TextView)findViewById(R.id.txtHeader);
    headerTextView.setText(name);

    ArrayList<SongItem> songItems = mPlaylistItem.getSongs();
    int numSongs = intent.getIntExtra("numSongs", 0);
    for (int songIndex = 0; songIndex < numSongs; songIndex++) {
      String propName = "songName" + String.valueOf(songIndex);
      String songName = intent.getStringExtra(propName);

      String propLength = "songLength" + String.valueOf(songIndex);
      int songLength = intent.getIntExtra(propLength, 0);

      songItems.add(new SongItem(songName, songLength));
    }
  }

  //==============================================================================================
  // presentToast
  //==============================================================================================
  /**
   * Helper function for presenting a toast message.
   * @param message The message to show.
   */
  private void presentToast(String message) {
    //cancel the previous toast first, in one existed.
    if (mToastMessage != null) {
      mToastMessage.cancel();
    }

    mToastMessage = Toast.makeText(this, message, Toast.LENGTH_SHORT);
    mToastMessage.setGravity(Gravity.BOTTOM, 0, 0);
    mToastMessage.show();
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
  // setupButtons
  //==============================================================================================
  /**
   * Set up nav buttons
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

    button = (Button)findViewById(R.id.btnAddAllSongs);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addAllSongsToActiveQueue();
      }
    });

    button = (Button)findViewById(R.id.btnActiveQueue);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ActivityStarter.ActiveQueue(activity);
      }
    });
  }

  //==============================================================================================
  // setupListView
  //==============================================================================================
  /**
   * Set up the list view that shows the songs in the playlist
   */
  private void setupListView() {
    SongAdapter adapter = new SongAdapter(this, mPlaylistItem.getSongs());
    ListView listView = (ListView) findViewById(R.id.list_songs);
    listView.setAdapter(adapter);

    //set up a click listener on list view items
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        SongItem songItem = (SongItem)adapterView.getItemAtPosition(position);
        addSongToActiveQueue(songItem);
      }
    });
  }
}
