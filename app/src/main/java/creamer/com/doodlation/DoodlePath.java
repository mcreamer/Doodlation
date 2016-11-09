package creamer.com.doodlation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by MarkCreamer on 11/7/16.
 */
public class DoodlePath extends Action {
    private ArrayList<Point> path;
    private Paint paint;

    public DoodlePath(Paint paint) {
        path = new ArrayList<>();
        this.paint = paint;
    }

    @Override
    public void draw(Canvas canvas, boolean transform) {
        if(path.isEmpty())
            return;

        Path displayPath = new Path();

        if(!transform) {
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

        canvas.drawPath(displayPath,paint);
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
