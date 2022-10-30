package app.clock.alarmclock

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import app.clock.alarmclock.cleint.ApiCleint
import app.clock.alarmclock.models.GetTimes
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call

class Sample : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    var token = ""
    var listSample: ListView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sample)

        listSample = findViewById(R.id.listSample)

        sharedPreferences = getSharedPreferences("app.clock.alarmClock", MODE_PRIVATE)
        token = sharedPreferences?.getString("token", "")!!
        val getTimes = GetTimes("","","")
        val gettime: Call<Any?>? = ApiCleint().userService.gettimes("Bearer $token")
        gettime?.enqueue(object : retrofit2.Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: retrofit2.Response<Any?>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Sample, "Success", Toast.LENGTH_SHORT).show()
                    val gson = Gson()
                    val json = gson.toJson(response.body())
                    val jsonObject = JSONObject(json)
                    val times = jsonObject.getJSONArray("times")
                    val switchs = jsonObject.getJSONArray("switchs")
                    val coments = jsonObject.getJSONArray("coments")
                    for (i in 0 until times.length()) {
                        getTimes.times = times.getString(i)
                        getTimes.switchs = switchs.getString(i)
                        getTimes.coments = coments.getString(i)
                        Toast.makeText(this@Sample, getTimes.times, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@Sample, "Error", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Any?>, t: Throwable) {
            }
        })
    }
}