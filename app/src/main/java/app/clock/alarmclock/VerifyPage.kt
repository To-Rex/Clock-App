package app.clock.alarmclock

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

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
    }
}