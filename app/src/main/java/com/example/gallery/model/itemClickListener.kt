package com.example.gallery.model

import java.io.File

interface itemClickListener {

    fun onPicClicked(image : File)
    fun onPicClicked(images : ImageFolder)
}