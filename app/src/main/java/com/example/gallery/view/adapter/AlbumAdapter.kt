package com.example.gallery.view.adapter

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gallery.R
import com.example.gallery.model.ImageFolder
import com.example.gallery.model.itemClickListener

class AlbumAdapter( private val folderContext: Context,
                    private val listener : itemClickListener
    ) : RecyclerView.Adapter<AlbumAdapter.FolderHolder>() {

    private var folders: ArrayList<ImageFolder> = ArrayList()

    fun updateFolders(it: ArrayList<ImageFolder>) {
        this.folders.clear()
        this.folders = it
        notifyDataSetChanged()
    }

    inner class FolderHolder(itemView: View) : ViewHolder(itemView) {
        var folderPic: ImageView
        var folderName: TextView
        var folderSize: TextView
        var folderCard: CardView

        init {
            folderPic = itemView.findViewById(R.id.folderPic)
            folderName = itemView.findViewById(R.id.folderName)
            folderSize = itemView.findViewById(R.id.folderSize)
            folderCard = itemView.findViewById(R.id.folderCard)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell = inflater.inflate(R.layout.picture_folder_item, parent, false)
        return FolderHolder(cell)
    }

    override fun getItemCount(): Int {
        return folders.size
    }

    override fun onBindViewHolder(holder: FolderHolder, position: Int) {
        val folder = folders[position]
        Glide.with(folderContext)
            .load(folder.firstPic)
            .apply(RequestOptions().centerCrop())
            .into(holder.folderPic)

        //setting the number of images
        val text = "" + folder.folderName
        val folderSizeString = "(" + folder.numberOfPics + ")"
        holder.folderSize.text = folderSizeString
        holder.folderName.text = text
        holder.folderPic.setOnClickListener {
            Log.i("CurrentlySelectFolder", "onBindViewHolder: ${folder.path}")
            listener.onPicClicked(folder)
        }
    }
}


