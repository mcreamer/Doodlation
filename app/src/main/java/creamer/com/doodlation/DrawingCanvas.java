package creamer.com.doodlation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by MarkCreamer on 11/1/16.
 */
public class DrawingCanvas extends View {
    private ArrayList<Action> actions;
    private ArrayList<Action> redoArray;

    public static final int MIN_SIZE = 10, MAX_SIZE = 200;

    public static int width, height;

    private Paint curPaint;
    private String drawingMode;
    private boolean transform;

    public DrawingCanvas(Context context, AttributeSet attrs) {
        super(context,attrs);

        actions = new ArrayList<>();
        redoArray = new ArrayList<>();

        curPaint = new Paint();
        curPaint.setColor(Color.RED);
        curPaint.setStrokeWidth((MAX_SIZE+MIN_SIZE)/2);
        curPaint.setTextSize((MAX_SIZE+MIN_SIZE)/2);
        curPaint.setStrokeCap(Paint.Cap.ROUND);
        curPaint.setStyle(Paint.Style.STROKE);

        drawingMode = "Brush";

        width = getWidth();
        height = getHeight();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the actions
        for(Action action: actions) {
            action.draw(canvas,transform);
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
                    newAction = new DoodlePath(new Paint(curPaint));
                }
                else if(drawingMode.equals("Line")) {
                    newAction = new DoodleLine(new Paint(curPaint));
                }
                else if(drawingMode.equals("Text")) {
                    newAction = new DoodleText(new Paint(curPaint));
                }
                else if(drawingMode.equals("TextPath")) {
                    newAction = new DoodleTextPath(new Paint(curPaint));
                }
                else if(drawingMode.equals("Rectangle")) {
                    newAction = new DoodleRect(new Paint(curPaint));
                }
                else if(drawingMode.equals("Oval")) {
                    newAction = new DoodleCircle(new Paint(curPaint));
                }

                newAction.handleOnDown(x,y);
                actions.add(newAction);

                redoArray.clear();
                break;
            case MotionEvent.ACTION_MOVE:
                lastAction = actions.get(actions.size()-1);

                lastAction.handleOnMove(x,y);

                break;
            case MotionEvent.ACTION_UP:
                lastAction = actions.get(actions.size()-1);

                lastAction.handleOnUp(x,y);

                if(drawingMode.equals("Text")) {
                    final DoodleText lastText = (DoodleText) lastAction;
                    // Display a text input popup
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Enter Text");

                    // Set up the input
                    final EditText input = new EditText(getContext());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            lastText.setText(input.getText().toString());

                            invalidate();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            actions.remove(actions.size()-1);
                            invalidate();
                        }
                    });

                    builder.show();
                }
                else if(drawingMode.equals("TextPath")) {
                    final DoodleTextPath lastTextPath = (DoodleTextPath) lastAction;
                    // Display a text input popup
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Enter Text");

                    // Set up the input
                    final EditText input = new EditText(getContext());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            lastTextPath.setText(input.getText().toString());

                            invalidate();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            actions.remove(actions.size()-1);
                            invalidate();
                        }
                    });

                    builder.show();
                }
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
    public void undo() {
        // if there are no actions, return
        if(actions.size() < 1)
            return;

        // remove the last action
        redoArray.add(actions.remove(actions.size()-1));

        invalidate();
    }

    // Redoes the previous action
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

    public static Point transform(Point input) {
        int newX = input.x;
        int newY = input.y;

        // center
        newX -= width/2;
        newY -= height/2;

        newX += 40;
        newY += 40;

        // decenter
        newX += width/2;
        newY += height/2;

        return new Point(newX,newY);
    }

    public Paint getPaint() {
        return new Paint(curPaint);
    }

    public void setPaint(Paint inputPaint) {
        curPaint = inputPaint;
    }
}
