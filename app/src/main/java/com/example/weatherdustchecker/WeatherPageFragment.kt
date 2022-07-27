package com.example.weatherdustchecker

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.service.controls.templates.TemperatureControlTemplate
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.net.URL

class WeatherPageFragment : Fragment() {
    lateinit var statusText :TextView
    lateinit var temperatureText :TextView
    lateinit var weatherImage :ImageView
    var APP_ID = "0f3ff41f65fd5d5c4cd6098b20374099"

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
        statusText = view.findViewById<TextView>(R.id.weather_status_text)
        temperatureText = view.findViewById<TextView>(R.id.weather_temp_text)
        weatherImage = view.findViewById<ImageView>(R.id.weather_icon)


//        statusText.text = arguments?.getString("status")
//        temperatureText.text = arguments?.getDouble("temperature").toString()
//
//        //imageview 가져와서 sun 이미지 출력하기
//        weathericon.setImageResource(arguments?.getInt("res_id")!!)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lat = arguments?.getDouble("lat")
        val lon = arguments?.getDouble("lon")
        var url = "http://api.openweathermap.org/data/2.5/weather?units=metric&appid=${APP_ID}&lat=${lat}&lon=${lon}"

        APICall(object :APICall.APICallback{
            override fun onComplete(result: String) {
                Log.d("mytag",result)
            }
        }).execute(URL(url))
    }

    //newInstance 만들어서 fragment에서 뭐 할지 만들어주기
    companion object{
        fun newInstance(lat:Double, lon:Double):WeatherPageFragment{
            val fragment = WeatherPageFragment()

            val args = Bundle()
            args.putDouble("lat", lat)
            args.putDouble("lon", lon)
            fragment.arguments = args

            return fragment
        }
    }

}