package com.example.jsondeserializationstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

data class MyJSONDataClass(
    val data1: Int,
    val data2: String,
    val list: List<Int>)

data class MyJSONDataClass2(
    val nested: MyJSONDataClass)



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mapper = jacksonObjectMapper()
        val jsonString = """
            {
            "data1":1234,
            "data2":"Hello",
            "list":[1,2,3]
            }
            """.trimIndent()

        var mapper2 = jacksonObjectMapper()
        val jsonString2 = """
            {
            "nested":{
                "data1": 1234,
                "data2": "Hello",
                "list": [1,2,3]
                }
            }
            """.trimIndent()
        val d2 = mapper?.readValue<MyJSONDataClass2>(jsonString2)


       // Log.d("mytag", d1.toString())
        Log.d("mytag2", d2.toString())
    }
}