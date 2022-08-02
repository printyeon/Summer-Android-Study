package com.example.githubusersearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GitHubAdapter (val dataList: List<GitHubRepos>)
    : RecyclerView.Adapter<GitHubAdapter.GithubHolder>()
{
        class GithubHolder(val view: View)
            :RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return GithubHolder(view)
    }

    override fun onBindViewHolder(holder: GithubHolder, position: Int) {
        val reposdata = dataList[position]

        val resname = holder.view.findViewById<TextView>(R.id.resname)
        val resdesc = holder.view.findViewById<TextView>(R.id.resdesc)
        val starCnt = holder.view.findViewById<TextView>(R.id.starCnt)
        val watchCnt = holder.view.findViewById<TextView>(R.id.watchCnt)
        val forkCnt = holder.view.findViewById<TextView>(R.id.forkCnt)


        resname.text = "repositoty name : ${dataList[position].name}"
        resdesc.text = "repositoty description : ${dataList[position].description}"
        starCnt.text = dataList[position].stargazers_count.toString()
        watchCnt.text = dataList[position].watchers_count.toString()
        forkCnt.text = dataList[position].forks_count.toString()

    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        return R.layout.github_list //레이아웃 id 반환
    }


}