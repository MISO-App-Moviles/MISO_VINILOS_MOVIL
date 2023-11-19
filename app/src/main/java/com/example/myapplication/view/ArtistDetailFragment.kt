package com.example.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.databinding.ArtistDetailFragmentBinding
import com.example.myapplication.model.models.ArtistDetail
import com.example.myapplication.model.models.PreviewAlbum
import com.example.myapplication.view.adapters.AlbumPreviewAdapter
import com.example.myapplication.viewModel.ArtistDetailViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [CollectorDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArtistDetailFragment : Fragment() {
    private var artistId: Int? = null

    private var _binding: ArtistDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ArtistDetailViewModel
    private var albumPreviewAdapter: AlbumPreviewAdapter? = null

    private lateinit var albumsRecyclerView: RecyclerView

    private var hasItems = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            artistId = it.getInt("artistId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        albumsRecyclerView = binding.albumPreviewRv
        albumsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        albumsRecyclerView.adapter = albumPreviewAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, ArtistDetailViewModel.Factory(activity.application, artistId!!)).get(
            ArtistDetailViewModel::class.java)
        getArtistDetail()
        getAlbums()
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    fun getArtistDetail(){
        viewModel.artist.observe(viewLifecycleOwner, Observer<ArtistDetail> {
            setArtistDetail(it)
        })
    }

    private fun setArtistDetail(currentArtist: ArtistDetail) {
        binding.artist = currentArtist
        (activity as? AppCompatActivity)?.supportActionBar?.title = currentArtist.name
        Glide.with(this)
            .load(currentArtist.image)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(binding.artistDetailAvatar);
    }

    fun getAlbums(){
        viewModel.albums.observe(viewLifecycleOwner, Observer<List<PreviewAlbum>> {
            if(it.isEmpty()){
                binding.albumsLabel.visibility = View.GONE
            }
            it.apply {
                albumPreviewAdapter!!.albums = this
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = ArtistDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        albumPreviewAdapter = AlbumPreviewAdapter()
        return view
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

}