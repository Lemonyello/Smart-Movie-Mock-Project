package com.hangpp.smartmovie.view.genres

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hangpp.domain.model.Genre
import com.hangpp.smartmovie.R
import com.hangpp.smartmovie.databinding.ItemListGenreBinding
import com.squareup.picasso.Picasso

class GenresAdapter(private val genres: List<Genre>) :
    RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    private lateinit var binding: ItemListGenreBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivGenre = binding.ivGenreImage
        private val tvGenre = binding.tvGenreName

        init {
            itemView.setOnClickListener {
                val bundle = bundleOf(
                    "genreId" to genres[adapterPosition].id,
                    "genreName" to genres[adapterPosition].name
                )
                Navigation.findNavController(itemView).navigate(R.id.move_to_detail_genre, bundle)
            }
        }

        fun bind(genre: Genre) {
            Picasso.with(itemView.context).load(R.drawable.planet_jupiter).into(ivGenre)
            tvGenre.text = genre.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemListGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genres[position])
    }
}