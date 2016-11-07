package creamer.com.doodlation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by MarkCreamer on 11/7/16.
 */
public class DoodleText extends Action {
    private Paint paint;

    private Point location;
    private String text;

    public DoodleText(Paint paint) {
        this.paint = paint;
        location = new Point();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(text,location.x,location.y,paint);
    }

    @Override
    public void handleOnMove(float x, float y) {
        location.set((int)x,(int)y);
    }

    @Override
    public void handleOnUp(float x, float y) {
        // Open up a keyboard

    }

    public void setPosition(int x, int y) {
        location.set(x,y);
    }

    public void setText(String text) {
        this.text = text;
    }
}
