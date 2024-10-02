package com.example.movieproject.presentation.home.adapter_movieDetails

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieproject.data.datasource.model.CastDetails
import com.example.movieproject.data.datasource.model.MovieDetails
import com.example.movieproject.data.datasource.remote.api.imageUrl
import com.example.movieproject.databinding.CastItemBinding

class MovieDetailsAdapter : RecyclerView.Adapter<MovieDetailsAdapter.MovieDetailsViewHolder>() {
    val result= arrayListOf<CastDetails>()
    lateinit var context: Context
    private var listener: ((Int) -> Unit)? = null
    inner class MovieDetailsViewHolder(val binding: CastItemBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(position: Int) {
            Log.e("TAG", "onBind: ${result}", )
            Glide.with(context).asBitmap().load("$imageUrl${result[position].profilePath}").into(binding.ivCast)
            binding.tvCastName.text=result[position].name
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailsViewHolder {
        context=parent.context
        val binding = CastItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return MovieDetailsViewHolder(binding)
    }

    override fun getItemCount(): Int =result.size

    override fun onBindViewHolder(holder: MovieDetailsViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun setData(results: List<CastDetails>) {
        result.clear()
        result.addAll(results)
        notifyDataSetChanged()
    }
}