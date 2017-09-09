package example.codeclan.com.spacebastardsconceptbuild;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import static example.codeclan.com.spacebastardsconceptbuild.R.layout.activity_main;


public class MainActivity extends Activity {

//    private SoundPool soundPool;
//    private int soundID;
//    boolean plays = false, loaded = false;
//    float actVolume, maxVolume, volume;
//    AudioManager audioManager;
//    int counter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        startActivity(new Intent(this, GameActivity.class));
//        loadSound(R.raw.arcade_music);
//        playSound();
        setContentView(new GameView(this));
    }

//    public void buildBeforeAPI21() {
//
//        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//        actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        volume = actVolume / maxVolume;
//
//        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
//
//        counter = 0;
//
//        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
//            @Override
//            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//                loaded = true;
//            }
//        });
//
//        soundID = soundPool.load(this, R.raw.arcade_music, 1);
//    }


//    public void playSound() {
//        // Is the sound loaded does it already play?
//        if (loaded && !plays) {
//            soundPool.play(soundID, volume, volume, 1, 0, 1f);
//            counter = counter++;
//            Toast.makeText(this, "Played sound", Toast.LENGTH_SHORT).show();
//            plays = true;
//        }
//    }

//    public void stopSound() {
//        if (plays) {
//            soundPool.stop(soundID);
//            soundID = soundPool.load(this, R.raw.arcade_music, counter);
//            Toast.makeText(this, "Stop sound", Toast.LENGTH_SHORT).show();
//            plays = false;
//        }
//    }
//
//    private int loadSound(int filename) {
//        int soundID = 0;
//        if (soundPool == null) {
//            soundPool = buildSoundPool();
//        }
//        try {
//            soundID = soundPool.load(this, filename, 1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return soundID;
//    }


    /**
     * Verify device's API before to load soundpool
     * @return
     */
//    @SuppressWarnings("deprecation")
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private SoundPool buildSoundPool() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_GAME)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                    .build();
//
//            soundPool = new SoundPool.Builder()
//                    .setMaxStreams(25)
//                    .setAudioAttributes(audioAttributes)
//                    .build();
//        } else {
//            buildBeforeAPI21();
//        }
//        return soundPool;
//    }
}


