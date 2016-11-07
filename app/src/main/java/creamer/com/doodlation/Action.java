package creamer.com.doodlation;

import android.graphics.Canvas;

/**
 * Created by MarkCreamer on 11/7/16.
 */
public abstract class Action {
     public abstract void draw(Canvas canvas);

     public abstract void handleOnMove(float x, float y);

     public abstract void handleOnUp(float x, float y);
}
