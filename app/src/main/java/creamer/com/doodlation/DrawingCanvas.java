package creamer.com.doodlation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by MarkCreamer on 11/1/16.
 */
public class DrawingCanvas extends View {
    private float x,y;
    private ArrayList<ArrayList<Point>> paths;
    private ArrayList<Paint> paints;

    private Paint curPaint;

    private String drawingMode;

    public static final int MIN_SIZE = 5, MAX_SIZE = 200;

    public DrawingCanvas(Context context, AttributeSet attrs) {
        super(context,attrs);

        paths = new ArrayList<>();
        paints = new ArrayList<>();
        curPaint = new Paint();
        curPaint.setColor(Color.RED);
        curPaint.setStrokeWidth(10);
        curPaint.setStrokeCap(Paint.Cap.ROUND);

        drawingMode = "Brush";
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the paths
        for(int i = 0; i < paths.size(); i++) {
            ArrayList<Point> path = paths.get(i);
            Paint paint = paints.get(i);

            if(path.size() >= 1) {
                canvas.drawCircle(path.get(0).x, path.get(0).y, paint.getStrokeWidth() / 2, paint);
            }
            for (int j = 0; j < path.size() - 1; j++) {
                Point p1 = path.get(j);
                Point p2 = path.get(j + 1);

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
                paints.add(new Paint(curPaint));

                ArrayList<Point> newPath = new ArrayList<Point>();
                Point start = new Point((int) x, (int) y);
                newPath.add(start);

                if(drawingMode.equals("Line")) {    // Create the two points
                    newPath.add(new Point((int) x, (int) y));
                }

                paths.add(newPath);
                break;
            case MotionEvent.ACTION_MOVE:
                ArrayList<Point> lastPath = paths.get(paths.size()-1);

                if(drawingMode.equals("Brush")) {
                    lastPath.add(new Point((int) x, (int) y));
                }
                else if(drawingMode.equals("Line")) {
                    lastPath.get(lastPath.size()-1).set((int)x, (int) y);
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
        // if there are no paths, return
        if(paths.size() < 1) {
            return;
        }

        // remove the last path
        paths.remove(paths.size()-1);
        paints.remove(paints.size()-1);

        invalidate();
    }

    public void clear() {
        // clear the entire paths array
        paths.clear();
        paints.clear();

        invalidate();
    }

    public Paint getPaint() {
        return curPaint;
    }

    public void setPaint(Paint inputPaint) {
        curPaint = inputPaint;
    }
}
