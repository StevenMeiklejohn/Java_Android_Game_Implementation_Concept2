package example.codeclan.com.spacebastardsconceptbuild;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;

import static example.codeclan.com.spacebastardsconceptbuild.R.layout.activity_main;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        startActivity(new Intent(this, GameActivity.class));
        setContentView(new GameView(this));

    }
}


