package com.example.sameera_oblig2.ui.theme

import com.example.sameera_oblig2.model.Stemmer

fun sorterStemmer(getStemmerList: List<Stemmer>): IntArray{
    val sortertArray = IntArray(5)
    for (stemmer in getStemmerList) {
        when (stemmer.id.toInt()) {
            1 -> sortertArray[0] = sortertArray[0] +1
            2 -> sortertArray[1] = sortertArray[1] +1
            3 -> sortertArray[2] = sortertArray[2] +1
            else -> sortertArray[3] = sortertArray[3] +1
        }
    }
    sortertArray[4] = getStemmerList.size
    return sortertArray
}