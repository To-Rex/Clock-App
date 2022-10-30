package app.clock.alarmclock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    private var veRey: String? = null
    var json: JsonObject? = null

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

        btnLogLogin?.setOnLongClickListener{
            startActivity(Intent(this, VerifyPage::class.java))
            false
        }
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
                    val user: Call<Any?>? = ApiCleint().userService.login(loginModels)
                    user?.enqueue(object : retrofit2.Callback<Any?> {
                        override fun onResponse(call: Call<Any?>, response: retrofit2.Response<Any?>) {
                            if (response.code() == 400) {
                                var json = gson.fromJson(response.errorBody()?.charStream(), JsonObject::class.java)
                                Toast.makeText(this@LoginPage, json.get("message").asString, Toast.LENGTH_SHORT).show()
                                val message = json.get("error").asString
                                if (message == "user is blocked") {
                                    Toast.makeText(this@LoginPage, "Your account is blocked", Toast.LENGTH_SHORT).show()
                                    AlertDialog.Builder(this@LoginPage)
                                            .setTitle("Your account is blocked")
                                            .setMessage("Please contact us to unblock your account")
                                            .setPositiveButton("OK") { dialog, _ ->
                                                dialog.dismiss()
                                            }
                                            .show()
                                }
                                if (message == "email is not verified") {
                                    val resendverefy: Call<Any?>? = ApiCleint().userService.resendverefy(email?.let { LoginModels(it, "") })
                                    resendverefy?.enqueue(object : retrofit2.Callback<Any?> {
                                        override fun onResponse(call: Call<Any?>, response: retrofit2.Response<Any?>) {
                                            if (response.code() == 400) {
                                                json = Gson().fromJson(response.errorBody()?.charStream(), JsonObject::class.java)
                                                val message = json.get("error").asString
                                                Toast.makeText(this@LoginPage, message, Toast.LENGTH_SHORT).show()
                                            } else {
                                                if (response.code() == 200) {
                                                    json = Gson().fromJson(response.body().toString(), JsonObject::class.java)
                                                    veRey = json.get("verefyCode").asString
                                                    Toast.makeText(this@LoginPage, "Kod yuborildi", Toast.LENGTH_SHORT).show()
                                                    val intent = Intent(this@LoginPage, VerifyPage::class.java)
                                                    intent.putExtra("email", email)
                                                    intent.putExtra("veRey", veRey)
                                                    startActivity(intent)
                                                }
                                            }
                                        }
                                        override fun onFailure(call: Call<Any?>, t: Throwable) {
                                            t.printStackTrace()
                                        }
                                    })

                                }
                            } else if (response.code() == 200) {
                                val json = gson.fromJson(response.body().toString(), JsonObject::class.java)
                                val token = json.get("token").asString
                                Toast.makeText(this@LoginPage, token, Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Any?>, t: Throwable) {
                            Toast.makeText(this@LoginPage, "Login Failed", Toast.LENGTH_SHORT).show()
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