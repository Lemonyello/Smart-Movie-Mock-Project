package com.hangpp.smartmovie.view.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hangpp.domain.model.Movie
import com.hangpp.domain.model.MovieLikedModel
import com.hangpp.smartmovie.R
import com.hangpp.smartmovie.view.common.imgUrl
import com.squareup.picasso.Picasso

enum class ListType { Grid, List }

// adapter for both vertical list and grid list
class AdapterListMovie(
    private var listType: ListType,
    var movieList: ArrayList<Movie?>,
    val movieLikedList: List<MovieLikedModel>,
    val insertFavoriteMovie: (String) -> Unit,
    val deleteFavoriteMovie: (String) -> Unit,
) : ListAdapter<Movie, RecyclerView.ViewHolder>(Movie.DIFF) {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            val rotation = AnimationUtils.loadAnimation(itemView.context, R.anim.rotate)
            rotation.fillAfter = true
            rotation.interpolator = LinearInterpolator()
            itemView.findViewById<ImageView>(R.id.ivLoading).startAnimation(rotation)
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private lateinit var tvOverview: TextView
        private val img: ImageView = itemView.findViewById(R.id.ivPoster)
        private val btnLike: ImageView = itemView.findViewById(R.id.btnLike)
        private var movieId = ""

        init {
            // if list type is List, the item has a tvOverview, grid doesn't
            if(listType == ListType.List) tvOverview = itemView.findViewById(R.id.tvOverview)

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val bundle = bundleOf("movieId" to (movieList[position]?.id ?: ""))
                    Navigation.findNavController(itemView).navigate(R.id.move_to_detail, bundle)
                }
            }

            btnLike.setOnClickListener {
                btnLike.apply {
                    if (tag == R.drawable.star) {
                        tag = R.drawable.star_liked
                        setImageResource(R.drawable.star_liked)
                        insertFavoriteMovie(movieId)
                        return@setOnClickListener
                    }

                    if (tag == R.drawable.star_liked) {
                        tag = R.drawable.star
                        setImageResource(R.drawable.star)
                        deleteFavoriteMovie(movieId)
                    }
                }
            }
        }

        fun bind(movie: Movie) {
            this.movieId = movie.id
            tvName.text = movie.title ?: "No name"
            Picasso.with(itemView.context)
                .load(imgUrl + movie.posterPath)
                .into(img)

            btnLike.apply {
                if (movieLikedList.any { it.movieId == movieId }) {
                    tag = R.drawable.star_liked
                    setImageResource(R.drawable.star_liked)
                } else {
                    tag = R.drawable.star
                    setImageResource(R.drawable.star)
                }
            }

            if(listType == ListType.List) tvOverview.text = movie.overview
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        // choose layout file depend on whether list type is list or grid
        val view = inflater.inflate(
            if (listType == ListType.List) R.layout.item_list_movie else R.layout.item_grid_movie,
            parent,
            false
        )

        // Return a new holder instance
        return if(viewType == VIEW_TYPE_LOADING) {
            val viewLoading = inflater.inflate(R.layout.item_load_more, parent, false)
            LoadingViewHolder(viewLoading)
        } else MovieViewHolder(view)
    }

    override fun getItemCount() = movieList.size

    override fun getItemViewType(position: Int): Int = if(movieList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MovieViewHolder) {
            movieList[position]?.let { holder.bind(it) }
        }
    }
}