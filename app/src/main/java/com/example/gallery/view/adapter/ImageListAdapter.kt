package com.example.gallery.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gallery.R
import com.example.gallery.model.itemClickListener
import java.io.File
import java.util.Arrays

class ImageListAdapter(
    private val imageContext: Context,
    private val listener : itemClickListener
    ) : RecyclerView.Adapter<ImageListAdapter.ImageHolder>() {

    private lateinit var _images : Array<File>

    fun updateImageList(image : Array<File>){
        this._images = image
        notifyDataSetChanged()
    }
    inner class ImageHolder(itemView: View) : ViewHolder(itemView){
        val imagePic : ImageView

        init {
            imagePic = itemView.findViewById(R.id.ImagePic)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell = inflater.inflate(R.layout.picture_image_item, parent, false)
        return ImageHolder(cell)
    }

    override fun getItemCount(): Int {
        return _images.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val image = _images[position]
        holder.imagePic.let {
            Glide.with(imageContext)
                .load(image)
                .apply(RequestOptions().centerCrop())
                .into(it)
        }
        holder.imagePic.setOnClickListener {
            Log.i("SelectedImage", "onBindViewHolder: $image")
            listener.onPicClicked(image)
        }

    }
}