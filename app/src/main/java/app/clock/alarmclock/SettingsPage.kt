package app.clock.alarmclock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SettingsPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_settings_page)
    }
}