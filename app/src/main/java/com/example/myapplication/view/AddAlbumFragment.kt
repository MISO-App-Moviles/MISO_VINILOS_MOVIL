package com.example.myapplication.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        binding.submitButton.setOnClickListener {
            val albumName = binding.editTextName.text
            Log.d("Tag",  albumName.toString())
            binding.editTextName.setText("")
            view.findNavController().navigateUp()
        }
    }

}