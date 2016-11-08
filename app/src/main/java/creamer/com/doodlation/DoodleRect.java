package creamer.com.doodlation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by MarkCreamer on 11/7/16.
 */
public class DoodleRect extends Action {
    private Paint paint;
    private Point start, end;

    public DoodleRect(Paint paint) {
        this.paint = paint;
        start = new Point();
        end = new Point();
    }

    @Override
    public void draw(Canvas canvas, boolean transform) {
        canvas.drawRect(start.x,start.y,end.x,end.y,paint);
    }

    @Override
    public void handleOnDown(float x, float y) {
        start.set((int)x,(int)y);
    }

    @Override
    public void handleOnMove(float x, float y) {
        end.set((int)x,(int)y);
    }

    @Override
    public void handleOnUp(float x, float y) {

    }
}
