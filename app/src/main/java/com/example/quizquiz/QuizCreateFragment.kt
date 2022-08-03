package com.example.quizquiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.quizquiz.database.Quiz
import com.example.quizquiz.database.QuizDatabase
import java.lang.Exception

class QuizCreateFragment :Fragment(){

    lateinit var db: QuizDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.quiz_create_fragment, container, false)

        db = QuizDatabase.getInstance(requireContext())

        view.findViewById<Button>(R.id.addQuiz).setOnClickListener {
            // Edittext에 있는 값들 모두 추출해서 문자열로 변환
            val question = view.findViewById<EditText>(R.id.question).text.toString()
            val answer = view.findViewById<EditText>(R.id.answer).text.toString()
            val category = view.findViewById<EditText>(R.id.category).text.toString()
            
            // Quiz 객체를 만들어야 됨
            val quiz = Quiz(type = "ox",
                question = question,
                answer = answer,
                category = category)

            // Quiz 객체를 DB에 추가(insert) 스레드 사용하기ㅏ
            Thread {
                db.quizDAO().insert(quiz)
            }.start()

            Toast.makeText(requireContext(), "추가되었습니다", Toast.LENGTH_SHORT).show()
        }
        return view
    }

}