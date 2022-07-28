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

@JsonDeserialize(using = MyDeserializer2::class)
data class OpenDustAPIJSONResponse(val pm10: Int, val pm25: Int)

class MyDeserializer2 : StdDeserializer<OpenDustAPIJSONResponse>(
    OpenDustAPIJSONResponse::class.java
) {
    override fun deserialize(
        p: JsonParser?,
        ctxt: DeserializationContext?
    ): OpenDustAPIJSONResponse {
        val node = p?.codec?.readTree<JsonNode>(p)

        val data = node?.get("data")
        val iaqi = data?.get("iaqi")
        val pm10 = iaqi?.get("pm10")
        val pm25 = iaqi?.get("pm25")
        val pm10Val = pm10?.get("v")?.asInt()
        val pm25Val = pm25?.get("v")?.asInt()

        return OpenDustAPIJSONResponse(pm10Val!!, pm25Val!!)
    }
}


class DustTextFragment :Fragment() {
    lateinit var statusText: TextView
    lateinit var dustText: TextView
    lateinit var dustImage :ImageView
    lateinit var normalDustText: TextView
    lateinit var normalStatusText: TextView

    var APP_ID = "7a12a6ef2ed34f508049298ab863f4dbe4f7e952"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater
            .inflate(
                R.layout.dust_test_fragment,
                container, false
            )
        statusText = view.findViewById<TextView>(R.id.dust_status_text)
        dustText = view.findViewById<TextView>(R.id.dust_temp_text)
        dustImage = view.findViewById<ImageView>(R.id.dust_icon)
        normalStatusText = view.findViewById<TextView>(R.id.normal_dust_status_text)
        normalDustText = view.findViewById<TextView>(R.id.normal_dust_temp_text)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lat = arguments?.getDouble("lat")
        val lon = arguments?.getDouble("lon")
        val url = "https://api.waqi.info/feed/geo:${lat};${lon}/?token=${APP_ID}"
        APICall(object : APICall.APICallback {
            override fun onComplete(result: String) {
                Log.d("mytag", result)
                var mapper = jacksonObjectMapper()
                var data = mapper?.readValue<OpenDustAPIJSONResponse>(result)

                //Log.d("mytag", data.pm10.toString())
                //Log.d("mytag", data.pm25.toString())



                val pm10 = data.pm10
                val pm25 = data.pm25
                dustText.text = pm25.toString()
                normalDustText.text = pm10.toString()

                if(pm25 != null){
                    if(pm25 <= 50){
                        statusText.text = "좋음(초미세먼지)"
                        dustImage.setImageResource(R.drawable.good)

                    }
                    else if(pm25 <=150){
                        statusText.text = "보통(초미세먼지)"
                        dustImage.setImageResource(R.drawable.normal)

                    }
                    else if(pm25 <=300){
                        statusText.text = "나쁨(초미세먼지)"
                        dustImage.setImageResource(R.drawable.bad)

                    }
                    else{
                        statusText.text = "매우나쁨(초미세먼지)"
                        dustImage.setImageResource(R.drawable.very_bad)
                    }

                }

                if(pm10 != null){
                    if(pm10 <= 50){
                        normalStatusText.text = "좋음(미세먼지)"

                    }
                    else if(pm10 <=150){
                        normalStatusText.text = "보통(미세먼지)"

                    }
                    else if(pm10 <=300){
                        normalStatusText.text = "나쁨(미세먼지)"

                    }
                    else{
                        normalStatusText.text = "매우나쁨(미세먼지)"
                    }

                }


            }
        }).execute(URL(url))
    }
    companion object{
        fun newInstance(lat:Double, lon:Double):DustTextFragment{
            val fragment = DustTextFragment()

            val args = Bundle()
            args.putDouble("lat", lat)
            args.putDouble("lon", lon)
            fragment.arguments = args

            return fragment
        }
    }
}



