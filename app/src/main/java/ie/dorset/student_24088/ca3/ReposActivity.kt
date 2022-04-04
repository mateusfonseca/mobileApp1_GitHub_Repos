package ie.dorset.student_24088.ca3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import ie.dorset.student_24088.ca3.adapter.RepoAdapter
import ie.dorset.student_24088.ca3.databinding.ActivityReposBinding
import ie.dorset.student_24088.ca3.lib.NumberAbbreviator
import ie.dorset.student_24088.ca3.model.Account
import ie.dorset.student_24088.ca3.network.RepoApi
import java.util.*
import kotlin.concurrent.schedule

class ReposActivity : AppCompatActivity(), RepoApi, NumberAbbreviator {
    private lateinit var binding: ActivityReposBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called!") // Only for debugging purposes

        // Inflates the associated layout via view binding
        binding = ActivityReposBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val url = intent.getStringExtra("Url")!!
        Log.d(TAG, url)

        val account = Account()
        fetchAccount(url, this@ReposActivity, account)

        Timer().schedule(10000) {

            Handler(Looper.getMainLooper()).post {

                binding.apply {
                    if (account.noConnection) {
                        errorMessage.errorMessage.text =
                            resources.getText(R.string.no_connection)
                        errorMessage.errorImage.setImageResource(R.drawable.ic_connection_error)
                        errorMessage.errorMessageLayout.visibility = VISIBLE
                        progressBar.visibility = GONE
                        errorMessage.errorButton.setOnClickListener {
                            finish()
                        }
                    } else if (account.notFound) {
                        errorMessage.errorMessage.text =
                            resources.getText(R.string.account_not_found)
                        errorMessage.errorMessageLayout.visibility = VISIBLE
                        progressBar.visibility = GONE
                        errorMessage.errorButton.setOnClickListener {
                            finish()
                        }
                    } else {
                        Picasso.get()
                            .load(account.avatar_url)
                            .placeholder(
                                AppCompatResources.getDrawable(
                                    this@ReposActivity,
                                    R.drawable.loading_animation
                                )!!
                            )
                            .error(
                                AppCompatResources.getDrawable(
                                    this@ReposActivity,
                                    R.drawable.ic_connection_error
                                )!!
                            )
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(avatar)

                        name.text = account.name
                        login.text = account.login
                        followers.text = abbreviate(account.followers!!.toLong())
                        following.text = abbreviate(account.following!!.toLong())
                        company.text = account.company
                        location.text = account.location

//                        fetchRepos(account.repos_url!!, this@ReposActivity, account.repos!!)
//                        account.repos!!.forEach {
//                            fetchParent(this@ReposActivity, it)
//                            fetchColor(this@ReposActivity, colorUrl, it)
//                            Log.d(TAG, it.language!!)
//                            Log.d(TAG, it.color!!)
//                        }

                        reposRecyclerView.adapter = RepoAdapter(account.repos!!, this@ReposActivity)

                        mainContent.visibility = VISIBLE
                        progressBar.visibility = GONE
                    }
                }
            }
        }
    }

    // Only for debugging purposes
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart called!")
    }

    // Only for debugging purposes
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called!")
    }

    // Only for debugging purposes
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called!")
    }

    // Only for debugging purposes
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called!")
    }

    // Only for debugging purposes
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called!")
    }

    // Only for debugging purposes
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called!")
    }

    // TAG used to identify the activity.
    // Debugging purposes
    companion object {
        private const val TAG = "ReposActivity"
    }
}