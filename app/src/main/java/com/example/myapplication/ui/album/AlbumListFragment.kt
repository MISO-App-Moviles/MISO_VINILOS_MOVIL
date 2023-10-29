package com.example.myapplication.ui.album

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.databinding.FragmentAlbumListBinding

class AlbumListFragment : Fragment() {

    private var _binding: FragmentAlbumListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val albumListViewModel =
            ViewModelProvider(this).get(AlbumListViewModel::class.java)

        _binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAlbumList
        albumListViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

}