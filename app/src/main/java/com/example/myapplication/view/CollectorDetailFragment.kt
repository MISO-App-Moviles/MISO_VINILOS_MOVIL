package com.example.myapplication.view

import android.os.Bundle
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
import com.example.myapplication.R
import com.example.myapplication.databinding.CollectorDetailFragmentBinding
import com.example.myapplication.model.models.CollectorDetail
import com.example.myapplication.model.models.PreviewAlbum
import com.example.myapplication.view.adapters.AlbumPreviewAdapter
import com.example.myapplication.view.adapters.ArtistPreviewAdapter
import com.example.myapplication.viewModel.CollectorDetailViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [CollectorDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CollectorDetailFragment : Fragment() {
    private var collectorId: Int? = null
    private var _binding: CollectorDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CollectorDetailViewModel
    private var albumPreviewAdapter: AlbumPreviewAdapter? = null
    private lateinit var albumsRecyclerView: RecyclerView
    private var artistPreviewAdapter: ArtistPreviewAdapter? = null
    private lateinit var artistsRecyclerView: RecyclerView
    private var hasItems = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectorId = it.getInt("collectorId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        albumsRecyclerView = binding.albumPreviewRv
        albumsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        albumsRecyclerView.adapter = albumPreviewAdapter

        artistsRecyclerView = binding.artistPreviewRv
        artistsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        artistsRecyclerView.adapter = artistPreviewAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, CollectorDetailViewModel.Factory(activity.application, collectorId!!)).get(
            CollectorDetailViewModel::class.java)
        getCollectorDetail(activity)
        getAlbums(activity)
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    fun getCollectorDetail(activity: FragmentActivity){
        viewModel.collectorDetail.observe(viewLifecycleOwner, Observer<CollectorDetail> {
            setCollectorDetail(it)
            checkIfViewHasItems()
        })
    }

    fun getAlbums(activity: FragmentActivity){
        viewModel.tracks.observe(viewLifecycleOwner, Observer<List<PreviewAlbum>> {
            if(it.count() == 0){
                binding.albumsLabel.visibility = View.GONE
            } else {
                hasItems = true
            }
            it.apply {
                albumPreviewAdapter!!.albums = this
            }
            checkIfViewHasItems()
        })
    }

    fun setCollectorDetail(currentCollector: CollectorDetail){
        binding.collector = currentCollector
        artistPreviewAdapter!!.artists = currentCollector.favoritePerformers
        if(currentCollector!!.favoritePerformers.count() == 0){
            binding.artistsLabel.visibility = View.GONE
        } else {
            hasItems = true
        }
        (activity as? AppCompatActivity)?.supportActionBar?.title = currentCollector.name
        Glide.with(this)
            .load(R.drawable.im_record_player)
            .into(binding.collectorDetailAvatar);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = CollectorDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        albumPreviewAdapter = AlbumPreviewAdapter()
        artistPreviewAdapter= ArtistPreviewAdapter()
        return view
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    private fun checkIfViewHasItems(){
        if(!hasItems){
            binding.noDataLabel.visibility = View.VISIBLE
        }else{
            binding.noDataLabel.visibility = View.GONE
        }
    }
}