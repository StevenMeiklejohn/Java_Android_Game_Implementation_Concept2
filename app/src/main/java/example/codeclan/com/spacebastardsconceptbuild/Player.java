package example.codeclan.com.spacebastardsconceptbuild;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

import static example.codeclan.com.spacebastardsconceptbuild.R.drawable.enemy;


public class Player {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private GameView gameView;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private Rect sourceRect;
    private Rect detectCollision;
    private boolean movingUp;
    private boolean movingDown;
    private boolean movingLeft;
    private boolean movingRight;
    private int lives;
    private int score;


    public Player(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth() / 4;
        this.height = bmp.getHeight();
        sourceRect = new Rect(0, 0, width, height);
        setStartingPositionAndSpeed();
        detectCollision = new Rect(x, y, x + width, y + height);
        this.lives = 3;
        this.score = 0;
    }

    private void setStartingPositionAndSpeed(){
        x = 100;
        y = 500;
        xSpeed = 20;
        ySpeed =20;
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

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public int getLives(){
        return this.lives;
    }

    public void loseLife(){
        this.lives -= 1;
    }

    public int getScore(){
        return this.score;
    }

    public void increaseScore(int amount){
        this.score += amount;
    }

    public boolean isCollision(Sprite sprite){
        return x < sprite.getX() + sprite.getWidth() && x + this.width > sprite.getX() && this.y < sprite.getY() + sprite.getHeight() && y + this.height > sprite.getY();
    }

    public void setMovingUp(){
        movingUp = true;
    }

    public void stopMovingUp(){
        movingUp = false;
    }

    public void setMovingDown(){
        movingDown = true;
    }

    public void stopMovingDown(){
        movingDown = false;
    }

    public void setMovingLeft(){
        movingLeft = true;
    }

    public void stopMovingLeft(){
        movingLeft = false;
    }

    public void setMovingRight(){
        movingRight = true;
    }

    public void stopMovingRight(){
        movingRight = false;
    }



    private void update() {
        if (movingRight) {
            if (x < gameView.getWidth() - width - xSpeed) {
                x = (x += xSpeed);
            }
        }
        if (movingLeft) {
            if (x + xSpeed > 0) {
                x = (x -= xSpeed);
            }
        }
        if (movingDown) {
            if (y < gameView.getHeight() - height - ySpeed) {
                y = (y += ySpeed);
            }
        }
        if (movingUp) {
            if (y + ySpeed > 0) {
                y = (y -= ySpeed);
            }
        }
//            x = x + xSpeed;
//            y = y + ySpeed;
            if (currentFrame == 3) {
                currentFrame = 0;
            } else {
                currentFrame = ++currentFrame;
            }
        }



    public void onDraw(Canvas canvas) {
        update();
        this.sourceRect.left = currentFrame * width;
        this.sourceRect.right = this.sourceRect.left + width;
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, sourceRect, dst, null);
    }
}
