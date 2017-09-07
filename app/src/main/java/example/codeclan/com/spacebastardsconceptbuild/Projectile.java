package example.codeclan.com.spacebastardsconceptbuild;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.List;
import java.util.Random;


public class Projectile {
//    private static final int BMP_ROWS = 6;
//    private static final int BMP_COLUMNS = 4;
//    private static final int MAX_SPEED = 10;
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
    private List<Projectile> projectiles;

    public Projectile(List<Projectile> projectiles, GameView gameView, int x, int y, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth() / 6;
        this.height = bmp.getHeight();
        this.x = x;
        this.y = y;
        sourceRect = new Rect(0, 0, width, height);
        detectCollision = new Rect((int)x, (int)y, (int)x + width, (int)y + height);
        setStartingPositionAndSpeed();
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

    public boolean isCollision(Sprite sprite){
        return x < sprite.getX() + sprite.getWidth() && x + this.width > sprite.getX() && this.y < sprite.getY() + sprite.getHeight() && y + this.height > sprite.getY();
    }


    private void setStartingPositionAndSpeed(){
        xSpeed = 50;
        ySpeed = 0;
    }


    private void update() {
        x = x + xSpeed;
        y = y + ySpeed;
        if(currentFrame ==5) {
            currentFrame = 0;}
            else
            {
                currentFrame = ++currentFrame;
            }
        }


    public void onDraw(Canvas canvas) {
        update();
        this.sourceRect.left = currentFrame * width;
        this.sourceRect.right = this.sourceRect.left + width;
        Rect dst = new Rect((int)x, (int)y, (int)x + width, (int)y + height);
        canvas.drawBitmap(bmp, sourceRect, dst, null);
    }
}
