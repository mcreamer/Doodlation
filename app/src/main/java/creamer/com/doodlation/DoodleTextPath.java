package creamer.com.doodlation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by MarkCreamer on 11/8/16.
 */
public class DoodleTextPath extends Action {
    private ArrayList<Point> path;
    private String text;
    private Paint paint;

    public DoodleTextPath(Paint paint) {
        path = new ArrayList<>();
        text = "Sample Text";
        this.paint = paint;
        paint.setStrokeWidth(10);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw(Canvas canvas, boolean transform) {
        if(path.isEmpty())
            return;

        Path displayPath = new Path();

        if(transform) {
            displayPath.moveTo(path.get(0).x, path.get(0).y);
            for (int i = 1; i < path.size(); i++) {
                Point p = path.get(i);

                displayPath.lineTo(p.x, p.y);
            }
        }
        else {
            Point altered = DrawingCanvas.transform(path.get(0));
            displayPath.moveTo(altered.x, altered.y);
            for (int i = 1; i < path.size(); i++) {
                Point p = path.get(i);

                altered = DrawingCanvas.transform(p);

                displayPath.lineTo(altered.x, altered.y);
            }
        }
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(displayPath,paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath(text,displayPath,0,0,paint);
    }

    @Override
    public void handleOnDown(float x, float y) {
        path.add(new Point((int)x,(int)y));
    }

    @Override
    public void handleOnMove(float x, float y) {
        path.add(new Point((int)x,(int)y));
    }

    @Override
    public void handleOnUp(float x, float y) {

    }
}
