package com.azamovme.MoviePlayerAkylai.ui.screens.tv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azamovme.MoviePlayerAkylai.data.Movie
import com.azamovme.MoviePlayerAkylai.databinding.TvItemBinding
import com.azamovme.MoviePlayerAkylai.utils.loadImage

class TvAdapter : RecyclerView.Adapter<TvAdapter.TvViewHolder>() {
    private val list = ArrayList<Movie>()

    var onItemClick: ((Movie) -> Unit)? = null

    inner class TvViewHolder(val binding: TvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(movie: Movie) {
            binding.apply {
                binding.root.setOnClickListener {
                    onItemClick?.invoke(movie)
                }
                root.loadImage(movie.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        return TvViewHolder(
            TvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    fun setItemClickListener(newListener: (Movie) -> Unit) {
        onItemClick = newListener
    }

    fun submitList(newList: ArrayList<Movie>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        holder.onBind(list.get(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }
}