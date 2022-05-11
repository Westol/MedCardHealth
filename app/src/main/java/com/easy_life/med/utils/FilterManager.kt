package com.easy_life.med.utils

import com.easy_life.med.model.Ad
import com.easy_life.med.model.AdFilter

object FilterManager {

    fun createFilter(ad: Ad): AdFilter{
        return AdFilter(
            ad.time,
        "${ad.category}_${ad.time}",
        "${ad.category}_${ad.direction}_${ad.time}",
        "${ad.category}_${ad.direction}_${ad.document}_${ad.time}",
        "${ad.category}_${ad.document}_${ad.time}",
        "${ad.direction}_${ad.time}",
        "${ad.direction}_${ad.document}_${ad.time}",
        "${ad.document}_${ad.time}",
        )
    }

    fun getFilter(filter: String): String{
        val sBuilderNode = StringBuilder()
        val sBuilderFilter = StringBuilder()
        val tempArray = filter.split("_")
        if(tempArray[0] != "empty") {
            sBuilderNode.append("category_")
            sBuilderFilter.append("${tempArray[0]}_")
        }
        if(tempArray[1] != "empty"){
            sBuilderNode.append("direction_")
            sBuilderFilter.append("${tempArray[1]}_")
        }
        if(tempArray[2] != "empty") {
            sBuilderNode.append("document_")
            sBuilderFilter.append("${tempArray[2]}_")
        }
        sBuilderNode.append("time")
        return "$sBuilderNode|$sBuilderFilter"
    }
}