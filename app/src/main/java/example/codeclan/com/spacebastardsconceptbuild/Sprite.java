package example.codeclan.com.spacebastardsconceptbuild;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

import java.util.Random;


public class Sprite {
//    private static final int BMP_ROWS = 6;
//    private static final int BMP_COLUMNS = 4;
    private static final int MAX_SPEED = 20;
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
    private Boolean firing;
    private long last_time;
    private int projectileGap;

    public Sprite(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth() / 4;
        this.height = bmp.getHeight();
        sourceRect = new Rect(0, 0, width, height);
        detectCollision = new Rect(x, y, x + width, y + height);
        setStartingPositionAndSpeed();
        firing = false;
        last_time = System.nanoTime();
    }

    public Rect getCollisionBox(){
        return this.detectCollision;
    }

    public int getX(){
        return this.x;
    }


    public int getY(){
        return this.y;
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }


    public boolean isCollision(Projectile projectile){
        return x < projectile.getX() + projectile.getWidth() && x + this.width > projectile.getX() && this.y < projectile.getY() + projectile.getHeight() && y + this.height > projectile.getY();
    }

    public boolean isFiring(){
        long time = System.nanoTime();
        int delta_time = (int) ((time - last_time) / 1000000);
        if(delta_time > this.projectileGap){
            this.firing = true;
            this.last_time = time;
            return true;
        }
        else{
            return false;
        }

    }


    private void setStartingPositionAndSpeed(){
        Random rnd = new Random();
        x = gameView.getWidth() - width;
        y = rnd.nextInt(gameView.getHeight() - height);
        xSpeed = rnd.nextInt(MAX_SPEED) + 10;
        this.projectileGap = rnd.nextInt(15000) + 3000;
//        ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED/3;
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
