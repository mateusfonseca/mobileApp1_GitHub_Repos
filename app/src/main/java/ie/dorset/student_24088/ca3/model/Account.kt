package ie.dorset.student_24088.ca3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import okhttp3.OkHttp

@Parcelize
data class Account(
    var noConnection: Boolean = false,
    var notFound: Boolean = false,
    var avatar_url: String? = "",
    var name: String? = "",
    var login: String? = "",
    var followers: Int? = 0,
    var following: Int? = 0,
    var company: String? = "",
    var location: String? = "",
    var repos_url: String? = "",
    var repos: @RawValue MutableList<Repo>? = mutableListOf(),
) : Parcelable