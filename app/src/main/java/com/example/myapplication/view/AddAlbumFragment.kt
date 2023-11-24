package com.example.myapplication.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.databinding.AddAlbumFragmentBinding
import com.example.myapplication.model.models.Album
import com.example.myapplication.utils.AlbumUtility
import com.example.myapplication.utils.DateUtil
import com.example.myapplication.utils.ImageUtil
import com.example.myapplication.utils.StringUtil
import com.example.myapplication.viewModel.AddAlbumViewModel
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
            onAddAlbumClick()
        }

        //errores
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                val toast = Toast.makeText(requireContext(),  "Ha ocurrido un error: " + isError.toString(), Toast.LENGTH_LONG) // in Activity
                toast.show()
            }
        }
        viewModel.albumId.observe(viewLifecycleOwner) { id ->
            val toast = Toast.makeText(requireContext(), "Se ha creado un album con el id " + id.toString(), Toast.LENGTH_LONG) // in Activity
            toast.show()
            //Limpiar los campos
        }
    }

    private fun onAddAlbumClick() {
        val album = Album(
            name = binding.editTextName.text.toString().trim(),
            cover = binding.editTextCover.text.toString().trim(),
            releaseDate = binding.editTextDate.text.toString().trim(),
            description = binding.editTextDescription.text.toString().trim(),
            genre = binding.editTextGenre.text.toString().trim(),
            recordLabel = binding.editTextRecord.text.toString().trim()
        )
        val isValidMessage = validateParams(album)
        if (isValidMessage.isEmpty()) {
            val jsonObject = JSONObject().apply {
                put("name", album.name)
                put("cover", album.cover)
                put("releaseDate", album.releaseDate)
                put("description", album.description)
                put("genre", album.genre)
                put("recordLabel", album.recordLabel)
            }
            viewModel.postAlbumToNetwork(jsonObject)
        } else {
            val toast = Toast.makeText(
                requireContext(),
                isValidMessage,
                Toast.LENGTH_LONG
            ) // in Activity
            toast.show()
        }
    }

    private fun validateParams(album: Album): String {
        //Validaciones
        if (!ImageUtil.isValidCover(album.cover.toString()))
            return "Debe digitar un cover valido (\"jpg\",\"jpeg\",\"png\",\"gif\",\"bmp\",\"webp\",\"tiff\",\"svg\",\"ico\")"
        if (!StringUtil.isNonEmpty(album.name))
            return "El nombre no puede ser vacio"
        if (!StringUtil.isNonEmpty(album.description))
            return "La descripcion no puede ser vacia"
        if (!AlbumUtility.isValidGenre(album.genre))
            return "Debe digitar un genero valido (\"Classical\",\"Salsa\",\"Rock\",\"Folk\")"
        if (!DateUtil.isValidDate(album.releaseDate))
            return "La fecha no es valida!"
        if (!AlbumUtility.isValidDiscography(album.recordLabel))
            return "Debe digitar una discografia valida (\"Sony Music\", \"EMI\", \"Discos Fuentes\", \"Elektra\", \"Fania Records\")"
        return ""
    }

    fun setImage(view: View, url: String){
        Glide.with(view.getContext())
            .load(url)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(binding.albumPreviewImage);
    }

}