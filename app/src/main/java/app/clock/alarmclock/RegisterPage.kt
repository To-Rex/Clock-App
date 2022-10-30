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

        ediRegEmail = findViewById(R.id.ediRegEmail)
        ediRegPas = findViewById(R.id.ediRegPas)
        ediRegRePas = findViewById(R.id.ediRegRePas)
        btnRegOk = findViewById(R.id.btnRegOk)
        txtRegEnter = findViewById(R.id.txtRegEnter)

        btnRegOk?.setOnClickListener {
            val email = ediRegEmail?.text.toString()
            val password = ediRegPas?.text.toString()
            val rePassword = ediRegRePas?.text.toString()
            if (email.isEmpty()){
                ediRegEmail?.error = "Email is required"
                ediRegEmail?.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                ediRegPas?.error = "Password is required"
                ediRegPas?.requestFocus()
                return@setOnClickListener
            }
            if (rePassword.isEmpty()){
                ediRegRePas?.error = "Re-Password is required"
                ediRegRePas?.requestFocus()
                return@setOnClickListener
            }
            if (password != rePassword){
                ediRegRePas?.error = "Password not match"
                ediRegRePas?.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 4){
                ediRegPas?.error = "Password must be at least 6 characters"
                ediRegPas?.requestFocus()
                return@setOnClickListener
            }
            
        }
    }
}