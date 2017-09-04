
package example.codeclan.com.spacebastardsconceptbuild;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;

public class GameView extends SurfaceView {
    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Sprite sprite;
    private Player player;
    private Paint paint;
    private Bitmap up;
    private Bitmap down;
    private Bitmap left;
    private Bitmap right;
    private ArrayList<Sprite> sprites;
    private ArrayList<Star> stars = new ArrayList<Star>();

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        sprites = new ArrayList<Sprite>();
        paint = new Paint();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createSprites();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
            });
    }


    private void createBackground(Canvas canvas){
        canvas.drawRGB(0, 0, 0);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, canvas.getWidth(), canvas.getHeight(), true);
        canvas.drawBitmap(scaledBitmap, 0, 0, null);
    }


    public void movePlayer(String direction){
        if(direction == "up") {
            player.setMovingUp();
        }
        if(direction == "down"){
            player.setMovingDown();
        }
        if(direction == "left"){
            player.setMovingLeft();
        }
        if(direction == "right"){
            player.setMovingRight();
        }
    }

    public void stopMovePlayer(String direction){
        if(direction == "up") {
            player.stopMovingUp();
        }
        if(direction == "down"){
            player.stopMovingDown();
        }
        if(direction == "left"){
            player.stopMovingLeft();
        }
        if(direction == "right"){
            player.stopMovingRight();
        }
    }

    private void createSprites(){
        sprites.add(createSprite(R.drawable.enemy1_sprite_sheet_90_90));
        sprites.add(createSprite(R.drawable.enemy1_sprite_sheet_90_90));
        sprites.add(createSprite(R.drawable.enemy1_sprite_sheet_90_90));
        sprites.add(createSprite(R.drawable.enemy1_sprite_sheet_90_90));
        sprites.add(createSprite(R.drawable.enemy_triangle_sprite_sheet_90_90));
        sprites.add(createSprite(R.drawable.enemy_triangle_sprite_sheet_90_90));
        sprites.add(createSprite(R.drawable.enemy_triangle_sprite_sheet_90_90));
        sprites.add(createSprite(R.drawable.enemy_triangle_sprite_sheet_90_90));
//        sprites.add(createPlayer(R.drawable.player_sprite_sheet_120_60));
        createPlayer(R.drawable.player_sprite_sheet_90_45);
        createButtons(R.drawable.green_arrow_up, R.drawable.greenn_arrow_down, R.drawable.green_arrow_left, R.drawable.green_arrow_right);

    }

    private Sprite createSprite(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this, bmp);
    }

    private void createPlayer(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        this.player =  new Player(this, bmp);
    }

    private void createButtons(int resource_up, int resource_down, int resource_left, int resource_right){
        this.up = BitmapFactory.decodeResource(getResources(), resource_up);
        this.down = BitmapFactory.decodeResource(getResources(), resource_down);
        this.left = BitmapFactory.decodeResource(getResources(), resource_left);
        this.right = BitmapFactory.decodeResource(getResources(), resource_right);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Toast.makeText(this.getContext(), "Touch event triggered " + "X: " + (int) event.getX() + "Y: " + (int) event.getY(), Toast.LENGTH_LONG).show();
        int x = (int) event.getX();
        int y = (int) event.getY();

//        Check up button pushed.
        if(x >= 170 && x <= 250 && y >= 720 && y <= 800) {
//            Toast.makeText(this.getContext(), "Up Button pushed", Toast.LENGTH_LONG).show();
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                movePlayer("up");
                return true;
            }
            else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                stopMovePlayer("up");
            }
        }
//        Check down button pushed.
        if(x >= 170 && x <= 250 && y >= 910 && y <= 1000) {
//            Toast.makeText(this.getContext(), "Down Button pushed", Toast.LENGTH_LONG).show();
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                movePlayer("down");
                return true;
            }
            else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                stopMovePlayer("down");
            }
        }
//        Check left button pushed.
        if(x >= 70 && x <= 150 && y >= 810 && y <= 900) {
//            Toast.makeText(this.getContext(), "Left Button pushed", Toast.LENGTH_LONG).show();
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                movePlayer("left");
                return true;
            }
            else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                stopMovePlayer("left");
            }
        }
//        Check right button pushed.
        if(x >= 260 && x <= 350 && y >= 810 && y <= 900) {
//            Toast.makeText(this.getContext(), "Right Button pushed", Toast.LENGTH_LONG).show();
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                movePlayer("right");
                return true;
            }
            else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                stopMovePlayer("right");
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        createBackground(canvas);
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }
        this.player.onDraw(canvas);
        canvas.drawBitmap(this.up, 150, 700, null);
        canvas.drawBitmap(this.down, 150, 900, null);
        canvas.drawBitmap(this.left, 50, 800, null);
        canvas.drawBitmap(this.right, 250, 800, null);
    }
}

