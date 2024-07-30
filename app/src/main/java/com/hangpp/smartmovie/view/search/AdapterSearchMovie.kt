package com.hangpp.smartmovie.view.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hangpp.domain.model.Movie
import com.hangpp.smartmovie.R
import com.hangpp.smartmovie.databinding.ItemSearchMovieBinding
import com.hangpp.smartmovie.view.common.imgUrl
import com.squareup.picasso.Picasso

class AdapterSearchMovie(private val movieList: List<Movie>) :
    RecyclerView.Adapter<AdapterSearchMovie.ViewHolder>() {

    private lateinit var binding: ItemSearchMovieBinding
    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivPoster: ImageView = binding.ivPoster
        private val tvTitle: TextView = binding.tvTitle
        private val ratingBar: RatingBar = binding.ratingBar

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val bundle = bundleOf("movieId" to movieList[position].id)
                    Navigation.findNavController(itemView).navigate(R.id.move_to_detail, bundle)
                }
            }
        }

        fun bind(movie: Movie) {
            Picasso.with(context).load(imgUrl + movie.backdropPath).into(ivPoster)
            tvTitle.text = movie.title
            ratingBar.rating = movie.voteAverage / 2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = ItemSearchMovieBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
    }
}