package com.example.sameera_oblig2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sameera_oblig2.ui.AlpacaScreen
import com.example.sameera_oblig2.viewmodel.AlpacaViewModel
import com.example.sameera_oblig2.ui.theme.Sameera_oblig2Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Sameera_oblig2Theme {
                AlpacaScreen(AlpacaViewModel())
            }
        }
    }
}



