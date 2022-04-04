package ie.dorset.student_24088.ca3

import android.content.Intent
import android.net.Uri
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

        val bundle = intent.extras
        val baseUrl = bundle?.getString("Base")
        val endPoint = bundle?.getString("End")

        val account = Account()
        fetchAccount(baseUrl + endPoint, this@ReposActivity, account)

        Timer().schedule(10000) {
            Handler(Looper.getMainLooper()).post {
                binding.apply {
                    when {
                        account.noConnection -> {
                            errorMessage.errorMessage.text =
                                resources.getText(R.string.no_connection)
                            errorMessage.errorImage.setImageResource(R.drawable.ic_connection_error)
                            errorMessage.errorMessageLayout.visibility = VISIBLE
                            progressBar.visibility = GONE
                            errorMessage.errorButton.setOnClickListener {
                                finish()
                            }
                        }
                        account.notFound -> {
                            errorMessage.errorMessage.text =
                                resources.getText(R.string.account_not_found)
                            errorMessage.errorMessageLayout.visibility = VISIBLE
                            progressBar.visibility = GONE
                            errorMessage.errorButton.setOnClickListener {
                                finish()
                            }
                        }
                        else -> {
                            Picasso.get()
                                .load(account.avatarUrl)
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

                            if (account.name == null) {
                                name.visibility = GONE
                            } else {
                                name.text = account.name
                            }

                            login.text = account.login
                            followers.text = abbreviate(account.followers!!.toLong())
                            following.text = abbreviate(account.following!!.toLong())

                            if (account.company == null) {
                                companyDetails.visibility = GONE
                            } else {
                                company.text = account.company
                            }

                            if (account.location == null) {
                                locationDetails.visibility = GONE
                            } else {
                                location.text = account.location
                            }

                            githubLink.setOnClickListener {
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(baseUrl)))
                            }

                            colorsLink.setOnClickListener {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://github-lang-deploy.herokuapp.com/")
                                    )
                                )
                            }

                            reposRecyclerView.adapter =
                                RepoAdapter(account.repos!!)

                            mainContent.visibility = VISIBLE
                            progressBar.visibility = GONE
                        }
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