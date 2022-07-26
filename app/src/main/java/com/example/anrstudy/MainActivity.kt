package com.example.anrstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.math.sqrt
import kotlin.random.Random

//ANR => Android Not Responding
//메인스레드 에서는 짧은 시간에 끝나는 코드나 ui 조작 코드만 쓰고
//오래걸리는 작업은 스레드를 새로 만들어서 쓰기
//새로만든 스레드에서 ui작업하는 것은 허용되지 않음
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val result = findViewById<TextView>(R.id.result)
        findViewById<Button>(R.id.btn).setOnClickListener {
            Toast.makeText(
                this,
                "Clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }

        findViewById<Button>(R.id.anr).setOnClickListener {
            Thread(Runnable {
                var sum = 0.0
                for (i in 1..60) {
                    sum += sqrt(Random.nextDouble())
                    Thread.sleep(100)
                }
                Log.d("mytag", sum.toString())
                runOnUiThread{
                    result.text = sum.toString()
                }

            }).start()
        }
    }
}