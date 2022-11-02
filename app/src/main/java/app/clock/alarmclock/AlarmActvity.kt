package app.clock.alarmclock

import android.media.Ringtone
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AlarmActvity : AppCompatActivity() {
    var ringtone: Ringtone? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_actvity)
    }
}