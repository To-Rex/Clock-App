package app.clock.alarmclock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class RegisterPage : AppCompatActivity() {

    private var ediRegEmail: EditText? = null
    private var ediRegPas: EditText? = null
    private var ediRegRePas: EditText? = null
    private var btnRegOk: Button? = null
    private var txtRegEnter: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

    }
}