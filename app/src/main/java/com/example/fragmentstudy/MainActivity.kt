package com.example.fragmentstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //커밋을 호출되기 전까지 비긴트레지션으로 프레그먼트를 어떻게 하겠다다
       val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, CurrencyConverterFragment1())
        transaction.commit()
    }
}