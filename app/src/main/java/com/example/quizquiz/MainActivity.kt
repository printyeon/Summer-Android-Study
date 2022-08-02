package com.example.quizquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.room.Entity

class MainActivity : AppCompatActivity() {
    lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.drawer_nav_view)

        //처음시작에 퀴즈 프레그먼트 뜸 그니까 이건 화면에 프레그먼트 띄우는 법
        supportFragmentManager.beginTransaction().add(
            R.id.frame,
            QuizFragment())
            .commit()

        //네비게이션 뷰에서 선택하면 실행행
       navView.setNavigationItemSelectedListener {
           when(it.itemId){
               R.id.quiz_solve -> {
                   supportFragmentManager
                       .beginTransaction()
                       .replace(R.id.frame, QuizFragment())
                       .commit()
               }
               R.id.quiz_manage -> {
                   supportFragmentManager
                       .beginTransaction()
                       .replace(R.id.frame, QuizListFragment()) //추가가 아니라 교체니까 replace
                       .commit()
               }
               }
           //메뉴 누르면 닫히게 하기
            drawerLayout.closeDrawers()

           //그냥..아묻따 true
            true
        }

        drawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.drawer_open,
            R.string.drawer_close,
        ){}

        //isDrawerIndicatorEnabled속성을 true로 설정해 액션바 왼쪽 상단위에 위치한 햄버거 아이콘을 통해 내비게이션 드로어를 표시하고 숨길 수 있도록함
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle)
        //setDisplayHomeAsUpEnabled 속성으로 햄버거 아이콘을표시하고 아이콘을 클릭해 내비게이션 드로어를 열고 닫을 수 있도록 설정
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        drawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}