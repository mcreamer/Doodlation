package creamer.com.doodlation;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyDialogFragment.NoticeDialogListener {
    private DrawingCanvas dCanvas;
    private ImageButton brushButton;
    private ImageButton lineButton;
    private ImageButton textButton;
    private ImageButton rectButton;
    private ImageButton ovalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dCanvas = (DrawingCanvas)findViewById(R.id.canvas);
        brushButton = (ImageButton)findViewById(R.id.brush);
        dCanvas.setMode("Brush");
        brushButton.setEnabled(false);
        lineButton = (ImageButton)findViewById(R.id.line);
        lineButton.setEnabled(true);
        textButton = (ImageButton)findViewById(R.id.text);
        textButton.setEnabled(true);
        rectButton = (ImageButton)findViewById(R.id.rectangle);
        rectButton.setEnabled(true);
        ovalButton = (ImageButton)findViewById(R.id.oval);
        ovalButton.setEnabled(true);
    }

    protected void onBrush(View v) {
        dCanvas.setMode("Brush");
        brushButton.setEnabled(false);
        lineButton.setEnabled(true);
        textButton.setEnabled(true);
        rectButton.setEnabled(true);
        ovalButton.setEnabled(true);
    }

    protected void onLine(View v) {
        dCanvas.setMode("Line");
        brushButton.setEnabled(true);
        lineButton.setEnabled(false);
        textButton.setEnabled(true);
        rectButton.setEnabled(true);
        ovalButton.setEnabled(true);
    }

    protected void onUndo(View v) {
        dCanvas.undo();
    }

    protected void onRedo(View v) {
        dCanvas.redo();
    }

    protected void onClear(View v) {
        dCanvas.clear();
    }

    protected void onText(View v) {
        dCanvas.setMode("Text");
        brushButton.setEnabled(true);
        lineButton.setEnabled(true);
        textButton.setEnabled(false);
        rectButton.setEnabled(true);
        ovalButton.setEnabled(true);
    }

    protected void onRectangle(View v) {
        dCanvas.setMode("Rectangle");
        brushButton.setEnabled(true);
        lineButton.setEnabled(true);
        textButton.setEnabled(true);
        rectButton.setEnabled(false);
        ovalButton.setEnabled(true);
    }

    protected void onOval(View v) {
        dCanvas.setMode("Oval");
        brushButton.setEnabled(true);
        lineButton.setEnabled(true);
        textButton.setEnabled(true);
        rectButton.setEnabled(true);
        ovalButton.setEnabled(false);
    }

    protected void onSettings(View v) {
        // Show the settings menu
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        myDialogFragment.setCurrentColor(dCanvas.getPaint());
        myDialogFragment.show(getSupportFragmentManager(),"SettingsDialogFragment");
    }

    protected void onTransform(View v) {
        // Toggle the transform
        dCanvas.toggleTransform();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        Dialog dialogView = dialog.getDialog();

        SeekBar rSlider = (SeekBar)dialogView.findViewById(R.id.rSlider);
        SeekBar gSlider = (SeekBar)dialogView.findViewById(R.id.gSlider);
        SeekBar bSlider = (SeekBar)dialogView.findViewById(R.id.bSlider);
        SeekBar aSlider = (SeekBar)dialogView.findViewById(R.id.aSlider);
        SeekBar sizeSlider = (SeekBar)dialogView.findViewById(R.id.sizeSlider);

        Paint inputPaint = new Paint();
        inputPaint.setARGB((int)(aSlider.getProgress()/100.0*255),
                (int)(rSlider.getProgress()/100.0*255),
                (int)(gSlider.getProgress()/100.0*255),
                (int)(bSlider.getProgress()/100.0*255));

        float newSize = DrawingCanvas.MIN_SIZE+sizeSlider.getProgress()/100.0f*DrawingCanvas.MAX_SIZE;
        inputPaint.setStrokeWidth(newSize);
        inputPaint.setTextSize(newSize);
        inputPaint.setStrokeCap(Paint.Cap.ROUND);
        dCanvas.setPaint(inputPaint);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
    }
}
