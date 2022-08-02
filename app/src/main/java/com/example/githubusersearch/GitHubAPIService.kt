package com.example.githubusersearch

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import java.lang.reflect.Type


interface GitHubAPIService {
    //https://api.github.com/users/printyeon
    @GET ("/users/{user}")
    fun getUser(
        @Path("user") id: String,  //위에 아래 벨류값 같아야함
        @Header("Authorization") pat : String
    ) :Call<GitHubUser>

    @GET ("/users/{user}/repos")
    fun getRepos(
        @Path("user") id: String,  //위에 아래 벨류값 같아야함
        @Header("Authorization") pat : String
    ) :Call<List<GitHubRepos>>
}
data class GitHubUser(
    val id:Int,
    val login:String,
    val name:String?,
    val followers:Int,
    val following:Int,
    @SerializedName("avatar_url") //이름바꿔서 카멜케이스 쓰기 그냥 -근본-
    val avatarUrl:String
) {

}

data class GitHubRepos(
    val name: String,
    val html_url:String,
    val description : String?,
    val stargazers_count:Int,
    val watchers_count:Int,
    val forks_count:Int
)
/*
class GitHubUserDeserializer : JsonDeserializer<GitHubUser> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GitHubUser {

        val root = json?.getAsJsonObject()
        val id = root?.getAsJsonPrimitive("id")?.asInt
        val login = root?.getAsJsonPrimitive("login")?.asString


        return GitHubUser(id!!, login!!)
    }

}
*/
