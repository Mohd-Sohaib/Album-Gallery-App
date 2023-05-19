package com.example.gallery.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallery.R
import com.example.gallery.databinding.FragmentAlbumListBinding
import com.example.gallery.model.ImageFolder
import com.example.gallery.model.itemClickListener
import com.example.gallery.view.adapter.AlbumAdapter
import com.example.gallery.viewModel.AlbumViewModel
import java.io.File

class AlbumListFrag : Fragment(), itemClickListener {
    private var _binding: FragmentAlbumListBinding? = null
    private val binding get() = _binding!!

    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var viewModel: AlbumViewModel


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        Log.d("AlbumListFrag", "AlbumListFrag created/re-created!")

        //checking the permission to access media file and read images on the device
        if (activity?.let {
                ContextCompat.checkSelfPermission(
                    it.applicationContext,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED
        )
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )

        viewModel = ViewModelProvider(requireActivity())[AlbumViewModel::class.java]

        albumAdapter = AlbumAdapter(requireContext(), this)
        binding.RecyclerAlbumList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.RecyclerAlbumList.adapter = albumAdapter


        viewModel.imgFolderPath.observe(viewLifecycleOwner, Observer {
            albumAdapter.updateFolders(it)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("AlbumListFrag", "AlbumListFrag destroyed!")
    }

    companion object {
        const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
    }

    override fun onPicClicked(image: File) {}

    override fun onPicClicked(image: ImageFolder) {
        Log.d("AlbumListFrag", "$image")
        viewModel.selectFolder(image)
        findNavController().navigate(R.id.action_albumListFrag_to_imageListFrag)
    }
}

