package com.gameplaycoder.cartrell.musicalstructure.subActivities;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gameplaycoder.cartrell.musicalstructure.utilities.ActivityStarter;
import com.gameplaycoder.cartrell.musicalstructure.utilities.IBackgroundManagerCallbacks;
import com.gameplaycoder.cartrell.musicalstructure.utilities.ISongPlayerCallbacks;
import com.gameplaycoder.cartrell.musicalstructure.MainActivity;
import com.gameplaycoder.cartrell.musicalstructure.R;
import com.gameplaycoder.cartrell.musicalstructure.data.SongItem;
import com.gameplaycoder.cartrell.musicalstructure.utilities.SongPlayer;
import com.gameplaycoder.cartrell.musicalstructure.utilities.SongTimeFormatter;

public class NowPlayingActivity extends AppCompatActivity implements ISongPlayerCallbacks,
  IBackgroundManagerCallbacks {

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // members
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //reference to the song player
  private SongPlayer mSongPlayer;

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

  //==============================================================================================
  // songPlayerOnTimer
  //==============================================================================================
  /**
   * The song player has updated its song playback timer.
   * @param songPlayer The song player sending the update
   */
  @Override
  public void songPlayerOnTimer(SongPlayer songPlayer) {
    int secondsRemaining = songPlayer.getSecondsRemaining();
    if (secondsRemaining == 0) {
      playNextSong();
    } else {
      setTimeText(secondsRemaining);
    }
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
    setContentView(R.layout.activity_now_playing);
    setupButtons();
    setupPlayButtons();

    mSongPlayer = MainActivity.ActiveSongPlayer;
    mSongPlayer.setCallbacks(this);

    presentCurrentSong();
  }

  //==============================================================================================
  // onPostResume
  //==============================================================================================
  @Override
  protected void onPostResume() {
    super.onPostResume();
    setupBackground();

    if (mSongPlayer.isRunning()) {
      setTimeText(mSongPlayer.getSecondsRemaining());
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // private
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // playNextSong
  //==============================================================================================
  /**
   * Advances to the next song, if one is available.
   */
  private void playNextSong() {
    if (MainActivity.ActiveQueueSongs.next()) {
      if (mSongPlayer.getPaused()) {
        presentCurrentSong();
      } else {
        playSong();
      }
    }
  }

  //==============================================================================================
  // playPreviousSong
  //==============================================================================================
  /**
   * Advances to the previous song, if one is available.
   */
  private void playPreviousSong() {
    if (MainActivity.ActiveQueueSongs.prev()) {
      if (mSongPlayer.getPaused()) {
        presentCurrentSong();
      } else {
        playSong();
      }
    }
  }

  //==============================================================================================
  // playSong
  //==============================================================================================
  /**
   * Plays the current song. If the song was playing, it will start over. It the song was paused,
   * it will resume.
   */
  private void playSong() {
    if (mSongPlayer.getPaused()) {
      mSongPlayer.setPaused(false);
    } else {
      SongItem songItem = MainActivity.ActiveQueueSongs.getCurrentSong();
      mSongPlayer.play(songItem);
      presentCurrentSong();
    }
  }

  //==============================================================================================
  // presentCurrentSong
  //==============================================================================================
  /**
   * Displays information about the current song.
   */
  private void presentCurrentSong() {
    SongItem songItem = mSongPlayer.getSongItem();
    if (songItem == null) {
      songItem = MainActivity.ActiveQueueSongs.getCurrentSong();
    }

    TextView textView;

    if (songItem != null) {
      //a song item is available, so displays its name and time
      textView = findViewById(R.id.txtSongName);
      textView.setText(songItem.getName());

      textView = findViewById(R.id.txtSongTime);
      textView.setText(songItem.toTimeFormat());

      findViewById(R.id.layout_playButtons).setVisibility(View.VISIBLE);
    } else {
      //no song item is available, so indicate that no songs are available
      textView = findViewById(R.id.txtSongName);
      textView.setText(R.string.no_songs_active);

      textView = findViewById(R.id.txtSongTime);
      textView.setText("");

      findViewById(R.id.layout_playButtons).setVisibility(View.INVISIBLE);
    }
  }

  //==============================================================================================
  // setTimeText
  //==============================================================================================
  /**
   * Displays the time on the view
   * @param timeInSeconds - The time, in seconds, to convert to h:mm:ss time
   */
  private void setTimeText(int timeInSeconds) {
    String timeFormat = SongTimeFormatter.Format(timeInSeconds);
    TextView textView = findViewById(R.id.txtSongTime);
    textView.setText(timeFormat);
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
   * Set up the nav buttons.
   */
  private void setupButtons() {
    final Activity activity = this;

    Button button = findViewById(R.id.btnPlaylists);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ActivityStarter.Playlists(activity);
      }
    });

    button = findViewById(R.id.btnActiveQueue);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ActivityStarter.ActiveQueue(activity);
      }
    });
  }

  //==============================================================================================
  // setupPlayButtons
  //==============================================================================================
  /**
   * Set up the play control buttons.
   */
  private void setupPlayButtons() {
    Button button = findViewById(R.id.btnPrevious);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        playPreviousSong();
      }
    });

    button = findViewById(R.id.btnPlay);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        playSong();
      }
    });

    button = findViewById(R.id.btnPause);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mSongPlayer.setPaused(true);
      }
    });

    button = findViewById(R.id.btnNext);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        playNextSong();
      }
    });
  }
}