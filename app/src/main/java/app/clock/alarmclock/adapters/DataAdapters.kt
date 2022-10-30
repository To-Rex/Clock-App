package app.clock.alarmclock.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.widget.TextView
import app.clock.alarmclock.R
import app.clock.alarmclock.models.GetTimes

class DataAdapters(context: Context, timeList: ArrayList<GetTimes>) : BaseAdapter() {

    private var context: Context? = context
    private var timeList: ArrayList<GetTimes>? = timeList

    override fun getCount(): Int {
        return timeList!!.size
    }

    override fun getItem(position: Int): Any {
        return timeList!!.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = (context as Activity).layoutInflater.inflate(R.layout.times_list, null)
        //val view = context.layoutInflater.inflate(R.layout.times_list, null)

        val times = view.findViewById<View>(R.id.txtTimes) as TextView
        val txtComents = view.findViewById<View>(R.id.txtComent) as TextView
        val switchItem = view.findViewById<View>(R.id.switchItem) as TextView
        val getTimes = timeList!![position]
        times.text = getTimes.times
        txtComents.text = getTimes.coments
        switchItem.isClickable = getTimes.switchs == "true"

        return view
    }
}