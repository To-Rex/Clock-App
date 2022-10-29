package app.clock.alarmclock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import app.clock.alarmclock.cleint.ApiCleint
import app.clock.alarmclock.models.LoginModels
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call

class LoginPage : AppCompatActivity() {

    private var ediLogEmail: EditText? = null
    private var ediLogPas: EditText? = null
    private var txtLogPass: TextView? = null
    private var btnLogLogin: Button? = null
    private var txtLogReg: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login_page)

        ediLogEmail = findViewById(R.id.ediLogEmail)
        ediLogPas = findViewById(R.id.ediLogPas)
        txtLogPass = findViewById(R.id.txtLogPass)
        btnLogLogin = findViewById(R.id.btnLogLogin)
        txtLogReg = findViewById(R.id.txtLogReg)
        val gson = Gson()

        btnLogLogin?.setOnClickListener {
            val email = ediLogEmail?.text.toString()
            val password = ediLogPas?.text.toString()
            if (email.isEmpty()) {
                ediLogEmail?.error = "Please enter email"
            } else if (password.isEmpty()) {
                ediLogPas?.error = "Please enter password"
            } else {
                if (email.length < 6) {
                    ediLogEmail?.error = "Email must be at least 6 characters"
                } else if (password.length < 4) {
                    ediLogPas?.error = "Password must be at least 5 characters"
                } else {
                    val loginModels = LoginModels(email, password)
                    val user: Call<Any>? = ApiCleint().userService.login(loginModels)
                    user?.enqueue(object : retrofit2.Callback<Any> {
                        override fun onResponse(call: Call<Any>, response: retrofit2.Response<Any>) {
                            if (response.code() == 400) {
                                val json = gson.fromJson(response.errorBody()?.charStream(), JsonObject::class.java)
                                val message = json.get("error").asString
                                Toast.makeText(this@LoginPage, message, Toast.LENGTH_SHORT).show()
                            } else if (response.code() == 200) {
                                Toast.makeText(this@LoginPage, "Login successful", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Any>, t: Throwable) {
                            Toast.makeText(this@LoginPage, "Login Failed 😜", Toast.LENGTH_SHORT).show()
                        }
                    })

                }
            }
        }

        txtLogReg?.setOnClickListener {
            startActivity(Intent(this, RegisterPage::class.java))
        }
    }

}