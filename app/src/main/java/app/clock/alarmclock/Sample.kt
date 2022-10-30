package app.clock.alarmclock

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import app.clock.alarmclock.adapters.DataAdapters
import app.clock.alarmclock.cleint.ApiCleint
import app.clock.alarmclock.models.GetTimes
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call

class Sample : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    var token = ""
    var listSample: ListView? = null
    private var dataAdapters: DataAdapters? = null
    private var timeList: ArrayList<GetTimes>? = null
    private var imgSamleSet: ImageView? = null
    private var progressSampe: ProgressBar? = null
    private var isLoading: Boolean = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sample)

        listSample = findViewById(R.id.listSample)
        progressSampe = findViewById(R.id.progressSampe)
        imgSamleSet = findViewById(R.id.imgSamleSet)

        sharedPreferences = getSharedPreferences("app.clock.alarmClock", MODE_PRIVATE)
        token = sharedPreferences?.getString("token", "")!!

        timeList = ArrayList()
        dataAdapters = DataAdapters(this, timeList!!)
        listSample?.adapter = dataAdapters
        listSample?.divider = null
        listSample?.dividerHeight = 20

        getAllTimes()

        imgSamleSet?.setOnClickListener {
            startActivity(Intent(this, SettingsPage::class.java))
        }

    }
    private fun getAllTimes() {
        val getTimes = GetTimes("","","")
        val gettime: Call<Any?>? = ApiCleint().userService.gettimes("Bearer $token")
        gettime?.enqueue(object : retrofit2.Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: retrofit2.Response<Any?>) {
                if (response.isSuccessful) {
                    listSample?.visibility = View.VISIBLE
                    progressSampe?.visibility = View.GONE
                    val gson = Gson()
                    val json = gson.toJson(response.body())
                    val jsonObject = JSONObject(json)
                    val times = jsonObject.getJSONArray("times")
                    val switchs = jsonObject.getJSONArray("switchs")
                    val coments = jsonObject.getJSONArray("coments")
                    for (i in 0 until times.length()) {
                        val time = times.getString(i)
                        val switch = switchs.getString(i)
                        val coment = coments.getString(i)
                        getTimes.times = time
                        getTimes.switchs = switch
                        getTimes.coments = coment
                        timeList?.add(getTimes)
                        dataAdapters?.notifyDataSetChanged()
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