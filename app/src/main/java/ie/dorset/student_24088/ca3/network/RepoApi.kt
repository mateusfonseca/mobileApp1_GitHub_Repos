package ie.dorset.student_24088.ca3.network

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import ie.dorset.student_24088.ca3.model.Account
import ie.dorset.student_24088.ca3.model.Repo
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException
import java.util.*

interface RepoApi {
    private val client: OkHttpClient
        get() = OkHttpClient()

    fun fetchAccount(
        url: String,
        context: Context,
        account: Account
    ) {
        Log.d(TAG, "here it really starts")
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Exception: $e")

                account.noConnection = true
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val bodyString = response.body?.string()
                    val gson = Gson()
                    val jsonAccount = gson.fromJson(bodyString, Account::class.java)

                    account.apply {
                        avatarUrl = jsonAccount.avatarUrl
                        name = jsonAccount.name
                        login = jsonAccount.login
                        followers = jsonAccount.followers
                        following = jsonAccount.following
                        company = jsonAccount.company
                        location = jsonAccount.location
                        reposUrl = jsonAccount.reposUrl
                    }

                    fetchRepos(account.reposUrl!!, context, account.repos!!)

                    Log.d(TAG, "here starts")
                    Log.d(TAG, account.avatarUrl!!)
                } else {
                    account.notFound = true
                }
            }
        })
    }

    fun fetchRepos(
        reposUrl: String,
        context: Context,
        repos: MutableList<Repo>
    ) {
        Log.d(TAG, "here 2")
        val request = Request.Builder().url(reposUrl).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Exception: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val bodyString = response.body?.string()
                    val gson = Gson()
                    val jsonRepos = gson.fromJson(bodyString, Array<Repo>::class.java).toList()

                    repos.addAll(jsonRepos)

                    repos.forEach {
                        if (it.fork!!)
                            fetchParent(context, it)
                        if (it.language != null)
                            fetchColor(context, it)
                    }
                }
            }
        })
    }

    fun fetchParent(
        context: Context,
        repo: Repo
    ) {
        val request = Request.Builder().url(repo.url!!).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Exception: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val bodyString = response.body?.string()
                    val gson = Gson()
                    val json = gson.fromJson(bodyString, JsonObject::class.java)
                    val jsonParent = json.get("parent")

                    if (jsonParent != null) {
                        repo.parent = jsonParent.asJsonObject.get("full_name").asString
                    } else {
                        repo.parent = ""
                    }
                }
            }
        })
    }

    fun fetchColor(
        context: Context,
        repo: Repo
    ) {
        val colorUrl = "https://github-lang-deploy.herokuapp.com/"
        val request = Request.Builder().url(colorUrl).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Exception: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val bodyString = response.body?.string()
                    val gson = Gson()
                    val json = gson.fromJson(bodyString, JsonObject::class.java)
                    val jsonColor = json.get(repo.language)

                    repo.color = try {
                        jsonColor.asJsonObject.get("color").asString
                    } catch (e: Exception) {
                        repo.color
                    }
                }
            }
        })
    }

    companion object {
        private const val TAG: String = "RepoApi"
    }
}