package com.example.myapplication.view.adapters

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.AlbumDetailItemBinding
import com.example.myapplication.model.models.AlbumDetail

class AlbumDetailAdapter: RecyclerView.Adapter<AlbumDetailAdapter.AlbumDetailViewHolder>() {

    class AlbumDetailViewHolder(val viewDataBinding: AlbumDetailItemBinding): RecyclerView.ViewHolder(viewDataBinding.root){
        companion object{
            @LayoutRes
            val LAYOUT = R.layout.album_detail_item
        }
    }

    var detail: AlbumDetail
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumDetailViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AlbumDetailViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}