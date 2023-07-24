package com.example.sameera_oblig2.data

import com.example.sameera_oblig2.model.PartiList


sealed class AlpacaUiState {
    data class Success(val parti: PartiList, val stemmer: IntArray) : AlpacaUiState()
}


