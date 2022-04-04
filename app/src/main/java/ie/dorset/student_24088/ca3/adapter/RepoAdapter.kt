package ie.dorset.student_24088.ca3.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Movie
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import ie.dorset.student_24088.ca3.R
import ie.dorset.student_24088.ca3.databinding.ActivityReposBinding
import ie.dorset.student_24088.ca3.databinding.ReposRecyclerTemplateBinding
import ie.dorset.student_24088.ca3.lib.NumberAbbreviator
import ie.dorset.student_24088.ca3.model.Repo

class RepoAdapter(
    private val repos: List<Repo>,
    private val context: Context
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
            visibility.text = repo.visibility
            forkedFrom.text = repo.parent
            description.text = repo.description
            languageColor.setCardBackgroundColor(Color.parseColor(repo.color))
            language.text = repo.language
            stargazers.text = abbreviate(repo.stargazers_count!!.toLong())
            forkedBy.text = abbreviate(repo.forks_count!!.toLong())
        }
    }

    override fun getItemCount(): Int = repos.size

    class RepoViewHolder(v: View) : RecyclerView.ViewHolder(v)
}