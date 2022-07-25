package com.example.lotteryapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class lotteryNumListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottery_num_list)

        val pref = getSharedPreferences("nums", Context.MODE_PRIVATE)
            var lottoNums = pref.getString("lottonums", "")
            var numList = if(lottoNums == ""){
                mutableListOf<String>()
            }
            else{
                lottoNums!!.split(",").toMutableList() // !!-> 무조건 값이 있다고 확신
            }

        val listView = findViewById<RecyclerView>(R.id.num_list)
        listView.setHasFixedSize(true)
        listView.layoutManager = LinearLayoutManager(this)

        listView.adapter = LotteryListAdapter(numList)
        listView.setHasFixedSize(true)
    }
}