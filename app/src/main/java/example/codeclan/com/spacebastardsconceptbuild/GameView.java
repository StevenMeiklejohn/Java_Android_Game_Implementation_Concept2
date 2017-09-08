
package example.codeclan.com.spacebastardsconceptbuild;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
    private ArrayList<EnemyProjectile> enemyProjectiles;
    private ArrayList<Star> stars = new ArrayList<Star>();

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        sprites = new ArrayList<Sprite>();
        explosions = new ArrayList<Explosion>();
        projectiles = new ArrayList<Projectile>();
        enemyProjectiles = new ArrayList<EnemyProjectile>();
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
                createPlayer();
                createButtons();
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
    }

    private void createProjectile(float x, float y){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.projectile);
        Projectile projectile = new Projectile(projectiles, this, (int)x, (int)y, bmp);
        this.projectiles.add(projectile);
    }

    private void createEnemyProjectile(float x, float y){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.projectile_red);
        EnemyProjectile projectile = new EnemyProjectile(enemyProjectiles, this, (int)x, (int)y, bmp);
        this.enemyProjectiles.add(projectile);
    }

    private void createButtons(){
        this.up = BitmapFactory.decodeResource(getResources(), R.drawable.green_arrow_up);
        this.down = BitmapFactory.decodeResource(getResources(), R.drawable.greenn_arrow_down);
        this.left = BitmapFactory.decodeResource(getResources(), R.drawable.green_arrow_left);
        this.right = BitmapFactory.decodeResource(getResources(), R.drawable.green_arrow_right);
        this.fire = BitmapFactory.decodeResource(getResources(), R.drawable.green_arrow_fire);
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
                return true;
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
                return true;
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
                return true;
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
                return true;
            }
        }
        //        Check fire button pushed.
        if(x >= 1600 && x <= 1700 && y >= 810 && y <= 900) {
//            Toast.makeText(this.getContext(), "Left Button pushed", Toast.LENGTH_LONG).show();
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                movePlayer("fire");
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
        canvas.drawText("LIVES: " + this.player.getLives(), 70, 70, paint);
    }

    public void drawScore(Canvas canvas){
        Paint paint = new Paint();
//        canvas.drawPaint(paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        canvas.drawText("Score: " + this.player.getScore(), 300, 70, paint);
    }

    public void enemyProjectiles(){
        for (Sprite sprite : sprites) {
            if(sprite.isFiring()){
               createEnemyProjectile(sprite.getX(), sprite.getY());
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
                        player.loseLife();
                    }
                    for (int z = projectiles.size() - 1; z >= 0; z--){
                        Projectile projectile = projectiles.get(z);
                        if(sprite.isCollision(projectile)){
                            createExplosion(sprite.getX(), sprite.getY());
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
                            enemyProjectiles.remove(projectile);
                        }
                    }
                }
            }
        return true;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        createBackground(canvas);
        drawLives(canvas);
        drawScore(canvas);
        removeProjectiles();
        enemyProjectiles();
        collisionLoop();
        for(int i = projectiles.size() -1; i >= 0; i--){
            projectiles.get(i).onDraw(canvas);
        }
        for (int i = explosions.size() - 1; i >= 0; i--) {
            explosions.get(i).onDraw(canvas);
        }
        for (int i = enemyProjectiles.size() - 1; i >= 0; i--) {
            enemyProjectiles.get(i).onDraw(canvas);
        }
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }
        if(sprites.size() < 1){
            createSprites();
        }
        if(this.player.getLives() > 0) {
            this.player.onDraw(canvas);
        }else{
            createExplosion(this.player.getX(), this.player.getY());
        }
        canvas.drawBitmap(this.up, 150, 700, null);
        canvas.drawBitmap(this.down, 150, 900, null);
        canvas.drawBitmap(this.left, 50, 800, null);
        canvas.drawBitmap(this.right, 250, 800, null);
        canvas.drawBitmap(this.fire, 1600, 800, null);
    }
}

