package com.gameplaycoder.cartrell.musicalstructure.utilities;

import android.app.Activity;
import android.content.Intent;

import com.gameplaycoder.cartrell.musicalstructure.MainActivity;
import com.gameplaycoder.cartrell.musicalstructure.data.PlaylistItem;
import com.gameplaycoder.cartrell.musicalstructure.data.SongItem;
import com.gameplaycoder.cartrell.musicalstructure.subActivities.ActiveQueueActivity;
import com.gameplaycoder.cartrell.musicalstructure.subActivities.NowPlayingActivity;
import com.gameplaycoder.cartrell.musicalstructure.subActivities.PlaylistActivity;

import java.util.ArrayList;

/*
  All the code that starts the activities are in this class.
 */
public final class ActivityStarter {
  ////////////////////////////////////////////////////////////////////////////////////////////////
  // public
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // ActiveQueue
  //==============================================================================================
  /**
   * Starts the active queue activity.
   * @param activity The current activity from which this activity is being started.
   */
  public static void ActiveQueue(Activity activity) {
    Intent intent = new Intent(activity, ActiveQueueActivity.class);
    activity.startActivity(intent);
  }

  //==============================================================================================
  // Playlist
  //==============================================================================================
  /**
   * Starts the playlist activity.
   * @param activity The current activity from which this activity is being started.
   * @param playlistItem The playlist whose information is being displayed in this activity.
   * The format of the playlist data that is passed to this activity within the intent:
   - name: The name of the playlist
   - numSongs: The number of songs in the playlist

     For each song in the playlist:
   - songNameX - The name of a song, where X is the position of the song within the playlist.
     (for example, songName0, songName1, songName2, and so on)
   - songLengthX - The length of a song, where X is position of the song within the playlist.
   */
  public static void Playlist(Activity activity, PlaylistItem playlistItem) {
    /*
     */
    Intent intent = new Intent(activity, PlaylistActivity.class);
    intent.putExtra("name", playlistItem.getName());

    ArrayList<SongItem> songs = playlistItem.getSongs();
    int numSongs = songs.size();
    intent.putExtra("numSongs", numSongs);

    for (int songIndex = 0; songIndex < numSongs; songIndex++) {
      SongItem songItem = songs.get(songIndex);

      String propName = "songName" + String.valueOf(songIndex);
      intent.putExtra(propName, songItem.getName());

      String propLength = "songLength" + String.valueOf(songIndex);
      intent.putExtra(propLength, songItem.getLength());
    }

    activity.startActivity(intent);
  }

  //==============================================================================================
  // Playlists
  //==============================================================================================
  /**
   * Starts the playlists activity.
   * @param activity The current activity from which this activity is being started.
   */
  public static void Playlists(Activity activity) {
    Intent intent = new Intent(activity, MainActivity.class);
    activity.startActivity(intent);
  }

  //==============================================================================================
  // NowPlaying
  //==============================================================================================
  /**
   * Starts the now playing activity.
   * @param activity The current activity from which this activity is being started.
   */
  public static void NowPlaying(Activity activity) {
    Intent intent = new Intent(activity, NowPlayingActivity.class);
    activity.startActivity(intent);
  }
}
