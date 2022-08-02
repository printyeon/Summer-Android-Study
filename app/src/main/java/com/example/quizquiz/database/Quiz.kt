package com.example.quizquiz.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName="quiz")
data class Quiz (

    @PrimaryKey(autoGenerate = true) //id
    var id: Long? = null,
    // 퀴즈의 종류(OX, N지선다)
    var type: String?,
    // 발문
    var question: String?,
    // 정답
    var answer: String?,
    // 퀴즈의 카테고리
    var category: String?,
    // N지선다 문제의 선택지
    @TypeConverters(StringListTypeConverter::class)
    var guesses: List<String>? = null

) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Quiz> {
        override fun createFromParcel(parcel: Parcel): Quiz {
            return Quiz(parcel)
        }

        override fun newArray(size: Int): Array<Quiz?> {
            return arrayOfNulls(size)
        }
    }

}
class StringListTypeConverter {
    @TypeConverter
    fun stringListToString(stringList: List<String>?): String? {
        return stringList?.joinToString(",") //객체에서 테이블로 ,로 연결
    }
    @TypeConverter
    fun stringToStringList(string: String?): List<String>? {
        return string?.split(",")?.toList()//테이블에서 객체로 ,로 나누기
    }
}