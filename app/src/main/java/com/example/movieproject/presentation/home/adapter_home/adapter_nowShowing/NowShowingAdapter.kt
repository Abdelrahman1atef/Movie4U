package com.example.movieproject.presentation.home.adapter_home.adapter_nowShowing

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieproject.data.datasource.model.MovieDetails
import com.example.movieproject.data.datasource.remote.api.imageUrl
import com.example.movieproject.databinding.NowShowingItemBinding
import com.example.movieproject.presentation.home.OnItemClickListener

class NowShowingAdapter: RecyclerView.Adapter<NowShowingAdapter.NowShowingViewHolder>(),OnItemClickListener {
    val result= arrayListOf<MovieDetails>()
    lateinit var context:Context
    private var listener: ((Int) -> Unit)? = null

    inner class NowShowingViewHolder(val binding: NowShowingItemBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(position: Int) {
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
    override  fun setOnItemClickListener(listener: (Int) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowShowingViewHolder {
        context=parent.context
        val binding = NowShowingItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return NowShowingViewHolder(binding)
    }

    override fun getItemCount(): Int =result.size

    override fun onBindViewHolder(holder: NowShowingViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun setData(results: List<MovieDetails>) {
        result.clear()
        result.addAll(results)
        notifyDataSetChanged()
    }




}