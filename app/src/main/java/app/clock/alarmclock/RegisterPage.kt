package app.clock.alarmclock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
                finish()
            },
            3000
        )

    }
}