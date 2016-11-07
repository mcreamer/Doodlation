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
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

/**
 * Created by MarkCreamer on 11/1/16.
 */
public class DrawingCanvas extends View {
    private ArrayList<Action> actions;
    private ArrayList<Action> redoArray;

    public static final int MIN_SIZE = 5, MAX_SIZE = 200;

    private Paint curPaint;
    private String drawingMode;
    private boolean transform;

    public DrawingCanvas(Context context, AttributeSet attrs) {
        super(context,attrs);

        actions = new ArrayList<>();
        redoArray = new ArrayList<>();

        curPaint = new Paint();
        curPaint.setColor(Color.RED);
        curPaint.setStrokeWidth(10);
        curPaint.setTextSize(10);
        curPaint.setStrokeCap(Paint.Cap.ROUND);

        drawingMode = "Brush";
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the actions
        for(Action action: actions) {
            action.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        Action lastAction;

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Action newAction = null;
                if(drawingMode.equals("Brush")) {
                    DoodlePath newPath = new DoodlePath(new Paint(curPaint));

                    Point start = new Point((int)x,(int)y);
                    newPath.addPoint(start);

                    newAction = newPath;
                }
                else if(drawingMode.equals("Line")) {
                    DoodleLine newLine = new DoodleLine(new Paint(curPaint));

                    newLine.setStart((int)x,(int)y);
                    newLine.setEnd((int)x,(int)y);

                    newAction = newLine;
                }
                else if(drawingMode.equals("Text")) {
                    DoodleText newText = new DoodleText(new Paint(curPaint));

                    newText.setPosition((int)x,(int)y);
                    newText.setText("Sample Text");

                    newAction = newText;
                }
                else if(drawingMode.equals("Rectangle")) {
                    DoodleRect newRect = new DoodleRect(new Paint(curPaint));

                    newRect.setStart((int)x,(int)y);

                    newAction = newRect;
                }
                else if(drawingMode.equals("Oval")) {
                    DoodleOval newOval = new DoodleOval(new Paint(curPaint));

                    newOval.setStart((int)x,(int)y);

                    newAction = newOval;
                }

                actions.add(newAction);
                break;
            case MotionEvent.ACTION_MOVE:
                lastAction = actions.get(actions.size()-1);

                lastAction.handleOnMove(x,y);

                break;
            case MotionEvent.ACTION_UP:
                lastAction = actions.get(actions.size()-1);

                lastAction.handleOnUp(x,y);
                break;
        }

        invalidate();
        return true;
    }

    // Assumes newMode is a valid mode
    public void setMode(String newMode) {
        drawingMode = newMode;
    }

    // Undoes the previous action
    // Returns true if the undo button should be enabled
    public void undo() {
        // if there are no actions, return
        if(actions.size() < 1)
            return;

        // remove the last action
        redoArray.add(actions.remove(actions.size()-1));

        invalidate();
    }

    // Redoes the previous action
    // Returns true if the redo button should be enabled
    public void redo() {
        // if there are no redos, return
        if(redoArray.size() < 1)
            return;

        // redo the last action
        actions.add(redoArray.remove(redoArray.size()-1));

        invalidate();
    }

    public void clear() {
        // clear the entire actions array
        actions.clear();
        redoArray.clear();

        invalidate();
    }

    public void toggleTransform() {
        transform = !transform;

        invalidate();
    }

    public Point transform(Point input) {
        int newX = input.x;
        int newY = input.y;

        // center
        newX -= getWidth()/2;
        newY -= getHeight()/2;

        newX += (newX-20)^2;
        newY += (newY-20)^2;

        // decenter
        newX += getWidth()/2;
        newY += getHeight()/2;

        return new Point(newX,newY);
    }

    public Paint getPaint() {
        return new Paint(curPaint);
    }

    public void setPaint(Paint inputPaint) {
        curPaint = inputPaint;
    }
}
