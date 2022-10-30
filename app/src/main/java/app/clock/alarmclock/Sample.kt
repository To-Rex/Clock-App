package app.clock.alarmclock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.clock.alarmclock.cleint.ApiCleint
import app.clock.alarmclock.models.GetTimes
import retrofit2.Call

class Sample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sample)

        val gettime: Call<GetTimes>? = ApiCleint().userService.gettimes()
        gettime?.enqueue(object : retrofit2.Callback<GetTimes?> {
            override fun onResponse(call: Call<GetTimes?>, response: retrofit2.Response<GetTimes?>) {
                if (response.isSuccessful) {
                    val getTimes: GetTimes? = response.body()
                    Toast.makeText(this@Sample, getTimes?.times.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<GetTimes?>, t: Throwable) {
            }
        })
    }
}