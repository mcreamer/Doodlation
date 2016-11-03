package creamer.com.doodlation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by MarkCreamer on 11/1/16.
 */
public class DrawingCanvas extends View {
    private float x,y;
    private ArrayList<ArrayList<Point>> paths;
    private Paint paint;
    private int width;
    private String drawingMode;

    public DrawingCanvas(Context context, AttributeSet attrs) {
        super(context,attrs);

        width = 10;

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(width);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paths = new ArrayList<>();

        drawingMode = "Brush";
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the paths
        for(ArrayList<Point> path: paths) {
            for (int i = 0; i < path.size() - 1; i++) {
                Point p1 = path.get(i);
                Point p2 = path.get(i + 1);

                canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                paths.add(new ArrayList<Point>());
            case MotionEvent.ACTION_MOVE:
                ArrayList<Point> lastPath = paths.get(paths.size() - 1);

                if(drawingMode.equals("Brush")) {
                    lastPath.add(new Point((int) x, (int) y));
                }
                else if(drawingMode.equals("Line")) {
                    if(lastPath.size() <= 1) {
                        lastPath.add(new Point((int) x, (int) y));
                    }
                    else {
                        lastPath.get(lastPath.size()-1).set((int)x, (int) y);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
        }

        invalidate();
        return true;
    }

    // Assumes newMode is a valid mode
    public void setMode(String newMode) {
        drawingMode = newMode;
    }

    // Undoes the previous stroke
    public void undo() {
        // remove the last path
        if(paths.size() >= 1)
            paths.remove(paths.size()-1);

        invalidate();
    }

    public void clear() {
        // clear the entire paths array
        paths.clear();

        invalidate();
    }
}
