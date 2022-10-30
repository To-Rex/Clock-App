package app.clock.alarmclock

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.clock.alarmclock.cleint.ApiCleint
import app.clock.alarmclock.models.GetTimes
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call

class Sample : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sample)
        sharedPreferences = getSharedPreferences("app.clock.alarmClock", MODE_PRIVATE)
        token = sharedPreferences?.getString("token", "")!!
        val getTimes = GetTimes("","","")
        val gettime: Call<Any?>? = ApiCleint().userService.gettimes("Bearer $token")
        gettime?.enqueue(object : retrofit2.Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: retrofit2.Response<Any?>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Sample, "Success", Toast.LENGTH_SHORT).show()
//                    val gson = Gson()
//                    val json = gson.toJson(response.body())
//                    val jsonObject = JSONObject(json)
//                    val data = jsonObject.getJSONObject("data")
//                    val times = data.getJSONArray("times")
//                    for (i in 0 until times.length()) {
//                        val time = times.getJSONObject(i)
//                        val id = time.getString("id")
//                        val time = time.getString("time")
//                        val status = time.getString("status")
//                        val getTimes = GetTimes(id,time,status)
//                        getTimesList.add(getTimes)
//                    }
                }else{
                    Toast.makeText(this@Sample, "Error", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Any?>, t: Throwable) {
            }
        })
    }
}