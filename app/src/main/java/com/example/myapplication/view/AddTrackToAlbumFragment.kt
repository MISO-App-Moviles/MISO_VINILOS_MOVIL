package com.example.myapplication.view

import android.os.Bundle
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
import com.example.myapplication.databinding.AddTrackToAlbumFragmentBinding
import com.example.myapplication.model.models.Album
import com.example.myapplication.model.models.Track
import com.example.myapplication.utils.DateUtil
import com.example.myapplication.utils.ImageUtil
import com.example.myapplication.utils.StringUtil
import com.example.myapplication.viewModel.AddAlbumViewModel
import com.example.myapplication.viewModel.AddTrackToAlbumViewModel
import org.json.JSONObject


class AddTrackToAlbumFragment : Fragment() {
    private var albumId: Int? = null
    private var _binding: AddTrackToAlbumFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddTrackToAlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumId = it.getInt("albumId")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddTrackToAlbumFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.cancelButton.setOnClickListener {
            clearForm()
        }
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, AddTrackToAlbumViewModel.Factory(activity.application)).get(
            AddTrackToAlbumViewModel::class.java)

        binding.submitButton.setOnClickListener {
            onAddTrackToAlbumClick()
        }

        //errores
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { error ->
            if (error != "") {
                val toast = Toast.makeText(requireContext(),  "Ha ocurrido un error: " + error.toString(), Toast.LENGTH_LONG) // in Activity
                toast.show()
            }
        }
        viewModel.trackId.observe(viewLifecycleOwner) { id ->
            val toast = Toast.makeText(requireContext(), "Se ha creado un track con el id " + id.toString(), Toast.LENGTH_LONG) // in Activity
            toast.show()
            clearForm()
        }
    }

    private fun onAddTrackToAlbumClick() {
        val track = Track(
            name = binding.editTextName.text.toString().trim(),
            duration = binding.editTextDuration.text.toString().trim(),
        )
        val isValidMessage = validateParams(track)
        if (isValidMessage.isEmpty()) {
            val jsonObject = JSONObject().apply {
                put("name", track.name)
                put("duration", track.duration)
            }
            viewModel.addTrackToAlbum(albumId!!, jsonObject)
        } else {
            val toast = Toast.makeText(
                requireContext(),
                isValidMessage,
                Toast.LENGTH_LONG
            ) // in Activity
            toast.show()
        }
    }

    private fun validateParams(track: Track): String {
        //Validaciones
        if (!StringUtil.isNonEmpty(track.name))
            return "El nombre no puede ser vacio"
        if (!StringUtil.isNonEmpty(track.duration))
            return "La duración no puede ser vacia"
        return ""
    }

    fun clearForm(){
        binding.editTextName.setText("")
        binding.editTextDuration.setText("")
    }

}