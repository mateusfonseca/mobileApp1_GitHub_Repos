package ie.dorset.student_24088.ca3.network

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import androidx.annotation.RestrictTo
import com.google.gson.Gson
import com.google.gson.JsonObject
import ie.dorset.student_24088.ca3.ReposActivity
import ie.dorset.student_24088.ca3.model.Account
import ie.dorset.student_24088.ca3.model.Repo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import okhttp3.*
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import okhttp3.internal.wait
import java.io.IOException
import java.lang.Thread.holdsLock
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.LockSupport
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.schedule
import kotlin.concurrent.withLock

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
                        avatar_url = jsonAccount.avatar_url
                        name = jsonAccount.name
                        login = jsonAccount.login
                        followers = jsonAccount.followers
                        following = jsonAccount.following
                        company = jsonAccount.company
                        location = jsonAccount.location
                        repos_url = jsonAccount.repos_url
                    }

                    fetchRepos(account.repos_url!!, context, account.repos!!)

                    Log.d(TAG, "here starts")
                    Log.d(TAG, account.avatar_url!!)
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


















//    fun fetchAccount(
//        url: String,
//        context: Context
//    ): Account {
//        var account = Account()
//        Log.d(TAG, "here it really starts")
//        val request = Request.Builder().url(url).build()
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e(TAG, "Exception: $e")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    val bodyString = response.body?.string()
//                    val gson = Gson()
//                    account = gson.fromJson(bodyString, Account::class.java)
//
//                    Log.d(TAG, "here starts")
//                    Log.d(TAG, account.avatar_url!!)
//                } else {
//                    account.notFound = true
//                }
//            }
//        })
//        sleep(2000)
//        return account
//    }
//
//    fun fetchRepos(
//        reposUrl: String,
//        context: Context
//    ): List<Repo> {
//        var repos = emptyList<Repo>()
//        Log.d(TAG, "here 2")
//        val request = Request.Builder().url(reposUrl).build()
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e(TAG, "Exception: $e")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    val bodyString = response.body?.string()
//                    val gson = Gson()
//                    repos = gson.fromJson(bodyString, Array<Repo>::class.java).toList()
//
//                }
//            }
//        })
//        sleep(2000)
//        return repos
//    }
//
//    fun fetchParent(
//        context: Context,
//        url: String
//    ): String? {
//        var parent = ""
//        val request = Request.Builder().url(url).build()
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e(TAG, "Exception: $e")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    val bodyString = response.body?.string()
//                    val gson = Gson()
//                    val json = gson.fromJson(bodyString, JsonObject::class.java)
//                    val jsonParent = json.get("parent")
//
//                    if (jsonParent != null) {
//                        parent = jsonParent.asJsonObject.get("full_name").asString
//                    } else {
//                        parent = ""
//                    }
//                }
//            }
//        })
//        sleep(2000)
//        return parent
//    }
//
//    fun fetchColor(
//        context: Context,
//        url: String,
//        language: String
//    ): String? {
//        var color = ""
//        val request = Request.Builder().url(url).build()
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e(TAG, "Exception: $e")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    val bodyString = response.body?.string()
//                    val gson = Gson()
//                    val json = gson.fromJson(bodyString, JsonObject::class.java)
//                    val jsonColor = json.get(language)
//                    color = jsonColor.asJsonObject.get("color").asString
//                }
//            }
//        })
//        sleep(2000)
//        return color
//    }

    companion object {
        private const val TAG: String = "RepoApi"
    }
}