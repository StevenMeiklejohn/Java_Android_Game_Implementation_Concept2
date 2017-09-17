
package example.codeclan.com.spacebastardsconceptbuild;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.constraint.solver.widgets.Rectangle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.contextClickable;
import static android.R.attr.data;
import static android.R.attr.level;
import static android.R.attr.resource;
import static android.R.attr.value;
import static android.R.attr.width;
import static android.R.attr.x;
import static android.R.attr.y;
import static android.media.CamcorderProfile.get;
import static android.support.v4.content.ContextCompat.startActivity;

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
    private Boolean gameOver;
    private int scoreX;
    private int scoreY;
    private int livesX;
    private int livesY;
    private int upButtonX;
    private int upButtonY;
    private int leftButtonX;
    private int leftButtonY;
    private int rightButtonX;
    private int rightButtonY;
    private int downButtonX;
    private int downButtonY;
    private int fireButtonX;
    private int fireButtonY;
    private int screenX;
    private int screenY;
    private ArrayList<Sprite> sprites;
    private ArrayList<Explosion> explosions;
    private ArrayList<Projectile> projectiles;
    private ArrayList<EnemyProjectile> enemyProjectiles;
    private ArrayList<Star> stars = new ArrayList<Star>();
    private static MediaPlayer mediaPlayer;
    private static MediaPlayer mediaPlayer2;
    private static MediaPlayer mediaPlayer3;
    private static MediaPlayer mediaPlayer4;


    public GameView(final Context context) {
        super(context);

        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        sprites = new ArrayList<Sprite>();
        explosions = new ArrayList<Explosion>();
        projectiles = new ArrayList<Projectile>();
        enemyProjectiles = new ArrayList<EnemyProjectile>();
        this.gameOver = false;
        paint = new Paint();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenX = size.x;
        screenY = size.y;



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
                playMusic(context);
                createSprites();
                createPlayer();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
            });
    }



    public static void playMusic(Context context){
        mediaPlayer = MediaPlayer.create(context, R.raw.arcade_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }


    public static void playPlayerFire(Context context){
        mediaPlayer2 = MediaPlayer.create(context, R.raw.laser_weapon);
        mediaPlayer2.setLooping(false);
        mediaPlayer2.start();
    }

    public static void playLargeExplosion(Context context){
        mediaPlayer3 = MediaPlayer.create(context, R.raw.large_explosion);
        mediaPlayer3.setLooping(false);
        mediaPlayer3.setVolume( (float)0.6, (float)0.6 );
        mediaPlayer3.start();
    }


    public static void playDamage(Context context){
        mediaPlayer4 = MediaPlayer.create(context, R.raw.damage);
        mediaPlayer4.setLooping(false);
        mediaPlayer4.start();
    }


    private void createBackground(Canvas canvas){
        canvas.drawRGB(0, 0, 0);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, canvas.getWidth(), canvas.getHeight(), true);
        canvas.drawBitmap(scaledBitmap, 0, 0, null);
    }




    public void movePlayer(String direction, Context context){
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
            createProjectile(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, context);
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
     }

    private Sprite createSprite(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this, bmp);
    }

    private void createPlayer() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.player_sprite_sheet_90_45 );
        this.player =  new Player(this, bmp);
    }

    private void createExplosion(float x, float y){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.explosion_sprite_sheet);
        Explosion newExplosion = new Explosion(explosions, this, x, y, bmp);
        this.explosions.add(newExplosion);
//        playLargeExplosion( context );
    }

    private void createProjectile(float x, float y, Context context){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.projectile);
        Projectile projectile = new Projectile(projectiles, this, (int)x, (int)y, bmp);
        this.projectiles.add(projectile);
        playPlayerFire(context);
    }

    private void createEnemyProjectile(float x, float y){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.projectile_red);
        EnemyProjectile projectile = new EnemyProjectile(enemyProjectiles, this, (int)x, (int)y, bmp);
        this.enemyProjectiles.add(projectile);
    }

    private void createButtons(Canvas canvas){
        this.up = BitmapFactory.decodeResource(getResources(), R.drawable.green_arrow_up);
        upButtonX = screenX - (screenX / 10) * 9;
        upButtonY = screenY - (screenY / 20) * 7;
        this.down = BitmapFactory.decodeResource(getResources(), R.drawable.greenn_arrow_down);
        downButtonX = upButtonX;
        downButtonY = upButtonY + 150;
        this.left = BitmapFactory.decodeResource(getResources(), R.drawable.green_arrow_left);
        leftButtonX = upButtonX - 75;
        leftButtonY = upButtonY + 75;
        this.right = BitmapFactory.decodeResource(getResources(), R.drawable.green_arrow_right);
        rightButtonX = leftButtonX + 150;
        rightButtonY = leftButtonY;
        this.fire = BitmapFactory.decodeResource(getResources(), R.drawable.green_arrow_fire);
        fireButtonX = screenX - 100;
        fireButtonY = rightButtonY;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();



//        Check up button pushed.
        if(x >= upButtonX + 20 && x <= upButtonX + 70 && y >= upButtonY + 20 && y <= upButtonY + 70) {

            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                movePlayer("up", this.getContext());
                return true;
            }
            else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                stopMovePlayer("up");
                return true;
            }

        }
//        Check down button pushed.
        if(x >= downButtonX + 20 && x <= downButtonX + 70 && y >= downButtonY + 20 && y <= downButtonY + 70) {
//            Toast.makeText(this.getContext(), "Down Button pushed", Toast.LENGTH_LONG).show();
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                movePlayer("down", this.getContext());
                return true;
            }
            else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                stopMovePlayer("down");
                return true;
            }
        }
