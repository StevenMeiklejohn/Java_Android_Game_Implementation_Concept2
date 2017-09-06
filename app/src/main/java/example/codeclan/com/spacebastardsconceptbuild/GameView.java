
package example.codeclan.com.spacebastardsconceptbuild;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.resource;
import static android.R.attr.width;
import static android.R.attr.x;
import static android.R.attr.y;
import static android.media.CamcorderProfile.get;

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
    private Bitmap fire;
    private ArrayList<Sprite> sprites;
    private ArrayList<Explosion> explosions;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Star> stars = new ArrayList<Star>();

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        sprites = new ArrayList<Sprite>();
        explosions = new ArrayList<Explosion>();
        projectiles = new ArrayList<Projectile>();
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
        if(direction == "fire"){
            createProjectile(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2);
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
        if(direction == "fire"){
//            player.stopFiring();
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
        createButtons(R.drawable.green_arrow_up, R.drawable.greenn_arrow_down, R.drawable.green_arrow_left, R.drawable.green_arrow_right, R.drawable.green_arrow_right);

    }

    private Sprite createSprite(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this, bmp);
    }

    private void createPlayer(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        this.player =  new Player(this, bmp);
    }

    private void createExplosion(float x, float y){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.explosion_sprite_sheet);
        Explosion newExplosion = new Explosion(explosions, this, x, y, bmp);
        this.explosions.add(newExplosion);
    }

    private void createProjectile(float x, float y){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.projectile);
        Projectile projectile = new Projectile(projectiles, this, (int)x, (int)y, bmp);
        this.projectiles.add(projectile);
    }

    private void createButtons(int resource_up, int resource_down, int resource_left, int resource_right, int resource_fire){
        this.up = BitmapFactory.decodeResource(getResources(), resource_up);
        this.down = BitmapFactory.decodeResource(getResources(), resource_down);
        this.left = BitmapFactory.decodeResource(getResources(), resource_left);
        this.right = BitmapFactory.decodeResource(getResources(), resource_right);
        this.fire = BitmapFactory.decodeResource(getResources(), resource_fire);
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
        //        Check fire button pushed.
        if(x >= 600 && x <= 700 && y >= 810 && y <= 900) {
//            Toast.makeText(this.getContext(), "Left Button pushed", Toast.LENGTH_LONG).show();
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                movePlayer("fire");
                return true;
            }
            else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                stopMovePlayer("fire");
            }
        }
        return super.onTouchEvent(event);
    }

    public void removeProjectiles(){
        for (int i = projectiles.size() - 1; i >= 0; i--){
            Projectile projectile = projectiles.get(i);
            if (projectile.getX() > this.getWidth()){
                projectiles.remove(projectile);
            }
        }
    }



    public boolean collisionLoop() {
            synchronized (getHolder()) {
                for (int i = sprites.size() - 1; i >= 0; i--) {
                    Sprite sprite = sprites.get(i);
                    if(this.player.isCollision(sprite)){
                        createExplosion(sprite.getX(), sprite.getY());
                        sprites.remove(sprite);
                    }
                    for (int z = projectiles.size() - 1; z >= 0; z--){
                        Projectile projectile = projectiles.get(z);
                        if(sprite.isCollision(projectile)){
                            createExplosion(sprite.getX(), sprite.getY());
                            sprites.remove(sprite);
                            projectiles.remove(projectile);
                        }
                    }
                }
            }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        createBackground(canvas);
        removeProjectiles();
        collisionLoop();
        for(int i = projectiles.size() -1; i >= 0; i--){
            projectiles.get(i).onDraw(canvas);
        }
        for (int i = explosions.size() - 1; i >= 0; i--) {
            explosions.get(i).onDraw(canvas);
        }
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }
        this.player.onDraw(canvas);
        canvas.drawBitmap(this.up, 150, 700, null);
        canvas.drawBitmap(this.down, 150, 900, null);
        canvas.drawBitmap(this.left, 50, 800, null);
        canvas.drawBitmap(this.right, 250, 800, null);
        canvas.drawBitmap(this.fire, 600, 800, null);
    }
}

