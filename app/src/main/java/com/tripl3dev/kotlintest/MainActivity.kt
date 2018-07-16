package com.tripl3dev.kotlintest

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val map: LinkedHashMap<TimeLineModelI, ArrayList<TimeLineModelI>> = LinkedHashMap()
        for (x in 1..4) {
            var timeLineModel = TimeLineModel(true, " هيدر $x")

            var list: ArrayList<TimeLineModelI> = ArrayList()

            for (i in 1..11) {
                var time = TimeLineModel(false, "ايتم $i  هيدر  $x")
                time.setType(x)
                list.add(time)
            }
            map[timeLineModel] = list
        }

        dataList.setOnStatusListener { v, item, status ->

            var titleText = v.findViewById<TextView>(R.id.title)
            when (status) {
                Constants.ViewStatus.CHECKED -> {
                    if(item.type() == 1)
                    titleText.setTextColor(Color.RED)
                    else
                        titleText.setTextColor(Color.BLACK)
                }
                Constants.ViewStatus.UNCHECKED -> titleText.setTextColor(Color.BLUE)
                else -> titleText.setTextColor(Color.GREEN)
            }

        }
        dataList.setLists(map)
    }


    fun getChecked(v: View) {
        var checkedList = TimeLineListView.getArrayList(dataList.checkedList)
        if (checkedList.isNotEmpty()) {
            var ss = ""
            for (i in 0 until checkedList.size) {
                ss += checkedList[i].title + " "
            }
            Log.e("Checked", ss)
        } else
            Log.e("Checked", "isEmpty")
    }
}
