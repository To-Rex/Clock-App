package app.clock.alarmclock

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("app.clock.alarmClock", MODE_PRIVATE)
        token = sharedPreferences?.getString("token", "")!!
        Handler(Looper.getMainLooper()).postDelayed({
            if (token.isEmpty()) {
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, Sample::class.java)
                startActivity(intent)
                finish()
            }
        }, 1000)

    }
}