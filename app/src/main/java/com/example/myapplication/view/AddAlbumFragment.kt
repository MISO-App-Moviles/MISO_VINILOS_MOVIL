package com.example.myapplication.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.AddAlbumFragmentBinding
import com.example.myapplication.model.models.Album
import com.example.myapplication.viewModel.AddAlbumViewModel
import com.example.myapplication.viewModel.AlbumDetailViewModel
import org.json.JSONObject

class AddAlbumFragment : Fragment() {
    private var _binding: AddAlbumFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddAlbumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddAlbumFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, AddAlbumViewModel.Factory(activity.application)).get(
            AddAlbumViewModel::class.java)
        binding.submitButton.setOnClickListener {
            val albumName = binding.editTextName.text
            Log.d("Tag",  albumName.toString())
            binding.editTextName.setText("")
            //view.findNavController().navigateUp()
            val album = Album(name = albumName.toString(), cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg", releaseDate = "2011-08-01T00:00:00-05:00", description = "desc", genre = "Salsa", recordLabel = "Sony")

            val jsonObject = JSONObject().apply {
                put("name", album.name)
                put("cover", album.cover)
                put("releaseDate", album.releaseDate)
                put("description", album.description)
                put("genre", album.genre)
                put("recordLabel", album.recordLabel)
            }
            try{
                viewModel.postAlbumToNetwork(jsonObject)
            }catch (e: Exception){
                Log.e("EXC", e.message.toString())
            }
            viewModel.eventNetworkError.observe(viewLifecycleOwner) { isError ->
                if (isError) {
                    Log.d("Error", isError.toString())
                }
            }
            viewModel.albumId.observe(viewLifecycleOwner) { id ->
                //TODO Agregar toast para mostrar mensaje con id de album creado
                Log.d("Id", id.toString())
            }
        }
    }
}