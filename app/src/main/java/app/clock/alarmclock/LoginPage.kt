package app.clock.alarmclock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login_page)
    }
}