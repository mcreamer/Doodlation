package creamer.com.doodlation;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DrawingCanvas dCanvas;
    private ImageButton brushButton;
    private ImageButton lineButton;

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
    }

    protected void onBrush(View v) {
        dCanvas.setMode("Brush");
        brushButton.setEnabled(false);
        lineButton.setEnabled(true);
    }

    protected void onLine(View v) {
        dCanvas.setMode("Line");
        brushButton.setEnabled(true);
        lineButton.setEnabled(false);
    }

    protected void onUndo(View v) {
        dCanvas.undo();
    }

    protected void onClear(View v) {
        dCanvas.clear();
    }

    protected void onText(View v) {
        //dCanvas.makeText();
    }

    protected void onSettings(View v) {
        // Show the settings menu

    }
}
