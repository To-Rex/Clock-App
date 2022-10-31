package app.clock.alarmclock.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import app.clock.alarmclock.R
import app.clock.alarmclock.cleint.ApiCleint
import app.clock.alarmclock.models.GetTimes
import retrofit2.Call

class DataAdapters(context: Context, timeList: ArrayList<GetTimes>) : BaseAdapter() {

    private var context: Context? = null
    private var timeList: ArrayList<GetTimes>? = null
    private var sharedPreferences: SharedPreferences? = null

    init {
        this.context = context
        this.timeList = timeList
    }

    override fun getCount(): Int {
        return timeList?.size!!
    }

    override fun getItem(position: Int): Any {
        return timeList?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint(
        "ViewHolder",
        "UseSwitchCompatOrMaterialCode",
        "InflateParams",
        "MissingInflatedId", "NewApi"
    )
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = (context as Activity).layoutInflater.inflate(R.layout.times_list, null)
        val txtTime = view.findViewById<TextView>(R.id.txtTimes)
        val txtComents = view.findViewById<View>(R.id.txtComent) as TextView
        val switchItem = view.findViewById<View>(R.id.switchItem) as Switch
        sharedPreferences = (context as Activity).getSharedPreferences(
            "app.clock.alarmClock",
            AppCompatActivity.MODE_PRIVATE
        )
        val token = sharedPreferences?.getString("token", "")!!

        txtTime.text = timeList?.get(position)?.times
        txtComents.text = timeList?.get(position)?.coments
        switchItem.isChecked = timeList?.get(position)?.switchs == "true"

        switchItem.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val getTimes = GetTimes(
                    timeList?.get(position)?.times!!,
                    timeList?.get(position)?.coments!!,
                    "true"
                )
                val updateTimeResponse: Call<Any?>? =
                    ApiCleint().userService.updateTime(position, "Bearer $token", getTimes)
                updateTimeResponse?.enqueue(object : retrofit2.Callback<Any?> {
                    override fun onResponse(
                        call: Call<Any?>,
                        response: retrofit2.Response<Any?>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Vaqt o`zgartirildi", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(context, "Xatolik", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Any?>, t: Throwable) {
                        Toast.makeText(context, "Nimadur Xato ketdi", Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                val getTimes = GetTimes(
                    timeList?.get(position)?.times!!,
                    timeList?.get(position)?.coments!!,
                    "false"
                )
                val updateTimeResponse: Call<Any?>? =
                    ApiCleint().userService.updateTime(position, "Bearer $token", getTimes)
                updateTimeResponse?.enqueue(object : retrofit2.Callback<Any?> {
                    override fun onResponse(
                        call: Call<Any?>,
                        response: retrofit2.Response<Any?>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Vaqt o`zgartirildi", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(context, "Xatolik", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Any?>, t: Throwable) {
                        Toast.makeText(context, "Nimadur Xato ketdi", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
        view.setOnClickListener {
            val inflater = LayoutInflater.from(context)
            val views = inflater.inflate(R.layout.edit_item, null)
            val addDialog = AlertDialog.Builder(context as Activity)
            addDialog.setView(views)
            val dialog = addDialog.create()

            val imgEtemDelete = views.findViewById<ImageView>(R.id.imgEtemDelete)
            val imgEtemEdit = views.findViewById<ImageView>(R.id.imgEtemEdit)
            val txtEtemComment = views.findViewById<TextView>(R.id.txtEtemComment)
            val timePickerEtem = views.findViewById<TimePicker>(R.id.timePickerEtem)
            val progresEtemBar = views.findViewById<ProgressBar>(R.id.progresEtemBar)

            timePickerEtem.setIs24HourView(true)
            txtEtemComment.text = timeList?.get(position)?.coments

            val time = timeList?.get(position)?.times?.split(":")
            timePickerEtem.hour = time?.get(0)?.toInt()!!
            timePickerEtem.minute = time[1].toInt()

            imgEtemDelete.setOnClickListener {
                AlertDialog.Builder(context as Activity)
                    .setTitle("Diqqat!")
                    .setMessage("Siz rostdan ham ushbu vaqtni o'chirmoqchimisiz?")
                    .setPositiveButton("OK") { dialogs, _ ->
                        progresEtemBar.visibility = View.VISIBLE
                        imgEtemDelete.visibility = View.GONE
                        imgEtemEdit.visibility = View.GONE
                        txtEtemComment.visibility = View.GONE
                        timePickerEtem.visibility = View.GONE
                        val deleteResponse: Call<Any?>? =
                            ApiCleint().userService.deleteTime(position, "Bearer $token")
                        deleteResponse?.enqueue(object : retrofit2.Callback<Any?> {
                            override fun onResponse(
                                call: Call<Any?>,
                                response: retrofit2.Response<Any?>
                            ) {
                                if (response.isSuccessful) {
                                    Toast.makeText(context, "Vaqt o`chirildi", Toast.LENGTH_SHORT)
                                        .show()
                                    dialogs.dismiss()
                                    dialog.dismiss()
                                } else {
                                    progresEtemBar.visibility = View.GONE
                                    imgEtemDelete.visibility = View.VISIBLE
                                    imgEtemEdit.visibility = View.VISIBLE
                                    txtEtemComment.visibility = View.VISIBLE
                                    timePickerEtem.visibility = View.VISIBLE
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<Any?>, t: Throwable) {
                                progresEtemBar.visibility = View.GONE
                                imgEtemDelete.visibility = View.VISIBLE
                                imgEtemEdit.visibility = View.VISIBLE
                                txtEtemComment.visibility = View.VISIBLE
                                timePickerEtem.visibility = View.VISIBLE
                                Toast.makeText(context, "Nimadur Xato ketdi", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        })
                        dialog.dismiss()
                    }
                    .setNegativeButton("Bekor qilish") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

            imgEtemEdit.setOnClickListener {
                dialog.dismiss()
                val inflaters = LayoutInflater.from(context)
                val viewEdit = inflaters.inflate(R.layout.add_item, null)
                val addDialogs = AlertDialog.Builder(context as Activity)
                addDialogs.setView(viewEdit)

                val ediSamComment = viewEdit.findViewById<EditText>(R.id.ediSamComment)
                val btnSamAdd = viewEdit.findViewById<Button>(R.id.btnSamAdd)
                val digitalClock = viewEdit.findViewById<TimePicker>(R.id.digitalClock)

                digitalClock.setIs24HourView(true)
                ediSamComment.setText(timeList?.get(position)?.coments)
                val list = timeList?.get(position)?.times?.split(":")
                digitalClock.hour = list?.get(0)?.toInt()!!
                digitalClock.minute = list[1].toInt()
                btnSamAdd.setOnClickListener {
                    val getTimes = GetTimes(
                        "${digitalClock.hour}:${digitalClock.minute}",
                        ediSamComment.text.toString(),
                        timeList?.get(position)!!.switchs
                    )
                    val updateTimeResponse: Call<Any?>? =
                        ApiCleint().userService.updateTime(position, "Bearer $token", getTimes)
                    updateTimeResponse?.enqueue(object : retrofit2.Callback<Any?> {
                        override fun onResponse(
                            call: Call<Any?>,
                            response: retrofit2.Response<Any?>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "Vaqt o`zgartirildi", Toast.LENGTH_SHORT)
                                    .show()
                                dialog.dismiss()
                            } else {
                                Toast.makeText(context, "Xatolik", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Any?>, t: Throwable) {
                            Toast.makeText(context, "Nimadur Xato ketdi", Toast.LENGTH_SHORT).show()
                        }
                    })
                }

                val dialogs = addDialogs.create()
                dialogs.show()
            }

            dialog.show()
        }
        return view
    }
}