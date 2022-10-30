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

class DataAdapters(private val context: Activity, private val list:ArrayList<GetTimes>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "InflateParams", "MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = context.layoutInflater.inflate(R.layout.times_list, null)

        val times = view.findViewById<View>(R.id.txtTimes) as TextView
        val txtComents = view.findViewById<View>(R.id.txtComent) as TextView
        val getTimes = list[position]
        times.text = getTimes.times
        txtComents.text = getTimes.coments

        return view
    }

}