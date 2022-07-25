package com.example.lotteryapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var currentNums: String //lateinit 나중에 접근하기 전에 꼬옥. 초기화 해줄게!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = getSharedPreferences("nums", Context.MODE_PRIVATE) //저장 할라고

        var numText = findViewById<TextView>(R.id.num)

        val numBtn = findViewById<Button>(R.id.numButton)

        //디폴트 번호 생성해주기
        currentNums = generateRandomLottoNum(6, "-")
        numText.text = currentNums

        numBtn.setOnClickListener{
            currentNums = generateRandomLottoNum(6, "-")
            numText.text = currentNums
        }

        val saveNumBtn = findViewById<Button>(R.id.saveButton)
        saveNumBtn.setOnClickListener {
            var lottoNums = pref.getString("lottonums", "")
            var numList = if(lottoNums == ""){
                mutableListOf<String>()
            }
            else{
                lottoNums!!.split(",").toMutableList() // !!-> 무조건 값이 있다고 확신
            }

            numList.add(currentNums)
            val editor = pref.edit()
            editor.putString("lottonums", numList.joinToString ("," ))
            editor.apply()
        }

        findViewById<Button>(R.id.checkButton).setOnClickListener{
            val intent = Intent(this, lotteryNumListActivity::class.java)
            startActivity(intent)
        }


    }

    fun generateRandomLottoNum(count:Int, sep:String = "-" ):String{
        var lottoNum = "" //그냥 새로운 문자열 만들기
        for(i in 0..count)
        {
            val rnds = (1..45).random()

            if(i!=count) lottoNum += "${rnds} ${sep} " //문자열 합치기
            else lottoNum += "${rnds}"
        }
        return lottoNum
    }

}