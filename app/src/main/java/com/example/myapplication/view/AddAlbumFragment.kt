package com.example.myapplication.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.databinding.AddAlbumFragmentBinding
import com.example.myapplication.databinding.AlbumFragmentBinding
import com.example.myapplication.model.models.Album
import com.example.myapplication.view.adapters.AlbumAdapter

class AddAlbumFragment : Fragment() {
    private var _binding: AddAlbumFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddAlbumFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.editTextCover.doAfterTextChanged {
            setImage(view, it.toString())
            Log.d("Tag",  it.toString())
        }
        binding.submitButton.setOnClickListener {
            val albumName = binding.editTextName.text
            Log.d("Tag",  albumName.toString())
            binding.editTextName.setText("")
            view.findNavController().navigateUp()
        }
    }

    fun setImage(view: View, url: String){
        Glide.with(view.getContext())
            .load(url)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(binding.albumPreviewImage);
    }

}