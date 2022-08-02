package com.example.githubusersearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userIdInput = findViewById<TextView>(R.id.github_search_text)
        val content = findViewById<TextView>(R.id.content)
        val pimage = findViewById<ImageView>(R.id.profile_image);

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(
               GsonConverterFactory.create(
                   /*
                GsonBuilder().registerTypeAdapter(
                    GitHubUser::class.java,
                    GitHubUserDeserializer()
                ).create()
                */

               )
            ).build()

            val apiService = retrofit.create(GitHubAPIService::class.java)
            findViewById<Button>(R.id.github_search_btn).setOnClickListener{
                var id = userIdInput.text
                var apiCallForData = apiService.getUser(id.toString(), "token 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")

                apiCallForData.enqueue(object : Callback<GitHubUser>{
                    override fun onResponse(
                        call: Call<GitHubUser>,
                        response: Response<GitHubUser>
                    ) {
                        val code :String = response.code().toString()
                        if(code.startsWith("4")) {
                            Log.d("mytag", response.code().toString())
                            Toast.makeText(this@MainActivity, "유저가 없습니다", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            val data = response.body()!!
                            Log.d("mytag", data.toString())

                            content.text = "login : ${data.login}\n"+
                                    "id : ${data.id}\n"+
                                    "name : ${data.name}\n"+
                                    "followers : ${data.followers}\n"+
                                    "following : ${data.following}\n"

                            //this는 object 콜백타입 말하고 있음 근데 바깥에 있는 메인 this 쓸려고 @MainActivity 사용
                            Glide.with(this@MainActivity)
                                .load(data.avatarUrl)
                                .into(pimage)
                        }


                    }


                    override fun onFailure(call: Call<GitHubUser>, t: Throwable) {
                    }

                })

            }

            //val classInfo : Class<GitHubAPIService> = GitHubAPIService::class.java

            findViewById<Button>(R.id.github_gitrepos_btn).setOnClickListener{
                val nextIntent = Intent(this, GitHubUserRepositoryListActivity::class.java)
                nextIntent.putExtra("userid", userIdInput.text.toString())
                startActivity(nextIntent)
            }




    }
}