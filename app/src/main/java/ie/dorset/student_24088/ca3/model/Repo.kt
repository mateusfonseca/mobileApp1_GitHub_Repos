package ie.dorset.student_24088.ca3.model

import com.google.gson.annotations.SerializedName

data class Repo(
    var name: String? = "",
    var visibility: String? = "",
    var url: String? = "",
    var description: String? = "",
    var language: String? = "",
    var color: String? = "#FFFFFF",
    @SerializedName("stargazers_count")
    var stargazersCount: Int? = 0,
    @SerializedName("forks_count")
    var forksCount: Int? = 0,
    var parent: String? = "",
    var fork: Boolean? = false
)