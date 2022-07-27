package com.example.jsondeserializationstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

@JsonDeserialize(using = ComplexJSONDataDeserializer::class)

data class ComplexJSONData(
    val innerData: String,
    val data1: Int,
    val data2: String,
    val list: List<Int>
)

class ComplexJSONDataDeserializer : StdDeserializer<ComplexJSONData>(
    ComplexJSONData::class.java
){
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): ComplexJSONData {
        val node = p?.codec?.readTree<JsonNode>(p)
        val nestedNode = node?.get("nested")
        val innerDataValue = nestedNode?.get("inner_data")?.asText() //asText => toString
        val innerNestedNode = nestedNode?.get("inner_nested")
        val innerNestedData1 = innerNestedNode?.get("data1")?.asInt()
        val innerNestedData2 = innerNestedNode?.get("data2")?.asText()

        val list = mutableListOf<Int>()
        innerNestedNode?.get("list")?.elements()?.forEach {
            list.add(it.asInt())
        }

        return ComplexJSONData(
            innerDataValue!!,
            innerNestedData1!!,
            innerNestedData2!!,
            list
        )
    }

}
data class MyJSONDataClass(
    val data1: Int,
    val data2: String,
    val list: List<Int>)

data class MyJSONDataClass2(
    val nested: MyJSONDataClass)

data class MyJSONDataClass4(
    val city: String,
    val lat: Float,
    val lon: Float)

data class MyJSONDataClass3(
    val name: String,
    val age: Int,
    val favorites: List<String>,
    val address: MyJSONDataClass4)






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

        val personString = """
            {
                "name": "John",
                "age": 20,
                "favorites": ["study","game"],
                "address":{
                    "city": "Seoul",
                    "lat":0.0,
                    "lon":1.0
                }
            }
            """.trimIndent()
        val d3 = mapper?.readValue<MyJSONDataClass3>(personString)

        val ComplexJsonString = """
            {
                "nested":{
                    "inner_data": "Hello From inner",
                    "inner_nested":{
                        "data1": 1234,
                        "data2": "Hello",
                        "list": [1,2,3]
                    }
                }
            }
            """.trimIndent()

        val complex = mapper?.readValue<ComplexJSONData>(ComplexJsonString)
       // Log.d("mytag", d1.toString())
        //Log.d("mytag2", d2.toString())
       // Log.d("mytag3", d3.toString())
        Log.d("mytag4", complex.toString())
    }
}