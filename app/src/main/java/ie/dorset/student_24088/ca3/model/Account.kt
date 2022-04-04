package ie.dorset.student_24088.ca3.model

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

data class Account(
    var noConnection: Boolean = false,
    var notFound: Boolean = false,
    @SerializedName("avatar_url")
    var avatarUrl: String? = "",
    var name: String? = "",
    var login: String? = "",
    var followers: Int? = 0,
    var following: Int? = 0,
    var company: String? = "",
    var location: String? = "",
    @SerializedName("repos_url")
    var reposUrl: String? = "",
    var repos: @RawValue MutableList<Repo>? = mutableListOf()
)