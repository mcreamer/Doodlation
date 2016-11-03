package creamer.com.doodlation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by MarkCreamer on 11/3/16.
 */
public class SampleCursor extends View {
    public Paint samplePaint;

    public SampleCursor(Context context, AttributeSet attrs) {
        super(context,attrs);

        samplePaint = new Paint();
        samplePaint.setStrokeWidth(10);
        samplePaint.setStrokeCap(Paint.Cap.ROUND);
        samplePaint.setColor(Color.BLACK);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(getWidth()/4,getHeight()/2,3*getWidth()/4,getHeight()/2,samplePaint);
    }
}
