package app.clock.alarmclock

import android.annotation.SuppressLint
import android.content.Intent
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
    private var email: String? = null
    private var token: String? = null
    private var verefy: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_page)

        ediVerCode = findViewById(R.id.ediVerCode)
        txtVerTime = findViewById(R.id.txtVerTime)
        btnVerOk = findViewById(R.id.btnVerOk)

        email = intent.getStringExtra("email")
        token = intent.getStringExtra("token")
        verefy = intent.getStringExtra("verefy")

        Timers()

        btnVerOk?.setOnClickListener {
            val code = ediVerCode?.text.toString()
            if (code.isEmpty()) {
                ediVerCode?.error = "Kodni kiriting"
                ediVerCode?.requestFocus()
                return@setOnClickListener
            }
            if (code != verefy) {
                ediVerCode?.error = "Kod noto'g'ri"
                ediVerCode?.requestFocus()
                return@setOnClickListener
            }
            
        }

    }
    private fun Timers(){
        object : CountDownTimer(5000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                txtVerTime?.text = "00:" + millisUntilFinished / 1000
            }

            @SuppressLint("SetTextI18n", "ResourceAsColor")
            override fun onFinish() {
                txtVerTime?.text = "Qayta urinib ko'ring"
                txtVerTime?.setTextColor(R.color.teal_700)
                txtVerTime?.setOnClickListener { Timers() }
            }
        }.start()
    }

}