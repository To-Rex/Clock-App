package app.clock.alarmclock.cleint

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import app.clock.alarmclock.R

class Resiver : BroadcastReceiver() {
    //var mediaPlayer: MediaPlayer? = null
    override fun onReceive(context: Context, intent: Intent) {
       // mediaPlayer = MediaPlayer.create(context, R.raw.alarm1)
       // mediaPlayer?.start()
        var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        val ringtone = RingtoneManager.getRingtone(context, alarmUri)
        ringtone!!.play()
    }
}