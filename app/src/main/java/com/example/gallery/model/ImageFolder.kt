package com.example.gallery.model

class ImageFolder {
    var path: String? = null
    var folderName: String? = null
    var numberOfPics = 0
    var firstPic: String? = null

    fun addpics() {
        numberOfPics++
    }
}
