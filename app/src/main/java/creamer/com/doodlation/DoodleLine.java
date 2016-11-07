package creamer.com.doodlation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by MarkCreamer on 11/7/16.
 */
public class DoodleLine extends Action {
    private Paint paint;

    private Point start, end;

    public DoodleLine(Paint paint) {
        this.paint = paint;
        start = new Point();
        end = new Point();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(start.x,start.y,end.x,end.y,paint);
    }

    @Override
    public void handleOnMove(float x, float y) {
        end.set((int)x,(int)y);
    }

    @Override
    public void handleOnUp(float x, float y) {

    }

    public void setStart(int x, int y) {
        start.set(x,y);
    }

    public void setEnd(int x, int y) {
        end.set(x,y);
    }
}
