package com.example.sameera_oblig2.ui


import android.graphics.fonts.FontStyle.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.sameera_oblig2.data.AlpacaUiState
import com.example.sameera_oblig2.model.Partier
import com.example.sameera_oblig2.viewmodel.AlpacaViewModel
import kotlin.math.roundToInt


@Composable
fun AlpacaScreen(Modell: AlpacaViewModel = viewModel()){
    val modellState by Modell.alpacaUiStateFlow.collectAsState()
    val sorterteStemmer= modellState.getStemmer()
    MessageList(modellState,sorterteStemmer,Modell)
}

@Composable
fun Kortlager(info: Partier, Stemmer: IntArray){
    Card(modifier = Modifier.semantics(mergeDescendants = true){}
        .padding(all = 5.dp)
        .fillMaxWidth()
        .fillMaxSize(),border=BorderStroke(1.dp,Color.Black)
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Spacer(
            modifier = Modifier
                .height(35.dp)
                .padding(5.dp)
                .fillMaxWidth()
                .background(
                    color = Color(info.color.toColorInt()), RectangleShape
                )
        )
        Text(
            text = info.name,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
        AsyncImage(
            model = info.img,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(200.dp)
                .clip(CircleShape)
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Leader: " + info.leader,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Votes: ${Stemmer[info.id.toInt()-1]} - ${
                prosentRegner(Stemmer[info.id.toInt()-1].toFloat(),Stemmer[4].toFloat())}%",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
    }
}





@Composable
fun MessageList(alpacaUiState: AlpacaUiState, Stemmer: IntArray, Modell: AlpacaViewModel) {
    LazyColumn(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            MenyForDistrikter(Modell)
            Spacer(modifier = Modifier.height(5.dp))
        }
        items(
            items = alpacaUiState.getParties(),
            itemContent = { item ->
                Kortlager(item,Stemmer)
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenyForDistrikter(Modell: AlpacaViewModel) {
    val options = listOf("District 1", "District 2","District 3")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("District 1") }
    Column(
        modifier=Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = { selectedOptionText = it },
                label = { Text("Choose District") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                            when (selectedOptionText){
                                "District 1" -> Modell.endredist(1)
                                "District 2" -> Modell.endredist(2)
                                else -> Modell.endredist(3)
                            }
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )

                }

            }
        }
    }
}

fun AlpacaUiState.getParties(): List<Partier> {
    return when (this) {
        is AlpacaUiState.Success -> parti.parties
    }
}

fun AlpacaUiState.getStemmer(): IntArray {
    return when (this) {
        is AlpacaUiState.Success -> stemmer
    }
}


fun prosentRegner(stemmer:Float, AntallStemmer:Float): Float {
    var result=stemmer/AntallStemmer*100
    result = ((result * 100.0).roundToInt() / 100.00).toFloat()
    return result
}






