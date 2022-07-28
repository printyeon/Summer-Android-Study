package com.example.weatherdustchecker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URL

@JsonDeserialize(using = DustCheckerResponseDeserializer::class)
//data class 보통 값을 저장만 하는 클래스
data class DustCheckResponse(val pm10:Int, val pm25:Int,
                             val pm10Status: String,
                             val pm25Status: String
)

class DustCheckerResponseDeserializer :
        StdDeserializer<DustCheckResponse>(DustCheckResponse::class.java){
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): DustCheckResponse {
        
        //미리 좋음 나쁨 나눠주는 함수 만들기
        fun checkCategory(aqi:Int):String{
            return when(aqi){
                in(0..100) -> "좋음"
                in(101..200) -> "보통"
                in(201..300) -> "나쁨"
                else -> "매우 나쁨"
            }
        }

        val node = p?.codec?.readTree<JsonNode>(p)

        val data = node?.get("data")
        val iaqi = data?.get("iaqi")
        val pm10 = iaqi?.get("pm10")?.get("v")?.asInt()
        val pm25 = iaqi?.get("pm25")?.get("v")?.asInt()



        return DustCheckResponse(pm10!!, pm25!!, checkCategory(pm10!!), checkCategory(pm25!!))
    }
        }


class DustPageFragment : Fragment() {
    val APP_TOKEN = "7a12a6ef2ed34f508049298ab863f4dbe4f7e952"
    lateinit var statusImage : ImageView
    lateinit var pm25StatusText : TextView
    lateinit var pm25IntensityText : TextView
    lateinit var pm10StatusText : TextView
    lateinit var pm10IntensityText : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater
            .inflate(
                R.layout.dust_page_fragment,
                container, false
            )

        statusImage = view.findViewById<ImageView>(R.id.dust_status_icon)
        pm25StatusText = view.findViewById<TextView>(R.id.dust_pm25_status_text)
        pm25IntensityText = view.findViewById<TextView>(R.id.dust_pm25_intensity_text)
        pm10StatusText = view.findViewById<TextView>(R.id.dust_pm10_status_text)
        pm10IntensityText = view.findViewById<TextView>(R.id.dust_pm10_intensity_text)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lat = arguments?.getDouble("lat")
        val lon = arguments?.getDouble("lon")
        val url = "https://api.waqi.info/feed/geo:${lat};${lon}/?token=${APP_TOKEN}"



        //Log.d("Mytag", url)
        APICall(object : APICall.APICallback {
            override fun onComplete(result: String) {
                //Log.d("Mytag", result)

                val mapper = jacksonObjectMapper()
                var data = mapper?.readValue<DustCheckResponse>(result)

                statusImage.setImageResource(when(data.pm25Status){
                    "좋음"->R.drawable.good
                    "보통"->R.drawable.normal
                    "나쁜"->R.drawable.bad
                    else -> R.drawable.very_bad
                })
                pm25IntensityText.text = data.pm25?.toString()
                pm10IntensityText.text = data.pm10?.toString()

                pm25StatusText.text = "${data.pm25Status}(초미세먼지)"
                pm10StatusText.text = "${data.pm10Status}(미세먼지)"
            }
        }).execute(URL(url))
    }

    companion object{
        fun newInstance(lat:Double, lon:Double):DustPageFragment{
            val fragment = DustPageFragment()

            val args = Bundle()
            args.putDouble("lat", lat)
            args.putDouble("lon", lon)
            fragment.arguments = args



            return fragment
        }
    }
}