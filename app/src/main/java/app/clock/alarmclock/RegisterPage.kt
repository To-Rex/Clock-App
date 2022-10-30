package app.clock.alarmclock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
        val gson = Gson()

        txtRegEnter?.setOnClickListener {
            finish()
        }

        btnRegOk?.setOnClickListener {
            val email = ediRegEmail?.text.toString()
            val password = ediRegPas?.text.toString()
            val rePassword = ediRegRePas?.text.toString()
            if (email.isEmpty()) {
                ediRegEmail?.error = "Email is required"
                ediRegEmail?.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                ediRegPas?.error = "Password is required"
                ediRegPas?.requestFocus()
                return@setOnClickListener
            }
            if (rePassword.isEmpty()) {
                ediRegRePas?.error = "Re-Password is required"
                ediRegRePas?.requestFocus()
                return@setOnClickListener
            }
            if (password != rePassword) {
                ediRegRePas?.error = "Password not match"
                ediRegRePas?.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 4) {
                ediRegPas?.error = "Password must be at least 6 characters"
                ediRegPas?.requestFocus()
                return@setOnClickListener
            }
            val loginModels = LoginModels(email, password)
            val register: Call<Any?>? = ApiCleint().userService.register(loginModels)
            register?.enqueue(object : retrofit2.Callback<Any?> {
                override fun onResponse(call: Call<Any?>, response: retrofit2.Response<Any?>) {
                    if (response.code() == 400) {
                        val json = gson.fromJson(
                            response.errorBody()?.charStream(),
                            JsonObject::class.java
                        )
                        val message = json.get("error").asString
                        if (message == "email already exist") {
                            ediRegEmail?.error = "Hisob mavjud! Iltimos, boshqa email kiriting"
                            AlertDialog.Builder(this@RegisterPage)
                                .setTitle("Hisob mavjud")
                                .setMessage("Hisob mavjud! Iltimos, boshqa email kiriting")
                                .setPositiveButton("Tushunarli") { dialog, which ->
                                    dialog.dismiss()
                                }
                                .show()
                            ediRegEmail?.requestFocus()
                        }
                        Toast.makeText(this@RegisterPage, message, Toast.LENGTH_SHORT).show()
                    } else if (response.code() == 200) {
                        val json = gson.fromJson(response.body().toString(), JsonObject::class.java)
                        val token = json.get("token").asString
                        val verefy = json.get("verefy").asString
                        val intent = Intent(this@RegisterPage, VerifyPage::class.java)
                        intent.putExtra("token", token)
                        intent.putExtra("veRey", verefy)
                        intent.putExtra("email", email)
                        startActivity(intent)
                        Toast.makeText(this@RegisterPage, token, Toast.LENGTH_SHORT).show()
                        Toast.makeText(this@RegisterPage, verefy, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        ediRegEmail?.error = "Email is already taken"
                        ediRegEmail?.requestFocus()
                    }, 1000)
                }
            })
        }
    }
}