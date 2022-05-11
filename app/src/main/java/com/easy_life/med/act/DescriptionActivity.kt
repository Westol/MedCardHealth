package com.easy_life.med.act

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.easy_life.med.adapters.ImageAdapter
import com.easy_life.med.databinding.ActivityDescriptionBinding
import com.easy_life.med.model.Ad
import com.easy_life.med.utils.ImageManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DescriptionActivity : AppCompatActivity() {

    lateinit var binding: ActivityDescriptionBinding
    lateinit var adapter: ImageAdapter
    private var ad: Ad? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init(){
        adapter = ImageAdapter()
        binding.apply {
            viewPager.adapter = adapter
        }
        getIntentFromMainAct()
        imageChangeCounter()
    }

    private fun getIntentFromMainAct(){
        ad = intent.getSerializableExtra("AD") as Ad
        if(ad != null) updateUI(ad!!)
    }

    private fun updateUI(ad: Ad){
        ImageManager.fillImageArray(ad, adapter)
        fillTextViews(ad)
    }

    private fun fillTextViews(ad: Ad) = with(binding) {
        tvTitleDescMain.text = ad.title
        tvDesc.text = ad.title
        tvCategory.text = ad.category
        tvClinic.text = ad.clinic
        tvDirection.text = ad.direction
        tvDocument.text = ad.document
        tvDescription.text = ad.description
        tvDate.text = ad.date
        tvWithSent.text = isWithSent(ad.withSent.toBoolean())


    }

    private fun isWithSent(withSent: Boolean): String{
        return if(withSent) "Да" else "Нет"
    }

    private fun imageChangeCounter(){
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val imageCounter = "${position + 1}/${binding.viewPager.adapter?.itemCount}"
                binding.tvImageCounter.text = imageCounter
            }
        })
    }



}