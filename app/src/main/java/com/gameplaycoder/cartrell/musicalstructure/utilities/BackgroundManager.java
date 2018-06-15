package com.gameplaycoder.cartrell.musicalstructure.utilities;

import android.graphics.drawable.GradientDrawable;
import android.os.Handler;

/*
  Manages changes to a drawable that can be used to change its color.
 */

public class BackgroundManager {
  ////////////////////////////////////////////////////////////////////////////////////////////////
  // members
  ////////////////////////////////////////////////////////////////////////////////////////////////
  //the rate, in milliseconds, at which the drawable is updated
  private final int COLOR_CHANGE_RATE = 30 * 1000;

  //The target drawable
  private GradientDrawable mDrawable;

  //responsible for automating when the drawable is updated
  private Handler mTimerHandler;
  private Runnable mTimerRunnable;

  //calback to notify a listener each time the manager updates the drawable
  private IBackgroundManagerCallbacks mCallbacks;

  //the current colors of the drawable (2 colors for a gradient)
  private int[] mColors;

  //the available colors that the drawable will cycle through
  private int[][] mTargetColors;

  //the index that points to the current cycle with the target colors
  private int mTargetColorIndex;

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // public
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // ctor
  //==============================================================================================
  public BackgroundManager() {
    mTimerHandler = new Handler();

    //fill colors with dummy data
    mColors = new int[] { 0, 0 };

    //setup the cycle of gradient colors
    mTargetColors = new int[][] {
      //blue
      { 0xff000080, 0xff000020 },

      //blue-to-purple
      { 0xff200080, 0xff080020 },
      { 0xff400080, 0xff100020 },
      { 0xff600080, 0xff180020 },

      //purple
      { 0xff800080, 0xff200020 },

      //purple-to-red
      { 0xff800060, 0xff200018 },
      { 0xff800040, 0xff200010 },
      { 0xff800020, 0xff200008 },

      //red
      { 0xff800000, 0xff200000 },

      //red-to-yellow
      { 0xff802000, 0xff200800 },
      { 0xff804000, 0xff201000 },
      { 0xff806000, 0xff201800 },

      //yellow
      { 0xff808000, 0xff202000 },

      //yellow-to-green
      { 0xff608000, 0xff182000 },
      { 0xff408000, 0xff102000 },
      { 0xff208000, 0xff082000 },

      //green
      { 0xff008000, 0xff002000 },

      //green-to-cyan
      { 0xff008020, 0xff002008 },
      { 0xff008040, 0xff002010 },
      { 0xff008060, 0xff002018 },

      //cyan
      { 0xff008080, 0xff002020 },

      //cyan-to-blue
      { 0xff006080, 0xff001820 },
      { 0xff004080, 0xff001020 },
      { 0xff002080, 0xff000820 }
    };

    initTimer();
  }

  //==============================================================================================
  // setCallbacks
  //==============================================================================================
  /**
   * Sets or clears the object that receives notification when this manager has updated the drawable.
   * @param value The object that will receive the notifications
   */
  public void setCallbacks(IBackgroundManagerCallbacks value) {
    mCallbacks = value;
  }

  //==============================================================================================
  // setDrawable
  //==============================================================================================
  /**
   * Sets the gradient drawable whose properties will be updated by this manager.
   * @param value The target gradient drawable
   */
  public void setDrawable(GradientDrawable value) {
    mTimerHandler.removeCallbacks(mTimerRunnable);

    mDrawable = value;
    if (mDrawable == null) {
      return;
    }

    //Ensure that this drawable owns its own properties
    mDrawable.mutate();
    updateDrawable();
    mTimerHandler.postDelayed(mTimerRunnable, COLOR_CHANGE_RATE);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // private
  ////////////////////////////////////////////////////////////////////////////////////////////////

  //==============================================================================================
  // initTimer
  //==============================================================================================
  /**
   * Sets up the timer and its run callback
   */
  private void initTimer() {

    mTimerRunnable = new Runnable() {
      @Override
      public void run() {
        setNextTargetColorIndex();
        updateDrawable();

        //notify the listener via the callback
        if (mCallbacks != null) {
          mCallbacks.backgroundManagerOnUpdate(mDrawable);
        }

        //start the timer again
        mTimerHandler.postDelayed(mTimerRunnable, COLOR_CHANGE_RATE);
      }
    };
  }

  //==============================================================================================
  // setNextTargetColorIndex
  //==============================================================================================
  /**
   * Goes to teh next cycle in the list of target colors. If the end is reached
   * start the cycle over.
   */
  private void setNextTargetColorIndex() {
    mTargetColorIndex = (mTargetColorIndex + 1) % mTargetColors.length;
  }

  //==============================================================================================
  // updateDrawable
  //==============================================================================================
  /**
   * Updates the drawable with the current gradient colors.
   */
  private void updateDrawable() {
    //assign the new colors to the drawable
    int[] targetColors = mTargetColors[mTargetColorIndex];
    mColors[0] = targetColors[0];
    mColors[1] = targetColors[1];
    mDrawable.setColors(mColors);
  }
}