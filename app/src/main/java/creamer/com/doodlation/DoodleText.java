package creamer.com.doodlation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by MarkCreamer on 11/7/16.
 */
public class DoodleText extends Action {
    private Paint paint;

    private Point location;
    private String text;

    public DoodleText(Paint paint) {
        this.paint = paint;
        paint.setStyle(Paint.Style.FILL);
        location = new Point();
    }

    @Override
    public void draw(Canvas canvas, boolean transform) {
        if(!transform) {
            canvas.drawText(text, location.x, location.y, paint);
        }
        else {
            Point altered = DrawingCanvas.transform(location);
            canvas.drawText(text, altered.x, altered.y, paint);
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void handleOnDown(float x, float y) {
        location.set((int)x,(int)y);
        this.text = "Sample Text";
    }

    @Override
    public void handleOnMove(float x, float y) {
        location.set((int)x,(int)y);
    }

    @Override
    public void handleOnUp(float x, float y) {

    }
}
