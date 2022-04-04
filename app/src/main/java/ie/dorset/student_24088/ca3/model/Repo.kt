package ie.dorset.student_24088.ca3.model

import android.os.Parcelable
import androidx.appcompat.widget.DecorContentParent
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Repo(
    var name: String? = "",
    var visibility: String? = "",
    var url: String? = "",
    var description: String? = "",
    var language: String? = "",
    var color: String? = "#FFFFFF",
    var stargazers_count: Int? = 0,
    var forks_count: Int? = 0,
    var parent: String? = "",
    var fork: Boolean? = false
) : Parcelable