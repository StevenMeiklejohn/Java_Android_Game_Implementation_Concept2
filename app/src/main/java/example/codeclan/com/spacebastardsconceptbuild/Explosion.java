package example.codeclan.com.spacebastardsconceptbuild;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.List;
import java.util.Random;

import static android.R.attr.width;

/**
 * Created by user on 06/09/2017.
 */

public class Explosion {


    private float x;
    private float y;
    private int width;
    private int height;
    private Bitmap bmp;
    private int life = 15;
    private int currentFrame = 0;
    private Rect sourceRect;
    private List<Explosion> explosions;

    public Explosion(List<Explosion> explosions, GameView gameView, float x,
                      float y, Bitmap bmp) {
//        this.x = Math.min(Math.max(x - bmp.getWidth() / 2, 0),
//                gameView.getWidth() - bmp.getWidth());
//        this.y = Math.min(Math.max(y - bmp.getHeight() / 2, 0),
//                gameView.getHeight() - bmp.getHeight());
        this.x = x;
        this.y = y;
        this.bmp = bmp;
        this.explosions = explosions;
        this.width = bmp.getWidth() / 9;
        this.height = bmp.getHeight();
        sourceRect = new Rect(0, 0, width, height);
    }

    public void onDraw(Canvas canvas) {
        update();
        this.sourceRect.left = currentFrame * width;
        this.sourceRect.right = this.sourceRect.left + width;
        Rect dst = new Rect((int)x, (int)y, (int)x + width, (int)y + height);
        canvas.drawBitmap(bmp, sourceRect, dst, null);
    }

    private void update() {
            if(currentFrame ==9) {
                currentFrame = 0;}
            else
            {
                currentFrame = ++currentFrame;
            }

        if (--life < 1) {
            explosions.remove(this);
        }
    }
}

