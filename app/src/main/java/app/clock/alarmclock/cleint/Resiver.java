package app.clock.alarmclock.cleint;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import app.clock.alarmclock.R;

public class Resiver extends BroadcastReceiver {
    MediaPlayer mediaPlayer;
    @Override
    public void onReceive(Context context, Intent intent) {
        mediaPlayer = MediaPlayer.create(context, R.raw.alarm1);
        mediaPlayer.start();
    }
}
