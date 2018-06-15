package com.gameplaycoder.cartrell.musicalstructure.data;

import java.util.ArrayList;

public class PlaylistItem {
  //All the songs contained in this playlist
  private ArrayList<SongItem>mSongItems;

  //The name of the playlist
  private String mName;

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // public
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // ctor
  //==============================================================================================
  /**
   * Data for a playlist of songs.
   * @param name The name of the playlist.
   */
  public PlaylistItem(String name) {
    mName = name;
    mSongItems = new ArrayList<>();
  }

  //==============================================================================================
  // getName
  //==============================================================================================
  /**
   * Returns the name of the playlist.
   * @return - The playlist name.
   */
  public String getName() {
    return(mName);
  }

  //==============================================================================================
  // getSongs
  //==============================================================================================
  /**
   * Gets all the songs in the playlist
   * @return The array of song items
   */
  public ArrayList<SongItem>getSongs() {
    return(mSongItems);
  }
}
