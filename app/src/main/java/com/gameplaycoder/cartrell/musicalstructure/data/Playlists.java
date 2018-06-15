package com.gameplaycoder.cartrell.musicalstructure.data;

import android.content.Context;
import android.content.res.Resources;

import com.gameplaycoder.cartrell.musicalstructure.R;
import com.gameplaycoder.cartrell.musicalstructure.data.PlaylistItem;
import com.gameplaycoder.cartrell.musicalstructure.data.SongItem;

import java.util.ArrayList;

public class Playlists {
  ////////////////////////////////////////////////////////////////////////////////////////////////
  // members
  ////////////////////////////////////////////////////////////////////////////////////////////////
  //An array of all the playlists used.
  private ArrayList<PlaylistItem> mPlaylistItems;

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // public
  ////////////////////////////////////////////////////////////////////////////////////////////////
  public Playlists(Context context) {
    mPlaylistItems = new ArrayList<>();
    createDefaultPlaylists(context);
  }

  //==============================================================================================
  // getPlaylistItems
  //==============================================================================================
  /**
   * Gets all the playlists.
   * @return The array list of PlaylistItem objects.
   */
  public ArrayList<PlaylistItem>getPlaylistItems() {
    return(mPlaylistItems);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // private
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // createDefaultPlaylists
  //==============================================================================================
  /**
   * Creates the default playlists
   */
  private void createDefaultPlaylists(Context context) {
    createPlaylistSonic(context);
    createPlaylistMario(context);
    createPlaylistMetroid(context);
  }

  //==============================================================================================
  // createPlaylistMario
  //==============================================================================================
  /**
   * Creates the default playlist for the super mario series songs
   */
  private void createPlaylistMario(Context context) {
    PlaylistItem playlistItem = new PlaylistItem(context.getString(R.string.super_mario_series));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.smb_theme), 125));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sm_underground), 150));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sm_bowsers_castle), 180));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sm_desert), 154));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.smb2_theme), 72));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sm_defeated), 5));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sm_starman), 20));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.smb2_boss), 111));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.smb2_wart), 105));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.smb1_another_castle), 25));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.smb3_theme1), 150));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.smb3_theme2), 180));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.smb3_airship), 154));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.smb3_bowser), 72));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.smw_castle), 5));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.smw_bowser_valley), 53));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.smb2_polar_bear), 111));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.smw_game_over), 5));
    mPlaylistItems.add(playlistItem);
  }

  //==============================================================================================
  // createPlaylistMetroid
  //==============================================================================================
  /**
   * Creates the default playlist for the metroid series songs
   */
  private void createPlaylistMetroid(Context context) {
    PlaylistItem playlistItem = new PlaylistItem(context.getString(R.string.metroid_series));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.metroid_intro), 100));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.metroid_energize), 299));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.metroid_secret), 60));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.metroid_collect_item), 6));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.metroid_brinstar), 114));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.metroid_crateria), 120));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.metroid_kraid), 70));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.metroid_norfair), 81));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.metroid_ridley), 120));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.metroid_tourian), 40));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.metroid_mother_brain), 59));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.metroid_escape), 72));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.metroid_super_long_ending), 3701));
    mPlaylistItems.add(playlistItem);
  }

  //==============================================================================================
  // createPlaylistSonic
  //==============================================================================================
  /**
   * Creates the default playlist for the sonic series songs
   */
  private void createPlaylistSonic(Context context) {
    Resources resources = context.getResources();
    PlaylistItem playlistItem = new PlaylistItem(context.getString(R.string.sonic_series));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_theme), 252));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_green_hill), 105));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_scrap_brain), 80));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_star_light), 218));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_oil_ocean), 29));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_labryinth), 76));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_chemical_plant), 81));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_mystic_cave), 113));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_metropolis), 110));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_chaos_emerald), 5));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_mushroom_hill), 120));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_sky_sanctuary), 45));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_flying_battery), 72));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_hydrocity), 80));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_carnival_night), 95));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_launch_base), 119));
    playlistItem.getSongs().add(new SongItem(context.getString(R.string.sonic_doomsady), 90));
    mPlaylistItems.add(playlistItem);
  }
}
