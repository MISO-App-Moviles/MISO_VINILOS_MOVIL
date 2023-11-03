package com.example.myapplication.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ArtistItemBinding
import com.example.myapplication.model.models.Artist

class ArtistAdapter : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>(){

    class ArtistViewHolder(val viewDataBinding: ArtistItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artist_item
        }
    }

    var Artists :List<Artist> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val withDataBinding: ArtistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistViewHolder.LAYOUT,
            parent,
            false)
        return ArtistViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.artist = Artists[position]
            Glide.with(holder.itemView.getContext())
                .load(Artists[position].image)
                .into(holder.viewDataBinding.imageView);
        }
//        holder.viewDataBinding.root.setOnClickListener {
//            val action = ArtistFragmentDirections.actionArtistFragmentToCommentFragment(Artists[position].ArtistId)
//            // Navigate using that action
//            holder.viewDataBinding.root.findNavController().navigate(action)
//        }
    }

    override fun getItemCount(): Int {
        return Artists.size
    }
}