package com.example.sameera_oblig2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sameera_oblig2.data.AlpacaPartyApi
import com.example.sameera_oblig2.data.AlpacaUiState
import com.example.sameera_oblig2.data.hentXml
import com.example.sameera_oblig2.model.PartiList
import com.example.sameera_oblig2.ui.theme.sorterStemmer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AlpacaViewModel: ViewModel() {
    private var alpacaUiState: MutableStateFlow<AlpacaUiState> = MutableStateFlow(
        AlpacaUiState.Success(
            PartiList(emptyList()), IntArray(5)
        )
    )
    var alpacaUiStateFlow: StateFlow<AlpacaUiState> = alpacaUiState.asStateFlow()
        private set

    private var district=1

    init {
        getAlpacaInfo()
    }


    private fun getAlpacaInfo() {
        viewModelScope.launch {
            val listResult = AlpacaPartyApi.retrofitService.getInfo()
            val hentStemmer =when(district){
                1-> sorterStemmer(AlpacaPartyApi.retrofitService.getDist1())
                2 -> sorterStemmer(AlpacaPartyApi.retrofitService.getDist2())
                else -> hentXml()
            }
            alpacaUiState.value = AlpacaUiState.Success(listResult, hentStemmer)
        }
    }

    fun endredist(dist:Int){
        district=dist
        getAlpacaInfo()
    }


}

