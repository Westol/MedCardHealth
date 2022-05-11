package com.easy_life.med.model

data class AdFilter(
    val time: String? = null,
    val cat_time: String? = null,
    //Filter with category
    val cat_direction_time: String? = null,
    val cat_direction_document_time: String? = null,
    val cat_document_time: String? = null,
    //Filter without category
    val direction_time: String? = null,
    val direction_document_time: String? = null,
    val document_time: String? = null
)
