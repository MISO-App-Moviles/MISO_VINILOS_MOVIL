package com.example.myapplication.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ArtistPreviewItemBinding
import com.example.myapplication.model.models.PreviewPerformer

class ArtistPreviewAdapter: RecyclerView.Adapter<ArtistPreviewAdapter.ArtistPreviewViewHolder>(){
    class ArtistPreviewViewHolder(val viewDataBinding: ArtistPreviewItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artist_preview_item
        }
    }

    var artists :List<PreviewPerformer> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistPreviewViewHolder {
        val withDataBinding: ArtistPreviewItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistPreviewViewHolder.LAYOUT,
            parent,
            false)
        return ArtistPreviewViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistPreviewViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.artist = artists[position]
            Glide.with(holder.itemView.getContext())
                .load(artists[position].image)
                .into(holder.viewDataBinding.artistImage);
        }
    }

    override fun getItemCount(): Int {
        return artists.size
    }
}