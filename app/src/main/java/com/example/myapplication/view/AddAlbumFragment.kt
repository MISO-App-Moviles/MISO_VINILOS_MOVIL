package com.example.myapplication.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
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
        binding.editTextCover.doAfterTextChanged {
            setImage(view, it.toString())
            Log.d("Tag",  it.toString())
        }
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, AddAlbumViewModel.Factory(activity.application)).get(
            AddAlbumViewModel::class.java)
        binding.submitButton.setOnClickListener {
//            Leer inputs
//            Pasar inputs por función
            val albumName = binding.editTextName.text.toString()
            val albumDescription = binding.editTextDescription.text.toString()
            val albumGenre= binding.editTextGenre.text.toString()

            Log.d("Tag",  validateNonEmpty(albumName).toString())
            Log.d("Tag",  validateNonEmpty(albumDescription).toString())
            Log.d("Tag",  validateNonEmpty(albumGenre).toString())



//            binding.editTextName.setText("")
            //view.findNavController().navigateUp()
            val album = Album(name = albumName.toString(), cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg", releaseDate = "2011-08-01T00:00:00-05:00", description = "desc", genre = "Salsa", recordLabel = "Sony")

//            val jsonObject = JSONObject().apply {
//                put("name", album.name)
//                put("cover", album.cover)
//                put("releaseDate", album.releaseDate)
//                put("description", album.description)
//                put("genre", album.genre)
//                put("recordLabel", album.recordLabel)
//            }
//            viewModel.postAlbumToNetwork(jsonObject)

//            viewModel.eventNetworkError.observe(viewLifecycleOwner) { isError ->
//                if (isError) {
//                    Log.d("Error", isError.toString())
//                }
//            }
//            viewModel.albumId.observe(viewLifecycleOwner) { id ->
//                //TODO Agregar toast para mostrar mensaje con id de album creado
//                Log.d("Id", id.toString())
//            }
        }
    }

    fun setImage(view: View, url: String){
        Glide.with(view.getContext())
            .load(url)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(binding.albumPreviewImage);
    }

    private fun validateAlbum(album:Album): String{
//

        return ""
    }

    private fun validateNonEmpty(input: String): Boolean{
        return input != ""
    }

}