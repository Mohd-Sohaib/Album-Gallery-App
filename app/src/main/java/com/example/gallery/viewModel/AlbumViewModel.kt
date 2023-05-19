package com.example.gallery.viewModel


import android.annotation.SuppressLint
import android.app.Application
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gallery.model.ImageFolder
import com.example.gallery.model.itemClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val _imgFolderPath = MutableLiveData<ArrayList<ImageFolder>>()
    val imgFolderPath: LiveData<ArrayList<ImageFolder>> = _imgFolderPath

    private val _imgList = MutableLiveData<ImageFolder>()
    val selectedFolder: LiveData<ImageFolder> = _imgList

    private val _img = MutableLiveData<File>()
    val selectedImage : LiveData<File> = _img

    init {
        Log.d("AlbumListFrag", "AlbumViewModel created!")
        val path: ArrayList<ImageFolder> = AlbumPath()
    }

    //fetching Image folder from device
    @SuppressLint("SuspiciousIndentation")
    fun AlbumPath(): ArrayList<ImageFolder> {
        val picFolders = ArrayList<ImageFolder>()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Log.d("AlbumListFrag", Thread.currentThread().toString())
                Log.d("AlbumListFrag", Thread.currentThread().toString())
                val picPaths = ArrayList<String>()
                val allImagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                val projection = arrayOf(
                    MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID
                )
                val cursor =
                    getApplication<Application>().contentResolver.query(
                        allImagesUri,
                        projection,
                        null,
                        null,
                        null
                    )

                try {
                    cursor?.moveToFirst()
                    do {
                        val folds = ImageFolder()
//                    val name =
//                        cursor!!.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                        val folder =
                            cursor?.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                        val datapath =
                            cursor?.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                        var folderpaths = datapath?.substring(0, datapath.lastIndexOf("$folder/"))
                        folderpaths = "$folderpaths$folder/"
                        if (!picPaths.contains(folderpaths)) {
                            picPaths.add(folderpaths)
                            folds.path = folderpaths
                            folds.folderName = folder
                            folds.firstPic = datapath
                            //if the folder has only one picture this line helps to set it as first
                            // so as to avoid blank image in item view
                            folds.addpics()
                            picFolders.add(folds)
                        } else {
                            for (i in picFolders.indices) {
                                if (picFolders[i].path == folderpaths) {
                                    picFolders[i].firstPic = datapath
                                    picFolders[i].addpics()
                                }
                            }
                        }
                    } while (cursor!!.moveToNext())
                    cursor.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                _imgFolderPath.postValue(picFolders)

            }
        }
        return picFolders
    }

    fun selectFolder(folder: ImageFolder) {
        _imgList.value = folder
    }

    fun selectImage(image: File){
        _img.value = image
    }
    
}