//        Check left button pushed.
        if(x >= leftButtonX + 20 && x <= leftButtonX + 70 && y >= leftButtonY + 20 && y <= leftButtonY + 70) {
//            Toast.makeText(this.getContext(), "Left Button pushed", Toast.LENGTH_LONG).show();
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                movePlayer("left", this.getContext());
                return true;
            }
            else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                stopMovePlayer("left");
                return true;
            }
        }
//        Check right button pushed.
        if(x >= rightButtonX + 20 && x <= rightButtonX + 70 && y >= rightButtonY + 20 && y <= rightButtonY + 70) {
//            Toast.makeText(this.getContext(), "Right Button pushed", Toast.LENGTH_LONG).show();
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                movePlayer("right", this.getContext());
                return true;
            }
            else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                stopMovePlayer("right");
                return true;
            }
        }
        //        Check fire button pushed.
        if(x >= fireButtonX + 20 && x <= fireButtonX + 70 && y >= fireButtonY + 20 && y <= fireButtonY + 70) {
//            Toast.makeText(this.getContext(), "Left Button pushed", Toast.LENGTH_LONG).show();
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                movePlayer("fire", this.getContext());
                return true;
            }
            else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                stopMovePlayer("fire");
                return true;
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
//        for (int i = enemyProjectiles.size() - 1; i >= 0; i--){
//            EnemyProjectile projectile = enemyProjectiles.get(i);
//            if (projectile.getX() < 0){
//                projectiles.remove(projectile);
//            }
//        }
    }

    public void drawLives(Canvas canvas){
        Paint paint = new Paint();
//        canvas.drawPaint(paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        canvas.drawText("LIVES: " + this.player.getLives(), 200, 60 , paint);
    }

    public void drawScore(Canvas canvas){
        Paint paint = new Paint();
//        canvas.drawPaint(paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        canvas.drawText("Score: " + this.player.getScore(), 400 , 60, paint);
    }

    public void enemyProjectiles(){
        for (Sprite sprite : sprites) {
            if(sprite.isFiring()){
               createEnemyProjectile(sprite.getX(), sprite.getY());
            }
        }
    }

//    private void restartGame() {
///        playMusic(context);
//        createSprites();
//        createPlayer();
//        createButtons();
//        gameLoopThread.setRunning(true);
//        gameLoopThread.start();
//        temps.clear();
//        sprites.clear();
//        numberOfGuy = numbers * level;
//        createBarView();
//        createSprites();
//        gameOver = false;
//    }

    public void gameOver() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer2.release();
        mediaPlayer3.release();
        mediaPlayer4.release();
        gameOver = true;
        gameLoopThread.setRunning( false );
        launchGameOver();
    }

    public void launchGameOver(){
            Intent newIntent = new Intent(this.getContext(), GameOver.class);
            this.getContext().startActivity(newIntent);
    }



    public boolean collisionLoop() {
            synchronized (getHolder()) {
                for (int i = sprites.size() - 1; i >= 0; i--) {
                    Sprite sprite = sprites.get(i);
                    if(this.player.isCollision(sprite)){
                        createExplosion(sprite.getX(), sprite.getY());
                        sprites.remove(sprite);
                        player.loseLife();
                        playDamage( this.getContext() );
                        playLargeExplosion( this.getContext() );
                    }
                    for (int z = projectiles.size() - 1; z >= 0; z--){
                        Projectile projectile = projectiles.get(z);
                        if(sprite.isCollision(projectile)){
                            createExplosion(sprite.getX(), sprite.getY());
                            playLargeExplosion( this.getContext() );
                            sprites.remove(sprite);
                            this.player.increaseScore(20);
                            projectiles.remove(projectile);
                        }
                    }
                    for (int y = enemyProjectiles.size() - 1; y >= 0; y--){
                        EnemyProjectile projectile = enemyProjectiles.get(y);
                        if(this.player.isProjectileCollision(projectile)){
                            createExplosion(player.getX(), player.getY());
                            this.player.loseLife();
                            playDamage( this.getContext() );
                            playLargeExplosion( this.getContext() );
                            enemyProjectiles.remove(projectile);

                        }
                    }
                }
            }
        return true;
    }





    @Override
    protected void onDraw(Canvas canvas) {
        if(this.player.getLives() < 1){
            gameOver();
        }
        else {

            createBackground( canvas );
            removeProjectiles();
            enemyProjectiles();
            collisionLoop();
            for (int i = projectiles.size() - 1; i >= 0; i--) {
                projectiles.get( i ).onDraw( canvas );
            }
            for (int i = explosions.size() - 1; i >= 0; i--) {
                explosions.get( i ).onDraw( canvas );
            }
            for (int i = enemyProjectiles.size() - 1; i >= 0; i--) {
                enemyProjectiles.get( i ).onDraw( canvas );
            }
            for (Sprite sprite : sprites) {
                sprite.onDraw( canvas );
            }
            if (sprites.size() < 1) {
                createSprites();
            }
            if (this.player.getLives() > 0) {
                this.player.onDraw( canvas );
            } else {
                createExplosion( this.player.getX(), this.player.getY());
            }
            createButtons( canvas );
            drawLives( canvas );
            drawScore( canvas );
        }
        canvas.drawBitmap(this.up, upButtonX, upButtonY, null);
        canvas.drawBitmap(this.down, downButtonX, downButtonY, null);
        canvas.drawBitmap(this.left, leftButtonX, leftButtonY, null);
        canvas.drawBitmap(this.right, rightButtonX, rightButtonY, null);
        canvas.drawBitmap(this.fire, fireButtonX, fireButtonY, null);
    }


}

