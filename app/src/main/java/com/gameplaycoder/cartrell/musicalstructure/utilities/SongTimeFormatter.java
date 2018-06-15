package com.gameplaycoder.cartrell.musicalstructure.utilities;

import java.util.Locale;

public final class SongTimeFormatter {
  ////////////////////////////////////////////////////////////////////////////////////////////////
  // public
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // Format
  //==============================================================================================
  /**
   * Returns the time in a h:m:s format.
   * @param timeInSeconds The time, in seconds, to format.
   * @return - The formatted time.
   */
  public static String Format(int timeInSeconds) {
    String format = "";

    int hours = timeInSeconds / 3600;
    if (hours > 0) {
      format += String.valueOf(hours) + ":";
    }

    String msFormat = "%1$02d:%2$02d";
    int minutes = (timeInSeconds / 60) % 60;
    int seconds = timeInSeconds % 60;
    format += String.format(Locale.getDefault(), msFormat, minutes, seconds);

    return (format);
  }
}
