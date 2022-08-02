package com.example.quizquiz.database

import android.content.Context
import androidx.room.*

//Data Access Object
@Dao
interface QuizDAO{
        @Insert
        fun insert(quiz: Quiz):Long

        @Update
        fun update(quiz: Quiz)

        @Delete
        fun delete(quiz: Quiz)

        @Query("SELECT * FROM quiz")
        fun getAll():List<Quiz>

}

@Database(entities = [Quiz::class], version = 1)
@TypeConverters(StringListTypeConverter::class)
abstract class QuizDatabase : RoomDatabase(){
        abstract fun quizDAO(): QuizDAO

        //정적 매서드(클래스 메서드)
        companion object{
                private var INSTANCE: QuizDatabase? = null

                //싱글톤 패턴 중요!!!!!!!!!!!!!!!!!!!!
                //객체의 인스턴스가 오직 1개만 생성되는 패턴
                fun getInstance(context: Context):QuizDatabase{
                        if(INSTANCE == null){
                                INSTANCE = Room.databaseBuilder(
                                        context.applicationContext,
                                        QuizDatabase::class.java, "database.db").build()
                        }
                        return INSTANCE!!
                }

        }
}