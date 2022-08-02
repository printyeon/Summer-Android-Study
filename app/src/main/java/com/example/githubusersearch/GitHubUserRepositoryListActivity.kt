package com.example.githubusersearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitHubUserRepositoryListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_hub_user_repository_list)

        val resid = findViewById<TextView>(R.id.resid)
        val resname = findViewById<TextView>(R.id.resname)
        val desc =  findViewById<TextView>(R.id.resdesc)
        val starCnt = findViewById<TextView>(R.id.starCnt)
        val watchCnt = findViewById<TextView>(R.id.watchCnt)
        val forkCnt = findViewById<TextView>(R.id.forkCnt)




        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
        val apiService = retrofit.create(GitHubAPIService::class.java)

        val id = intent.getStringExtra("userid")!!
        Log.d("myt", id)
        //resid.text = "id : ${id}"

        var apiCallForData = apiService.getRepos(id, "token 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")

        apiCallForData.enqueue(object : Callback<List<GitHubRepos>> {
            override fun onResponse(
                call: Call<List<GitHubRepos>>,
                response: Response<List<GitHubRepos>>
            ) {
                val data = response.body()!!

                Log.d("mytag", data.toString())

                //어뎁터에 넘겨주기
                val listView = findViewById<RecyclerView>(R.id.repos_list)
                listView.setHasFixedSize(true)
                listView.layoutManager = LinearLayoutManager(this@GitHubUserRepositoryListActivity)

                listView.adapter = GitHubAdapter(data)
                listView.setHasFixedSize(true)


            }

            override fun onFailure(call: Call<List<GitHubRepos>>, t: Throwable) {

            }

        })



        }
}


