package app.clock.alarmclock

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import app.clock.alarmclock.adapters.DataAdapters
import app.clock.alarmclock.cleint.ApiCleint
import app.clock.alarmclock.models.GetTimes
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call

class Sample : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    private var token = ""
    var listSample: ListView? = null
    private var dataAdapters: DataAdapters? = null
    private var timeList: ArrayList<GetTimes>? = null
    private var imgSampleSet: ImageView? = null
    private var progressSample: ProgressBar? = null
    private var floatRefresh: FloatingActionButton? = null
    private var floatAdd: FloatingActionButton? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sample)

        listSample = findViewById(R.id.listSample)
        progressSample = findViewById(R.id.progressSampe)
        imgSampleSet = findViewById(R.id.imgSamleSet)
        floatRefresh = findViewById(R.id.floatRefresh)
        floatAdd = findViewById(R.id.floatAdd)

        sharedPreferences = getSharedPreferences("app.clock.alarmClock", MODE_PRIVATE)
        token = sharedPreferences?.getString("token", "")!!

        timeList = ArrayList()
        dataAdapters = DataAdapters(this, timeList!!)
        listSample?.adapter = dataAdapters
        listSample?.divider = null
        listSample?.dividerHeight = 20

        getAllTimes()

        imgSampleSet?.setOnClickListener {
            startActivity(Intent(this, SettingsPage::class.java))
        }

        floatRefresh?.setOnClickListener {
            timeList?.clear()
            dataAdapters?.notifyDataSetChanged()
            getAllTimes()
        }
        floatAdd?.setOnClickListener {
            addTime()
        }
    }
    private fun getAllTimes() {
        val getTime: Call<Any?>? = ApiCleint().userService.gettimes("Bearer $token")
        getTime?.enqueue(object : retrofit2.Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: retrofit2.Response<Any?>) {
                if (response.isSuccessful) {
                    listSample?.visibility = View.VISIBLE
                    progressSample?.visibility = View.GONE
                    val gson = Gson()
                    val json = gson.toJson(response.body())
                    val jsonObject = JSONObject(json)
                    val times = jsonObject.getJSONArray(getString(R.string.time))
                    val switchS = jsonObject.getJSONArray(getString(R.string.switchCheck))
                    val comments = jsonObject.getJSONArray(getString(R.string.comment))
                    for (i in 0 until times.length()) {
                        val time = times.getString(i)
                        val switchBox = switchS.getString(i)
                        val coMntS = comments.getString(i)
                        timeList?.add(GetTimes(time, coMntS,switchBox))
                        dataAdapters?.notifyDataSetChanged()
                    }
                    dataAdapters?.notifyDataSetChanged()
                }else{
                    Toast.makeText(this@Sample, "Error", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Any?>, t: Throwable) {
            }
        })
    }

    @SuppressLint("InflateParams", "MissingInflatedId", "NewApi")
    private fun addTime(){
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.add_item, null)

        val ediSamComment = view.findViewById<EditText>(R.id.ediSamComment)
        val btnSamAdd = view.findViewById<Button>(R.id.btnSamAdd)
        val digitalClock = view.findViewById<TimePicker>(R.id.digitalClock)

        digitalClock.setIs24HourView(true)
        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(view)

        var comment = ediSamComment.text.toString()
        val time = digitalClock.hour.toString() + ":" + digitalClock.minute.toString()
        btnSamAdd.setOnClickListener {
            Toast.makeText(this, time, Toast.LENGTH_SHORT).show()
        }

        val dialog = addDialog.create()
        dialog.show()

    }
}