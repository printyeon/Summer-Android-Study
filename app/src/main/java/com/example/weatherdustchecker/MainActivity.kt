package com.example.weatherdustchecker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.lang.reflect.Array.newInstance
import javax.xml.datatype.DatatypeFactory.newInstance

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //상단 제목 표시줄 숨기기
        supportActionBar?.hide()

        //WeatherPageFragment 표시하기 (FrameLayout)
        val transaction = supportFragmentManager.beginTransaction()

        //newInstance 클래스 메서드 정의해서 status 값(문자열) temperature 값 전달할 수 있도록 해주기
        transaction.add(R.id.fragment_container,
            DustTextFragment.newInstance(37.58, 126.98))
        transaction.commit()
    }
}