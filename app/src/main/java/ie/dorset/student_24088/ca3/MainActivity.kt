package ie.dorset.student_24088.ca3

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Movie
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import ie.dorset.student_24088.ca3.databinding.ActivityMainBinding
import ie.dorset.student_24088.ca3.model.Account
import ie.dorset.student_24088.ca3.network.RepoApi
import kotlinx.coroutines.*
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity(), RepoApi {
    private lateinit var binding: ActivityMainBinding
    private var baseUrl = "https://api.github.com/"
    private var endPoint = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called!") // Only for debugging purposes

        // Inflates the associated layout via view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onRadioButtonClicked(binding.radioButtonUsername)

        binding.buttonSearch.setOnClickListener {
            if (TextUtils.isEmpty(binding.textInput.text)) {
                Toast.makeText(applicationContext, "Field cannot be empty!", Toast.LENGTH_LONG)
                    .show()
            } else {
                endPoint += binding.textInput.text
                startActivity(
                    Intent(this@MainActivity, ReposActivity::class.java).putExtra(
                        "Url",
                        baseUrl + endPoint
                    )
                )
            }
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_button_username ->
                    if (checked) {
                        binding.textInput.hint = "username"
                        endPoint = "users/"
                        Log.d(TAG, endPoint)
                    }
                R.id.radio_button_orgname ->
                    if (checked) {
                        binding.textInput.hint = "orgname"
                        endPoint = "orgs/"
                        Log.d(TAG, endPoint)
                    }
            }
        }
    }

    // Only for debugging purposes
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart called!")

        if (endPoint.contains("users/")) {
            onRadioButtonClicked(binding.radioButtonUsername)
        } else {
            onRadioButtonClicked(binding.radioButtonOrgname)
        }
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
        private const val TAG = "MainActivity"
    }
}