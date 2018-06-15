package com.gameplaycoder.cartrell.musicalstructure;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.gameplaycoder.cartrell.musicalstructure.adapters.PlaylistAdapter;
import com.gameplaycoder.cartrell.musicalstructure.data.ActiveQueue;
import com.gameplaycoder.cartrell.musicalstructure.data.PlaylistItem;
import com.gameplaycoder.cartrell.musicalstructure.data.Playlists;
import com.gameplaycoder.cartrell.musicalstructure.utilities.ActivityStarter;
import com.gameplaycoder.cartrell.musicalstructure.utilities.BackgroundManager;
import com.gameplaycoder.cartrell.musicalstructure.utilities.IBackgroundManagerCallbacks;
import com.gameplaycoder.cartrell.musicalstructure.utilities.SongPlayer;

public class MainActivity extends AppCompatActivity implements IBackgroundManagerCallbacks {

  /**
   * Manager of all songs currently playing in 'now playing'
   */
  public static ActiveQueue ActiveQueueSongs = new ActiveQueue();

  /**
   * Manages teh song that is currently "playing"
   */
  public static SongPlayer ActiveSongPlayer = new SongPlayer();

  /**
   * Controls the visuals of the background image
   */
  public static BackgroundManager BackgroundMgr = new BackgroundManager();

  /**
   * Manager of all available playlists
   */
  private Playlists mPlaylists;

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
    setContentView(R.layout.activity_main);

    mPlaylists = new Playlists(this);

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

    BackgroundMgr.setCallbacks(this);
    BackgroundMgr.setDrawable((GradientDrawable)backgroundImage.getBackground());
  }

  //==============================================================================================
  // setupButtons
  //==============================================================================================
  /**
   * Set up the nav buttons
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
   * Set up the list view that shows the playlist items
   */
  private void setupListView() {
    PlaylistAdapter adapter = new PlaylistAdapter(this, mPlaylists.getPlaylistItems());
    ListView listView = (ListView) findViewById(R.id.list_playlists);
    listView.setAdapter(adapter);

    final Activity activity = this;

    //set up a click listener on list view items
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        PlaylistItem playlistItem = (PlaylistItem)adapterView.getItemAtPosition(position);
        ActivityStarter.Playlist(activity, playlistItem);
      }
    });
  }
}
