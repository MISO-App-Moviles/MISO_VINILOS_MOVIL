package com.example.myapplication.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.AlbumPreviewItemBinding
import com.example.myapplication.model.models.PreviewAlbum
import com.example.myapplication.view.CollectorDetailFragmentDirections

class AlbumPreviewAdapter: RecyclerView.Adapter<AlbumPreviewAdapter.AlbumPreviewViewHolder>() {
        class AlbumPreviewViewHolder(val viewDataBinding: AlbumPreviewItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_preview_item
        }
    }

    var albums :List<PreviewAlbum> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumPreviewViewHolder {
        val withDataBinding: AlbumPreviewItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumPreviewViewHolder.LAYOUT,
            parent,
            false)
        return AlbumPreviewViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumPreviewViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]
            Glide.with(holder.itemView.getContext())
                .load(albums[position].cover)
                .into(holder.viewDataBinding.albumImage);
        }
        holder.viewDataBinding.root.setOnClickListener {
            val fragment = CollectorDetailFragmentDirections
            val action = albums[position].id?.let { it1 -> fragment.actionToAlbumDetailFragment(it1) }
            // Navigate using that action
            if (action != null) {
                holder.viewDataBinding.root.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

}