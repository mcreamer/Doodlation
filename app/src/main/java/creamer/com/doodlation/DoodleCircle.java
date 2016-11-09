package creamer.com.doodlation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by MarkCreamer on 11/7/16.
 */
public class DoodleCircle extends Action {
    private Paint paint;

    private Point start, end;

    public DoodleCircle(Paint paint) {
        this.paint = paint;
        start = new Point();
        end = new Point();
    }

    @Override
    public void draw(Canvas canvas, boolean transform) {
        if(!transform) {
            canvas.drawCircle(start.x + (end.x - start.x) / 2, start.y + (end.y - start.y) / 2, Math.max(end.x - start.x, end.y - start.y) / 2, paint);
        }
        else {
            Point altered = DrawingCanvas.transform(start.x + (end.x - start.x)/ 2, start.y + (end.y - start.y) / 2);
            canvas.drawCircle(altered.x, altered.y, Math.max(end.x - start.x, end.y - start.y) / 2, paint);
        }
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
