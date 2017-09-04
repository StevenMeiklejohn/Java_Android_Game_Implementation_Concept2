package example.codeclan.com.spacebastardsconceptbuild;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;


public class Sprite {
//    private static final int BMP_ROWS = 6;
//    private static final int BMP_COLUMNS = 4;
    private static final int MAX_SPEED = 6;
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
//    private int x = 200;
//    private int y = 200;
//    private int xSpeed = 5;
    private GameView gameView;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private Rect sourceRect;
    private Rect detectCollision;

    public Sprite(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth() / 4;
        this.height = bmp.getHeight();
        sourceRect = new Rect(0, 0, width, height);
        detectCollision = new Rect(x, y, x + width, y + height);
        setStartingPositionAndSpeed();
    }

    private void setStartingPositionAndSpeed(){
        Random rnd = new Random();
        x = gameView.getWidth() - width;
        y = rnd.nextInt(gameView.getHeight() - height);
        xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED/3;
        ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED/3;
    }



    private void update() {
        Random rnd = new Random();
        if (x > gameView.getWidth() - width - xSpeed) {
            xSpeed = -xSpeed;
        }

        if (x + xSpeed < 0){
            x = gameView.getWidth() - width;
        }
        if (y > gameView.getHeight() - height - ySpeed) {
            ySpeed = -this.ySpeed;
        }

        if(y + ySpeed < 0){
            ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED/3;
        }
        x = x + xSpeed;
        y = y + ySpeed;
        if(currentFrame ==3) {
            currentFrame = 0;}
            else
            {
                currentFrame = ++currentFrame;
            }
        }


    public void onDraw(Canvas canvas) {
        update();
//        int srcX = currentFrame * width;
//        int srcY = height;
        this.sourceRect.left = currentFrame * width;

        this.sourceRect.right = this.sourceRect.left + width;

//        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, sourceRect, dst, null);
    }
}
