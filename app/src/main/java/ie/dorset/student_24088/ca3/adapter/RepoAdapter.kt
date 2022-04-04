package ie.dorset.student_24088.ca3.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.dorset.student_24088.ca3.databinding.ReposRecyclerTemplateBinding
import ie.dorset.student_24088.ca3.lib.NumberAbbreviator
import ie.dorset.student_24088.ca3.model.Repo

class RepoAdapter(
    private val repos: List<Repo>,
) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>(), NumberAbbreviator {
    private lateinit var binding: ReposRecyclerTemplateBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        binding =
            ReposRecyclerTemplateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repos[position]

        binding.apply {
            name.text = repo.name
            visibility.text = repo.visibility!!.replaceFirstChar { it.uppercaseChar() }
            forkedFrom.text = repo.parent

            if (repo.description == null) {
                description.visibility = GONE
            } else {
                description.text = repo.description
            }

            languageColor.setCardBackgroundColor(Color.parseColor(repo.color))
            language.text = repo.language
            stargazers.text = abbreviate(repo.stargazersCount!!.toLong())
            forkedBy.text = abbreviate(repo.forksCount!!.toLong())
        }
    }

    override fun getItemCount(): Int = repos.size

    class RepoViewHolder(v: View) : RecyclerView.ViewHolder(v)
}