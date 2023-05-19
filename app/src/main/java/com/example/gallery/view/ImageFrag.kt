package com.example.gallery.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gallery.R
import com.example.gallery.databinding.FragmentImageBinding
import com.example.gallery.viewModel.AlbumViewModel
import java.io.File


class ImageFrag : Fragment() {
    private var _binding : FragmentImageBinding ? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AlbumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       _binding = FragmentImageBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[AlbumViewModel::class.java]

        viewModel.selectedImage.observe(viewLifecycleOwner) { selectedImage ->
            if (selectedImage != null) {
                val folderFile = File(selectedImage.path)
                val image = folderFile.listFiles()
                val bitmap = BitmapFactory.decodeFile(selectedImage.path)
                binding.ImgView.setImageBitmap(bitmap)
                Log.i("selectedImage", "onViewCreated: $bitmap")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}