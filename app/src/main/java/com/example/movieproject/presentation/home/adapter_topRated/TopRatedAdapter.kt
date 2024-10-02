package com.example.movieproject.presentation.home.adapter_topRated

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieproject.data.datasource.model.MovieDetails
import com.example.movieproject.data.datasource.remote.api.imageUrl
import com.example.movieproject.databinding.TopRatedItemBinding

class TopRatedAdapter: RecyclerView.Adapter<TopRatedAdapter.TopRatedViewHolder>() {
    val result= arrayListOf<MovieDetails>()
    lateinit var context: Context
    private var listener: ((Int) -> Unit)? = null
    inner class TopRatedViewHolder(val binding: TopRatedItemBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(position: Int) {
            binding.tvRank.text="#${position + 1}"
            Glide.with(context).asBitmap().load("${imageUrl}${result[position].posterPath}").into(binding.ivMoviePic)
            binding.tvMovieTitle.text=result[position].title
            binding.tvRatingTitleStyle.text=result[position].voteAverage
            binding.root.setOnClickListener {
                listener?.let {
                    it(position)  // Call the listener with the position
                }
            }

        }

    }
    fun setOnItemClickListener(listener: (Int) -> Unit) {
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedViewHolder {
        context=parent.context
        val binding = TopRatedItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return TopRatedViewHolder(binding)
    }

    override fun getItemCount(): Int =result.size

    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun setData(results: List<MovieDetails>) {
        result.clear()
        result.addAll(results)
        notifyDataSetChanged()
    }
}