package com.easy_life.med.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.exifinterface.media.ExifInterface
import com.easy_life.med.adapters.ImageAdapter
import com.easy_life.med.model.Ad
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.io.File
import java.io.InputStream

object ImageManager {

    private const val MAX_IMAGE_SIZE = 1000
    private const val WIDTH = 0
    private const val HEIGHT = 1


    fun getImageSize(uri : Uri, act: Activity) : List<Int>{
        val inStream = act.contentResolver.openInputStream(uri)
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeStream(inStream, null, options)
        return listOf(options.outWidth, options.outHeight)

    }




    fun chooseScaleType(im: ImageView, bitmap: Bitmap){

        if(bitmap.width > bitmap.height){
            im.scaleType = ImageView.ScaleType.CENTER_CROP
        } else {
            im.scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
    }

    suspend fun imageResize(uris: ArrayList<Uri>, act: Activity): List<Bitmap> = withContext(Dispatchers.IO){

        val tempList = ArrayList<List<Int>>()
        val bitmapList = ArrayList<Bitmap>()
        for(n in uris.indices){

            val size = getImageSize(uris[n], act)
            val imageRatio = size[WIDTH].toFloat() / size[HEIGHT].toFloat()

            if(imageRatio > 1) {

                if(size[WIDTH] > MAX_IMAGE_SIZE){

                    tempList.add(listOf(MAX_IMAGE_SIZE, (MAX_IMAGE_SIZE / imageRatio).toInt()))

                } else {

                    tempList.add(listOf(size[WIDTH], size[HEIGHT]))
                }


            } else {

                if(size[HEIGHT] > MAX_IMAGE_SIZE){

                    tempList.add(listOf((MAX_IMAGE_SIZE * imageRatio).toInt(), MAX_IMAGE_SIZE))

                } else {

                    tempList.add(listOf(size[WIDTH], size[HEIGHT]))
                }

            }

        }

        for(i in uris.indices) {

            kotlin.runCatching {

                bitmapList.add(Picasso.get().load(uris[i]).resize(tempList[i][WIDTH], tempList[i][HEIGHT]).get())
            }

        }

         return@withContext bitmapList

    }

    private suspend fun getBitmapFromUris(uris: List<String?>): List<Bitmap> = withContext(Dispatchers.IO){

        val bitmapList = ArrayList<Bitmap>()

        for(i in uris.indices) {
            kotlin.runCatching {
                bitmapList.add(Picasso.get().load(uris[i]).get())
            }
        }
        return@withContext bitmapList

    }

    fun fillImageArray(ad: Ad, adapter: ImageAdapter){
        val listUris = listOf(ad.mainImage,ad.image2, ad.image3, ad.image4, ad.image5, ad.image6, ad.image7, ad.image8, ad.image9, ad.image10, ad.image11, ad.image12, ad.image13, ad.image14, ad.image15, ad.image16, ad.image17, ad.image18, ad.image18, ad.image19, ad.image20, ad.image22, ad.image23, ad.image24, ad.image25, ad.image26, ad.image27, ad.image28, ad.image29, ad.image30, ad.image31, ad.image32, ad.image33, ad.image34, ad.image35)
        CoroutineScope(Dispatchers.Main).launch {
            val bitMapList = getBitmapFromUris(listUris)
            adapter.update(bitMapList as ArrayList<Bitmap>)
        }
    }

}