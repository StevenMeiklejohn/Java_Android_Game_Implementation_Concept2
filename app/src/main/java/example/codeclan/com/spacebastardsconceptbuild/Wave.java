package example.codeclan.com.spacebastardsconceptbuild;

import java.util.ArrayList;


/**
 * Created by user on 06/09/2017.
 */

public class Wave {

    private float timeSinceLastSpawn;
    private float spawnTime;
    private Sprite enemyType;
    private ArrayList<Sprite> enemyList;

    public Wave(float spawnTime, Sprite enemyType){
        this.spawnTime = spawnTime;
        this.enemyType = enemyType;
        timeSinceLastSpawn = 0;
        enemyList = new ArrayList<Sprite>();
    }

//    public void update(){
//        timeSinceLastSpawn += System.nanoTime();
//        if()
//    }
}
