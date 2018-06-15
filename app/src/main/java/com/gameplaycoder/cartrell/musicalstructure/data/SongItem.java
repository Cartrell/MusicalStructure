package com.gameplaycoder.cartrell.musicalstructure.data;

import com.gameplaycoder.cartrell.musicalstructure.utilities.SongTimeFormatter;

public class SongItem {

  //==============================================================================================
  // static
  //==============================================================================================
  private static int smNextId = 0;

  //==============================================================================================
  // members
  //==============================================================================================

  //name of the song
  private String mName;

  //length of the song in seconds
  private int mLength;

  //unique ID for each song
  private int mId;

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // public
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // ctor
  //==============================================================================================
  /**
   * Constructs a new song item
   * @param name - The name/title of the song
   * @param length - The length of the song, in seconds
   */
  public SongItem(String name, int length) {
    mId = ++smNextId;
    mName = name;
    mLength = length;
  }

  //==============================================================================================
  // getId
  //==============================================================================================
  /**
   * Returns the unique identifier of the song. Used for ui purposes.
   * @return - The ID.
   */
  public int getId() {
    return(mId);
  }

  //==============================================================================================
  // getLength
  //==============================================================================================
  /**
   * Returns the length, in seconds, of the song.
   * @return - The song length.
   */
  public int getLength() {
    return(mLength);
  }

  //==============================================================================================
  // getName
  //==============================================================================================
  /**
   * Returns the name of the song.
   * @return - The song name.
   */
  public String getName() {
    return(mName);
  }

  //==============================================================================================
  // toTimeFormat
  //==============================================================================================
  /**
   * Returns the song length in a h:m:s format.
   * @return - The formatted time.
   */
  public String toTimeFormat() {
    return(SongTimeFormatter.Format(mLength));
  }
}
