package app.clock.alarmclock.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import app.clock.alarmclock.R
import app.clock.alarmclock.models.GetTimes

class DataAdapters(context: Context, timeList: ArrayList<GetTimes>) : BaseAdapter() {

    private var context: Context? = null
    private var timeList: ArrayList<GetTimes>? = null

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

    @SuppressLint("ViewHolder", "UseSwitchCompatOrMaterialCode", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = (context as Activity).layoutInflater.inflate(R.layout.times_list, null)
        val txtTime = view.findViewById<TextView>(R.id.txtTimes)
        val txtComents = view.findViewById<View>(R.id.txtComent) as TextView
        val switchItem = view.findViewById<View>(R.id.switchItem) as Switch

        txtTime.text = timeList?.get(position)?.times
        txtComents.text = timeList?.get(position)?.coments
        switchItem.isChecked = timeList?.get(position)?.switchs == "true"

        view.setOnClickListener {
            Toast.makeText(context, timeList?.get(position)?.times, Toast.LENGTH_SHORT).show()
            val inflater = LayoutInflater.from(context)
            val views = inflater.inflate(R.layout.add_item, null)
            val addDialog = AlertDialog.Builder(context as Activity)

            addDialog.setView(views)
            val dialog = addDialog.create()
            dialog.show()
        }

        return view
    }
}