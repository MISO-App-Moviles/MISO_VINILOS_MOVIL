package com.example.myapplication.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.databinding.AlbumDetailFragmentBinding
import com.example.myapplication.databinding.AlbumFragmentBinding
import com.example.myapplication.model.models.Album
import com.example.myapplication.model.models.AlbumDetail
import com.example.myapplication.model.models.Track
import com.example.myapplication.view.adapters.AlbumAdapter
import com.example.myapplication.view.adapters.TrackAdapter
import com.example.myapplication.viewModel.AlbumDetailViewModel
import com.example.myapplication.viewModel.AlbumViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AlbumDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumDetailFragment : Fragment() {
    private var albumId: Int? = null
    private var _binding: AlbumDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumDetailViewModel
    private var trackAdapter: TrackAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumId = it.getInt("albumId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.trackRv
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = trackAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, AlbumDetailViewModel.Factory(activity.application, albumId!!)).get(
            AlbumDetailViewModel::class.java)
        getAlbumDetail(activity)
        getAlbumTracks(activity)
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    fun getAlbumDetail(activity: FragmentActivity){
        viewModel.albumDetail.observe(viewLifecycleOwner, Observer<AlbumDetail> {
            setAlbumDetail(it)
        })
    }

    fun getAlbumTracks(activity: FragmentActivity){
        viewModel.tracks.observe(viewLifecycleOwner, Observer<List<Track>> {
            if(it.count() == 0){
                binding.tracksLabel.visibility = View.GONE
            }
            it.apply {
                trackAdapter!!.tracks = this
            }
        })
    }

    fun setAlbumDetail(currentAlbum: AlbumDetail){
        binding.album = currentAlbum
        (activity as? AppCompatActivity)?.supportActionBar?.title = currentAlbum.name
        Glide.with(this)
            .load(currentAlbum.cover)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(binding.albumCover);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = AlbumDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        trackAdapter = TrackAdapter()
        return view
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}