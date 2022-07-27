package com.example.weatherdustchecker

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.service.controls.templates.TemperatureControlTemplate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class WeatherPageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater
            .inflate(R.layout.weather_page_fragment,
            container, false
            )
        //arguments 값 참조해서 가져오고 해당하는 뷰에 출력해주기
        val statusText = view.findViewById<TextView>(R.id.weather_status_text)
        val temperatureText = view.findViewById<TextView>(R.id.weather_temp_text)
        val weathericon = view.findViewById<ImageView>(R.id.weather_icon)
        statusText.text = arguments?.getString("status")
        temperatureText.text = arguments?.getDouble("temperature").toString()

        //imageview 가져와서 sun 이미지 출력하기
        weathericon.setImageResource(arguments?.getInt("res_id")!!)

        return view
    }

    //newInstance 만들어서 fragment에서 뭐 할지 만들어주기
    companion object{
        fun newInstance(status:String, temperature:Double):WeatherPageFragment{
            val fragment = WeatherPageFragment()

            val args = Bundle()
            args.putString("status", status)
            args.putDouble("temperature", temperature)
            args.putInt("res_id", R.drawable.sun)
            fragment.arguments = args

            return fragment
        }
    }

}