package com.example.myapplication.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.databinding.AddAlbumFragmentBinding
import com.example.myapplication.model.models.Album
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
        val genreSpinner: Spinner = binding.genreSpinner
        buildSpinner(view, genreSpinner, R.array.genre_array)
        val recordSpinner: Spinner = binding.recordSpinner
        buildSpinner(view, recordSpinner, R.array.record_array)
        binding.editTextCover.doAfterTextChanged {
            setImage(view, it.toString())
        }
        binding.cancelButton.setOnClickListener {
            clearForm()
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
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { error ->
            if (error != "") {
                val toast = Toast.makeText(requireContext(),  "Ha ocurrido un error: " + error.toString(), Toast.LENGTH_LONG) // in Activity
                toast.show()
            }
        }
        viewModel.albumId.observe(viewLifecycleOwner) { id ->
            val toast = Toast.makeText(requireContext(), "Se ha creado un album con el id " + id.toString(), Toast.LENGTH_LONG) // in Activity
            toast.show()
            clearForm()
        }
    }

    private fun onAddAlbumClick() {
        val album = Album(
            name = binding.editTextName.text.toString().trim(),
            cover = binding.editTextCover.text.toString().trim(),
            releaseDate = binding.editTextDate.text.toString().trim(),
            description = binding.editTextDescription.text.toString().trim(),
            genre = binding.genreSpinner.selectedItem.toString().trim(),
            recordLabel = binding.recordSpinner.selectedItem.toString().trim()
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
        if (!DateUtil.isValidDate(album.releaseDate))
            return "La fecha no es valida!"
        return ""
    }

    fun setImage(view: View, url: String){
        Glide.with(view.getContext())
            .load(url)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(binding.albumPreviewImage);
    }

    fun buildSpinner(view: View, spinner: Spinner, itemsArray : Int){
        ArrayAdapter.createFromResource(
            view.context,
            itemsArray,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    fun clearForm(){
        binding.editTextName.setText("")
        binding.editTextCover.setText("")
        binding.editTextDate.setText("")
        binding.editTextDescription.setText("")
        binding.genreSpinner.setSelection(0)
        binding.recordSpinner.setSelection(0)
    }

}