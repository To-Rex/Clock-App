package app.clock.alarmclock

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class VerifyPage : AppCompatActivity() {

    private var ediVerCode: EditText? = null
    private var txtVerTime: TextView? = null
    private var btnVerOk: Button? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_page)

        ediVerCode = findViewById(R.id.ediVerCode)
        txtVerTime = findViewById(R.id.txtVerTime)
        btnVerOk = findViewById(R.id.btnVerOk)

        object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                txtVerTime?.text = "seconds remaining: " + millisUntilFinished / 1000
            }

            override fun onFinish() {
                txtVerTime?.text = "Time's finished!"
            }
        }.start()
    }
}