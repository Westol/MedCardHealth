package com.easy_life.med.act

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.easy_life.med.R
import com.easy_life.med.databinding.ActivityFilterBinding
import com.easy_life.med.dialogs.DialogSpinnerHelper

class FilterActivity : AppCompatActivity() {

    lateinit var binding: ActivityFilterBinding
    private val dialog = DialogSpinnerHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClickSelectCategory()
        onClickSelectDirection()
        onClickSelectDocument()
        onClickDone()
        onClickClear()
        actionBarSettings()
        getFilter()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun getFilter() = with(binding){
        val filter = intent.getStringExtra(FILTER_KEY)
        if(filter != null && filter != "empty"){
            val filterArray = filter.split("_")
            if(filterArray[0] != "empty") tvCategory.text = filterArray[0]
            if(filterArray[1] != "empty") tvDirection.text = filterArray[1]
            if(filterArray[2] != "empty") tvDocument.text = filterArray[2]

        }
    }

    private fun onClickSelectCategory() = with(binding){
        tvCategory.setOnClickListener {
            val listCategory = resources.getStringArray(R.array.category).toMutableList() as ArrayList
            dialog.showSpinnerDialog(this@FilterActivity, listCategory, tvCategory)
        }
    }

    private fun onClickSelectDirection() = with(binding){
        tvDirection.setOnClickListener {
            val listDirection = resources.getStringArray(R.array.direction).toMutableList() as ArrayList
            dialog.showSpinnerDialog(this@FilterActivity, listDirection, tvDirection)
        }

    }

    private fun onClickSelectDocument() = with(binding){
        tvDocument.setOnClickListener {
            val listDocument = resources.getStringArray(R.array.documents).toMutableList() as ArrayList
            dialog.showSpinnerDialog(this@FilterActivity, listDocument, tvDocument)
        }
    }

    private fun onClickDone() = with(binding){
        btDone.setOnClickListener {
            val i = Intent().apply {
                putExtra(FILTER_KEY, createFilter())
            }
            setResult(RESULT_OK, i)
            finish()
        }
    }

    private fun onClickClear() = with(binding){
        btClear.setOnClickListener {
           tvCategory.text = getString(R.string.category_hint)
           tvDirection.text = getString(R.string.direction_hint)
           tvDocument.text = getString(R.string.document_hint)
            setResult(RESULT_CANCELED)
        }
    }

    private fun createFilter(): String = with(binding){

        val sBuilder = StringBuilder()
        val arrayTempFilter = listOf(
            tvCategory.text,
            tvDirection.text,
            tvDocument.text
        )
        for ((i, s) in arrayTempFilter. withIndex()){
            if(s != getString(R.string.category_hint) && s != getString(R.string.direction_hint) && s != getString(R.string.document_hint) && s.isNotEmpty()){
                sBuilder.append(s)
                if(i != arrayTempFilter.size - 1)sBuilder.append("_")
            } else {
                sBuilder.append("empty")
                if(i != arrayTempFilter.size - 1)sBuilder.append("_")
            }
        }
        return sBuilder.toString()
    }

    fun actionBarSettings(){
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        const val FILTER_KEY = "filter_key"
    }
}