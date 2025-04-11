package com.example.volumecontrol;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;

public class VolumeRestrictorService extends Service {

    private static final int MAX_VOLUME = 5;
    private Handler handler = new Handler();
    private Runnable volumeLimiter = new Runnable() {
        @Override
        public void run() {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                if (currentVolume > MAX_VOLUME) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, MAX_VOLUME, 0);
                }
            }
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        handler.post(volumeLimiter);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(volumeLimiter);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
