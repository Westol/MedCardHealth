package com.easy_life.med.model

import java.io.Serializable

data class Ad(
    val title: String? = null,
    val category: String? = null,
    val clinic: String? = null,
    val direction: String? = null,
    val document: String? = null,
    val description: String? = null,
    val date: String? = null,
    val withSent: String? = null,
    val mainImage: String? = null,
    val image2: String? = null,
    val image3: String? = null,
    val image4: String? = null,
    val image5: String? = null,
    val image6: String? = null,
    val image7: String? = null,
    val image8: String? = null,
    val image9: String? = null,
    val image10: String? = null,
    val image11: String? = null,
    val image12: String? = null,
    val image13: String? = null,
    val image14: String? = null,
    val image15: String? = null,
    val image16: String? = null,
    val image17: String? = null,
    val image18: String? = null,
    val image19: String? = null,
    val image20: String? = null,
    val image21: String? = null,
    val image22: String? = null,
    val image23: String? = null,
    val image24: String? = null,
    val image25: String? = null,
    val image26: String? = null,
    val image27: String? = null,
    val image28: String? = null,
    val image29: String? = null,
    val image30: String? = null,
    val image31: String? = null,
    val image32: String? = null,
    val image33: String? = null,
    val image34: String? = null,
    val image35: String? = null,
    val key: String? = null,
    var favCounter: String? = "0",
    val uid: String? = null,
    val time: String = "0",

    var isFav: Boolean = false,

    var viewsCounter: String? = "0",
    var emailCounter: String? = "0",
    var callsCounter: String? = "0"
): Serializable
