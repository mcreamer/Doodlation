package creamer.com.doodlation;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

/**
 * Created by MarkCreamer on 11/3/16.
 */
public class MyDialogFragment extends DialogFragment {
    private Paint paint;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement MyDialogListener");
        }
    }

    public void setCurrentColor(Paint p) {
        paint = p;
    }

    public Paint getCurrentColor() {
        return paint;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View inflated = inflater.inflate(R.layout.settings,null);
        SeekBar rSlider = (SeekBar)inflated.findViewById(R.id.rSlider);
        SeekBar gSlider = (SeekBar)inflated.findViewById(R.id.gSlider);
        SeekBar bSlider = (SeekBar)inflated.findViewById(R.id.bSlider);
        SeekBar aSlider = (SeekBar)inflated.findViewById(R.id.aSlider);
        SeekBar sizeSlider = (SeekBar)inflated.findViewById(R.id.sizeSlider);
        final SampleCursor sampleCursor = (SampleCursor)inflated.findViewById(R.id.sampleCursor);

        rSlider.setProgress((int)(Color.red(paint.getColor())/255.0*100));
        gSlider.setProgress((int)(Color.green(paint.getColor())/255.0*100));
        bSlider.setProgress((int)(Color.blue(paint.getColor())/255.0*100));
        aSlider.setProgress((int)(Color.alpha(paint.getColor())/255.0*100));
        sizeSlider.setProgress((int)(((paint.getStrokeWidth()-DrawingCanvas.MIN_SIZE)/(DrawingCanvas.MAX_SIZE-DrawingCanvas.MIN_SIZE))*100));

        rSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int prevColor = paint.getColor();
                paint.setColor(Color.argb(Color.alpha(prevColor),(int)(255*i/100.0),Color.green(prevColor),Color.blue(prevColor)));
                sampleCursor.samplePaint = paint;
                sampleCursor.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        gSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int prevColor = paint.getColor();
                paint.setColor(Color.argb(Color.alpha(prevColor),Color.red(prevColor),(int)(255*i/100.0),Color.blue(prevColor)));
                sampleCursor.samplePaint = paint;
                sampleCursor.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        bSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int prevColor = paint.getColor();
                paint.setColor(Color.argb(Color.alpha(prevColor),Color.red(prevColor),Color.green(prevColor),(int)(255*i/100.0)));
                sampleCursor.samplePaint = paint;
                sampleCursor.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        aSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int prevColor = paint.getColor();
                paint.setColor(Color.argb((int)(255*i/100.0),Color.red(prevColor),Color.green(prevColor),Color.blue(prevColor)));
                sampleCursor.samplePaint = paint;
                sampleCursor.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                paint.setStrokeWidth(DrawingCanvas.MIN_SIZE+(DrawingCanvas.MAX_SIZE-DrawingCanvas.MIN_SIZE)*(i/100.0f));
                sampleCursor.samplePaint = paint;
                sampleCursor.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sampleCursor.samplePaint = paint;
        sampleCursor.invalidate();

        builder.setView(inflated).setMessage(R.string.configure)
                .setPositiveButton(R.string.apply, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(MyDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(MyDialogFragment.this);
                    }
                });

        return builder.create();
    }
}
