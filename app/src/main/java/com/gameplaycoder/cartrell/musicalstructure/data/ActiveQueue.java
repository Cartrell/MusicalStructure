package com.gameplaycoder.cartrell.musicalstructure.data;

import com.gameplaycoder.cartrell.musicalstructure.data.SongItem;

import java.util.ArrayList;

public class ActiveQueue {
  /*
    Manager for songs in the active queue ("now playing")
  */

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // members
  ////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Array of all songs currently in the active queue
   */
  private ArrayList<SongItem> mSongItems;
  private int mCurrentPlaying;

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // public
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // ctor
  //==============================================================================================
  public ActiveQueue() {
    mSongItems = new ArrayList<>();
  }

  //==============================================================================================
  // add
  //==============================================================================================
  /**
   * Adds a song to the active queue.
   * @param songItem The song item to add.
   */
  public void add(SongItem songItem) {
    if (songItem != null) {
      mSongItems.add(songItem);
    }
  }

  //==============================================================================================
  // getCurrentSong
  //==============================================================================================
  /**
   * Retrieves the song currently playing.
   * @return The song item or null if no songs are in the queue.
   */
  public SongItem getCurrentSong() {
    if (mSongItems.size() == 0) {
      return(null);
    }
    return(mSongItems.get(mCurrentPlaying));
  }

  //==============================================================================================
  // getSongs
  //==============================================================================================
  /**
   * Retrieves the array of all song items in the list.
   * @return The array list of all songs.
   */
  public ArrayList<SongItem> getSongs() {
    return(mSongItems);
  }

  //==============================================================================================
  // next
  //==============================================================================================
  /**
   * Advances to the next song to be active. If it's the last song, it won't advance.
   * @return True if the activity was able to advance to the next song. False if not.
   */
  public boolean next() {
    int nextIndex = mCurrentPlaying + 1;
    int maxIndex = mSongItems.size() - 1;
    if (nextIndex <= maxIndex) {
      //can advance to the next song; otherwise, would already be at the last song in the queue,
      // and can not advance
      mCurrentPlaying = nextIndex;
      return(true);
    }

    return(false);
  }

  //==============================================================================================
  // prev
  //==============================================================================================
  /**
   * Advances to the previous song to be active. If it's the first song, it won't advance.
   * @return True if the activity was able to advance to the previous song. False if not.
   */
  public boolean prev() {
    int nextIndex = mCurrentPlaying - 1;
    int minIndex = 0;
    if (nextIndex >= minIndex) {
      //can advance to the previous song; otherwise, would already be at the first song in the
      // queue, and can not advance
      mCurrentPlaying = nextIndex;
      return(true);
    }

    return(false);
  }

  /**
   * Removes a song from the active queue. The song preceding the song becomes the active one.
   * If the song remove was the last song, the penultimate song becomes the active one.
   * @param position The index of the song within the queue to remove
   */
  //==============================================================================================
  // removeAt
  //==============================================================================================
  public void removeAt(int position) {
    if (!isValidPosition(position)) {
      return;
    }

    mSongItems.remove(position);

    int numSongs = mSongItems.size();
    if (mCurrentPlaying >= numSongs) {
      setCurrentIndex(numSongs - 1);
    }
  }

  //==============================================================================================
  // setCurrentIndex
  //==============================================================================================
  /**
   * Sets the currently active song
   * @param position The index of the song within the queue to activate.
   */
  public void setCurrentIndex(int position) {
    if (mSongItems.size() == 0) {
      mCurrentPlaying = 0;
    } else if (isValidPosition(position)) {
      mCurrentPlaying = position;
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // private
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // isValidPosition
  //==============================================================================================
  /**
   * Determines if the specified position is valid within the queue (0 <= x < num songs).
   * @param position The position to check.
   * @return True if the position is valuel false otherwise.
   */
  private boolean isValidPosition(int position) {
    return(0 <= position && position < mSongItems.size());
  }
}
