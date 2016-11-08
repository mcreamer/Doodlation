package creamer.com.doodlation;

import android.graphics.Point;

/**
 * Created by MarkCreamer on 11/7/16.
 */
public class Transformer {
    private String xTransformation;
    private String yTransformation;

    public static Point transform(int x, int y) {
        return new Point(x,y);
    }
}
