package com.easy_life.med.act

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.easy_life.med.MainActivity
import com.easy_life.med.R
import com.easy_life.med.adapters.ImageAdapter
import com.easy_life.med.model.Ad
import com.easy_life.med.model.DbManager
import com.easy_life.med.databinding.ActivityEditAdsBinding
import com.easy_life.med.dialogs.DialogSpinnerHelper
import com.easy_life.med.frag.FragmentCloseInterface
import com.easy_life.med.frag.ImageListFrag
import com.easy_life.med.utils.CityHelper
import com.easy_life.med.utils.ImageManager
import com.easy_life.med.utils.ImagePicker
import com.google.android.gms.tasks.OnCompleteListener
import java.io.ByteArrayOutputStream


class EditAdsAct : AppCompatActivity(), FragmentCloseInterface {

    var chooseImageFrag : ImageListFrag? = null
    lateinit var binding: ActivityEditAdsBinding
    private val dialog = DialogSpinnerHelper()
    private var isImagesPermissionGranted = false
    lateinit var imageAdapter : ImageAdapter
    private val dbManager = DbManager()
    var editImagePos = 0
    private var imageIndex = 0
    private var isEditState = false
    private var ad: Ad? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        checkEditState()
        imageChangeCounter()

    }

    private fun checkEditState(){
        isEditState = isEditState()
        if(isEditState){
            ad = intent.getSerializableExtra(MainActivity.ADS_DATA) as Ad
            if(ad != null)fillViews(ad!!)
        }

    }

    private fun isEditState(): Boolean {
        return intent.getBooleanExtra(MainActivity.EDIT_STATE, false)

    }


    private fun fillViews(ad:Ad) = with(binding) {

        edTitle.setText(ad.title)
        tvCategory.text = ad.category
        edClinic.setText(ad.clinic)
        tvDirection.text = ad.direction
        tvDocument.text = ad.document
        edDescription.setText(ad.description)
        edData.setText(ad.date)
        checkBoxWithSend.isChecked = ad.withSent.toBoolean()
        updateImageCounter(0)
        ImageManager.fillImageArray(ad, imageAdapter)

    }

    private fun init(){

        imageAdapter = ImageAdapter()
        binding.vpImages.adapter = imageAdapter

    }

    //OnClicks
    fun onClickSelectCountry(view: View){
        val listCountry = CityHelper.getAllCountries(this)
        dialog.showSpinnerDialog(this, listCountry, binding.tvCountry)
        if(binding.tvCity.text.toString() != getString(R.string.select_city)){
            binding.tvCity.text = getString(R.string.select_city)
        }
    }

    fun onClickSelectCity(view: View){
        val selectedCountry = binding.tvCountry.text.toString()
        if(selectedCountry != getString(R.string.select_country)) {
            val listCity = CityHelper.getAllCities(selectedCountry, this)
            dialog.showSpinnerDialog(this, listCity, binding.tvCity)
        } else {
            Toast.makeText(this, "Не выбрана страна", Toast.LENGTH_LONG).show()
        }

    }

    fun onClickSelectCategory(view: View){

        val listCategory = resources.getStringArray(R.array.category).toMutableList() as ArrayList
        dialog.showSpinnerDialog(this, listCategory, binding.tvCategory)

    }

    fun onClickSelectDirection(view: View){

            val listDirection = resources.getStringArray(R.array.direction).toMutableList() as ArrayList
            dialog.showSpinnerDialog(this, listDirection, binding.tvDirection)

    }

    fun onClickSelectDocument(view: View){

        val listDocument = resources.getStringArray(R.array.documents).toMutableList() as ArrayList
        dialog.showSpinnerDialog(this, listDocument, binding.tvDocument)

    }


    fun onClickGetImages(view: View) {

        if(imageAdapter.mainArray.size == 0) {

            ImagePicker.getMultiImages(this, 100)

        } else {

            openChooseImageFrag(null)
            chooseImageFrag?.updateAdapterFromEdit(imageAdapter.mainArray)
        }
    }

    fun onClickPublish(view: View){
       ad = fillAd()
       uploadImages()
    }

    private fun onPublishFinish(): DbManager.FinishWorkListener{
        return  object: DbManager.FinishWorkListener{
            override fun onFinish() {
                finish()
            }
        }
    }

    private fun fillAd(): Ad{
        val adTemp: Ad
        binding.apply {
            adTemp = Ad(edTitle.text.toString(),
                tvCategory.text.toString(),
                edClinic.text.toString(),
                tvDirection.text.toString(),
                tvDocument.text.toString(),
                edDescription.text.toString(),
                edData.text.toString(),
                checkBoxWithSend.isChecked.toString(),
                ad?.mainImage ?: "empty",
                ad?.image2 ?: "empty",
                ad?.image3 ?: "empty",
                ad?.image4 ?:"empty",
                ad?.image5 ?:"empty",
                ad?.image6 ?:"empty",
                ad?.image7 ?:"empty",
                ad?.image8 ?:"empty",
                ad?.image9 ?:"empty",
                ad?.image10 ?:"empty",
                ad?.image11 ?:"empty",
                ad?.image12 ?:"empty",
                ad?.image13 ?:"empty",
                ad?.image14 ?:"empty",
                ad?.image15 ?:"empty",
                ad?.image16 ?:"empty",
                ad?.image17 ?:"empty",
                ad?.image18 ?:"empty",
                ad?.image19 ?:"empty",
                ad?.image20 ?:"empty",
                ad?.image21 ?:"empty",
                ad?.image22 ?:"empty",
                ad?.image23 ?:"empty",
                ad?.image24 ?:"empty",
                ad?.image25 ?:"empty",
                ad?.image26 ?:"empty",
                ad?.image27 ?:"empty",
                ad?.image28 ?:"empty",
                ad?.image29 ?:"empty",
                ad?.image30 ?:"empty",
                ad?.image31 ?:"empty",
                ad?.image32 ?:"empty",
                ad?.image33 ?:"empty",
                ad?.image34 ?:"empty",
                ad?.image35 ?:"empty",
                ad?.key ?: dbManager.db.push().key,"0",
                dbManager.auth.uid,
                ad?.time ?: System.currentTimeMillis().toString())
        }
        return adTemp
    }

    override fun onFragClose(list: ArrayList<Bitmap>) {
        binding.scrollViewMain.visibility = View.VISIBLE
        imageAdapter.update(list)
        chooseImageFrag = null
        updateImageCounter(binding.vpImages.currentItem)
    }

    fun openChooseImageFrag(newList : ArrayList<Uri>?) {

        chooseImageFrag = ImageListFrag(this)
        if(newList != null)chooseImageFrag?.resizeSelectedImages(newList, true, this)
        binding.scrollViewMain.visibility = View.GONE
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.place_holder, chooseImageFrag!!)
        fm.commit()
    }

    private fun uploadImages() {
        if (imageIndex == 35) {
            dbManager.publishAd(ad!!, onPublishFinish())
            return
        }
        val oldUrl = getUrlFromAd()
        if (imageAdapter.mainArray.size > imageIndex) {

            val byteArray = prepareImageByteArray(imageAdapter.mainArray[imageIndex])
            if(oldUrl.startsWith("http")){
                     updateImage(byteArray, oldUrl){
                           nextImage(it.result.toString())
                     }
            } else {
                uploadImage(byteArray) {
                    //dbManager.publishAd(ad!!, onPublishFinish())
                    nextImage(it.result.toString())
                }
            }



        } else {
            if (oldUrl.startsWith("http")) {
                deleteImageByUrl(oldUrl) {
                    nextImage("empty")
                }
            } else {
                nextImage("empty")
            }
        }
    }

    private fun nextImage(uri: String) {
        setImageUriToAd(uri)
        imageIndex++
        uploadImages()
    }


    private fun setImageUriToAd(uri: String){
        when(imageIndex){
            0 -> ad = ad?.copy(mainImage = uri)
            1 -> ad = ad?.copy(image2 = uri)
            2 -> ad = ad?.copy(image3 = uri)
            3 -> ad = ad?.copy(image4 = uri)
            4 -> ad = ad?.copy(image5 = uri)
            5 -> ad = ad?.copy(image6 = uri)
            6 -> ad = ad?.copy(image7 = uri)
            7 -> ad = ad?.copy(image8 = uri)
            8 -> ad = ad?.copy(image9 = uri)
            9 -> ad = ad?.copy(image10 = uri)
            10 -> ad = ad?.copy(image11 = uri)
            11 -> ad = ad?.copy(image12 = uri)
            12 -> ad = ad?.copy(image13 = uri)
            13 -> ad = ad?.copy(image14 = uri)
            14 -> ad = ad?.copy(image15 = uri)
            15 -> ad = ad?.copy(image16 = uri)
            16 -> ad = ad?.copy(image17 = uri)
            17 -> ad = ad?.copy(image18 = uri)
            18 -> ad = ad?.copy(image19 = uri)
            19 -> ad = ad?.copy(image20 = uri)
            20 -> ad = ad?.copy(image21 = uri)
            21 -> ad = ad?.copy(image22 = uri)
            22 -> ad = ad?.copy(image23 = uri)
            23 -> ad = ad?.copy(image24 = uri)
            24 -> ad = ad?.copy(image25 = uri)
            25 -> ad = ad?.copy(image26 = uri)
            26 -> ad = ad?.copy(image27 = uri)
            27 -> ad = ad?.copy(image28 = uri)
            28 -> ad = ad?.copy(image29 = uri)
            29 -> ad = ad?.copy(image30 = uri)
            30 -> ad = ad?.copy(image31 = uri)
            31 -> ad = ad?.copy(image32 = uri)
            32 -> ad = ad?.copy(image33 = uri)
            33 -> ad = ad?.copy(image34 = uri)
            34 -> ad = ad?.copy(image35 = uri)
        }
    }

    private fun getUrlFromAd(): String {
        return listOf(ad?.mainImage!!,
            ad?.image2!!,
            ad?.image3!!,
            ad?.image4!!,
            ad?.image5!!,
            ad?.image6!!,
            ad?.image7!!,
            ad?.image8!!,
            ad?.image9!!,
            ad?.image10!!,
            ad?.image11!!,
            ad?.image12!!,
            ad?.image13!!,
            ad?.image14!!,
            ad?.image15!!,
            ad?.image16!!,
            ad?.image17!!,
            ad?.image18!!,
            ad?.image19!!,
            ad?.image20!!,
            ad?.image21!!,
            ad?.image22!!,
            ad?.image23!!,
            ad?.image24!!,
            ad?.image25!!,
            ad?.image26!!,
            ad?.image27!!,
            ad?.image28!!,
            ad?.image29!!,
            ad?.image30!!,
            ad?.image31!!,
            ad?.image32!!,
            ad?.image33!!,
            ad?.image34!!,
            ad?.image35!!)[imageIndex]
    }

    private fun prepareImageByteArray(bitMap: Bitmap): ByteArray{
        val outStream = ByteArrayOutputStream()
        bitMap.compress(Bitmap.CompressFormat.JPEG, 20, outStream)
        return outStream.toByteArray()
    }

    private fun uploadImage(byteArray: ByteArray, listener: OnCompleteListener<Uri>){
        val imStorageRef = dbManager.dbStorage
            .child(dbManager.auth.uid!!)
            .child("image_${System.currentTimeMillis()}")
        val upTask = imStorageRef.putBytes(byteArray)
        upTask.continueWithTask{
            task -> imStorageRef.downloadUrl
        }.addOnCompleteListener(listener)
    }

    private fun deleteImageByUrl(oldUrl: String, listener: OnCompleteListener<Void>){
        dbManager.dbStorage.storage
            .getReferenceFromUrl(oldUrl)
            .delete().addOnCompleteListener(listener)
    }

    private fun updateImage(byteArray: ByteArray, url: String, listener: OnCompleteListener<Uri>){
        val imStorageRef = dbManager.dbStorage.storage.getReferenceFromUrl(url)
        val upTask = imStorageRef.putBytes(byteArray)
        upTask.continueWithTask{
                task -> imStorageRef.downloadUrl
        }.addOnCompleteListener(listener)
    }

    private fun imageChangeCounter(){
        binding.vpImages.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateImageCounter(position)
            }
        })
    }

    private fun updateImageCounter(counter: Int){
        var index = 1
        val itemCount = binding.vpImages.adapter?.itemCount
        if(itemCount == 0) index = 0
        val imageCounter = "${counter + index}/$itemCount"
        binding.tvImageCounter.text = imageCounter
    }

}