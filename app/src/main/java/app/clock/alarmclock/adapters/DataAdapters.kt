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

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = (context as Activity).layoutInflater.inflate(R.layout.times_list, null)
        val txtTime = view.findViewById<TextView>(R.id.txtTimes)
        val txtComents = view.findViewById<View>(R.id.txtComent) as TextView
        val switchItem = view.findViewById<View>(R.id.switchItem) as TextView

        txtTime.text = timeList?.get(position)?.times
        txtComents.text = timeList?.get(position)?.coments
        switchItem.isClickable = timeList?.get(position)?.switchs == "true"
        return view
    }
}
//    private var context: Context? = context
//    private var timeList: ArrayList<GetTimes>? = timeList
//
//    override fun getCount(): Int {
//        return timeList!!.size
//    }
//
//    override fun getItem(position: Int): Any {
//        return timeList!![position]
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    @SuppressLint("ViewHolder", "InflateParams")
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val view: View = (context as Activity).layoutInflater.inflate(R.layout.times_list, null)
//        val times = view.findViewById<View>(R.id.txtTimes) as TextView
//        val txtComents = view.findViewById<View>(R.id.txtComent) as TextView
//        val switchItem = view.findViewById<View>(R.id.switchItem) as TextView
//
//        times.text = timeList!![position].times
//        txtComents.text = timeList!![position].coments
//
//        return view
//    }
