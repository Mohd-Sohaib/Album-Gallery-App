package com.example.gallery.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallery.R
import com.example.gallery.databinding.FragmentImageListBinding
import com.example.gallery.model.ImageFolder
import com.example.gallery.model.itemClickListener
import com.example.gallery.view.adapter.ImageListAdapter
import com.example.gallery.viewModel.AlbumViewModel
import java.io.File


class ImageListFrag : Fragment(),itemClickListener {

    private var _binding : FragmentImageListBinding ? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AlbumViewModel
    private lateinit var imageAdapter : ImageListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentImageListBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[AlbumViewModel::class.java]

        imageAdapter = ImageListAdapter(requireContext(),this)
        binding.RecyclerImageList.layoutManager = GridLayoutManager(requireContext(),2)
        binding.RecyclerImageList.adapter = imageAdapter

        viewModel.selectedFolder.observe(viewLifecycleOwner){selectedFolder->
            if (selectedFolder!=null){
                val folderFile= File(selectedFolder.path!!)
                val images=folderFile.listFiles()
                if (images != null) {
                    imageAdapter.updateImageList(images)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPicClicked(image: File) {
        viewModel.selectImage(image)
        findNavController().navigate(R.id.action_imageListFrag_to_imageFrag)
    }

    override fun onPicClicked(images: ImageFolder) {}
}

