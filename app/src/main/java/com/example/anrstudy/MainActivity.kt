package com.example.anrstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlin.math.sqrt
import kotlin.random.Random

//ANR => Android Not Responding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn).setOnClickListener {
            Toast.makeText(
                this,
                "Clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
        findViewById<Button>(R.id.anr).setOnClickListener {
            Thread(Runnable {
                for (i in 1..60) {
                    Log.d("mytag", sqrt(Random.nextDouble()).toString())
                    Thread.sleep(1000)
                }


            }).start()
        }
    }
}