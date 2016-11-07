package creamer.com.doodlation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by MarkCreamer on 11/7/16.
 */
public class DoodlePath extends Action {
    private ArrayList<Point> path;
    private Paint paint;

    public DoodlePath(Paint paint) {
        path = new ArrayList<Point>();
        this.paint = paint;
    }

    public void addPoint(Point newPoint) {
        path.add(newPoint);
    }

    @Override
    public void draw(Canvas canvas) {
        if(path.size() <= 0)
            return;

        canvas.drawCircle(path.get(0).x, path.get(0).y, paint.getStrokeWidth() / 2, paint);
        for (int j = 0; j < path.size() - 1; j++) {
            Point p1 = path.get(j);
            Point p2 = path.get(j + 1);

            canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);
        }
    }

    @Override
    public void handleOnMove(float x, float y) {
        path.add(new Point((int)x,(int)y));
    }

    @Override
    public void handleOnUp(float x, float y) {

    }
}
