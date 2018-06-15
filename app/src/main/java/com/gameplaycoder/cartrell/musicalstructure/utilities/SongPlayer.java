package com.gameplaycoder.cartrell.musicalstructure.utilities;

import android.os.Handler;

import com.gameplaycoder.cartrell.musicalstructure.data.SongItem;

/*
  Resposible for "playing" songs and progress updates (songs don't actually play). When the current
   song is finished, the player sends a notification that it is done
 */
public final class SongPlayer {
  ////////////////////////////////////////////////////////////////////////////////////////////////
  // members
  ////////////////////////////////////////////////////////////////////////////////////////////////
  //one second, in milliseconds, that the sound player timer should update
  private final int TICK_DELAY = 1000;

  //object that will receive notifications from this player
  private ISongPlayerCallbacks mCallbacks;

  //responsible for handling the timer
  private Handler mTimerHandler;
  private Runnable mTimerRunnable;

  //reference to the current song playing
  private SongItem mSongItem;

  //number of seconds remaining
  private int mSongSecondsRemaining;

  //determines if the player is running; in other words, if a song is setup for play
  private boolean mIsRunning;

  //determines if the player is paused; this is only applicable if the player is already running
  private boolean mIsPaused;

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // public
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // ctor
  //==============================================================================================
  public SongPlayer() {
    mTimerHandler = new Handler();
    initTimer();
  }

  //==============================================================================================
  // getCallbacks
  //==============================================================================================
  public ISongPlayerCallbacks getCallbacks() {
    return(mCallbacks);
  }

  //==============================================================================================
  // getPaused
  //==============================================================================================
  public boolean getPaused() {
    return(mIsPaused);
  }

  //==============================================================================================
  // getSecondsRemaining
  //==============================================================================================
  public int getSecondsRemaining() {
    return(mSongSecondsRemaining);
  }

  //==============================================================================================
  // getSongItem
  //==============================================================================================
  public SongItem getSongItem() {
    return(mSongItem);
  }

  //==============================================================================================
  // isRunning
  //==============================================================================================
  public boolean isRunning() {
    return(mIsRunning);
  }

  //==============================================================================================
  // setPaused
  //==============================================================================================
  public void setPaused(boolean value) {
    if (!mIsRunning) {
      //cant set paused state if no song is playing
      return;
    }

    mIsPaused = value;

    if (mIsPaused) {
      mTimerHandler.removeCallbacks(mTimerRunnable);
    } else {
      mTimerHandler.post(mTimerRunnable);
    }
  }

  //==============================================================================================
  // play
  //==============================================================================================
  public void play(SongItem songItem) {
    stop();

    mSongItem = songItem;
    if (mSongItem == null) {
      return;
    }

    mIsRunning = true;
    mIsPaused = false;
    mSongSecondsRemaining = mSongItem.getLength();
    mTimerHandler.postDelayed(mTimerRunnable, TICK_DELAY);
  }

  //==============================================================================================
  // setCallbacks
  //==============================================================================================
  public void setCallbacks(ISongPlayerCallbacks value) {
    mCallbacks = value;
  }

  //==============================================================================================
  // stop
  //==============================================================================================
  public void stop() {
    mSongItem = null;
    mIsPaused = false;
    mIsRunning = false;
    mTimerHandler.removeCallbacks(mTimerRunnable);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // private
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // initTimer
  //==============================================================================================
  private void initTimer() {
    final SongPlayer songPlayer = this;

    mTimerRunnable = new Runnable() {
      @Override
      public void run() {
        if (mSongSecondsRemaining > 0) {
          mSongSecondsRemaining--;
          mTimerHandler.postDelayed(mTimerRunnable, TICK_DELAY);
        } else {
          songPlayer.stop();
        }

        if (mCallbacks != null) {
          mCallbacks.songPlayerOnTimer(songPlayer);
        }
      }
    };
  }
}
