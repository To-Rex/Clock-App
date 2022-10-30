package app.clock.alarmclock.adapters

import android.view.View
import android.widget.BaseAdapter
import android.view.ViewGroup

class DataAdapters : BaseAdapter() {
    override fun getCount(): Int {
        return 0
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        return View(parent.context)
    }
}