package com.example.calculator

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.navigationBarsPadding

val portraitButtonList = listOf(
    "C", "(", ")", "/",
    "7", "8", "9", "*",
    "4", "5", "6", "+",
    "1", "2", "3", "-",
    "AC", "0", ".", "="
)

val landscapeButtonList = listOf(
    "AC", "C", "(", ")", "/", "*", "+", "-", ".", "=",
    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
)


@Composable
fun Calculator(modifier: Modifier = Modifier, viewModel: CalculatorViewModel) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val columns = if (isPortrait) 4 else 10
    val buttons = if (isPortrait) portraitButtonList else landscapeButtonList

    val equationText = viewModel.equationText.observeAsState()
    val resultText = viewModel.resultText.observeAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.End
    ) {
        DisplayTexts(equationText.value, resultText.value)

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
        ) {
            CalculatorGrid(viewModel, columns, buttons)
        }
    }
}



@Composable
fun DisplayTexts(equation: String?, result: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = equation ?: "",
            style = TextStyle(
                fontSize = 24.sp,
                textAlign = TextAlign.End,
                color = Color.White
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Text(
            text = result ?: "",
            style = TextStyle(
                fontSize = 48.sp,
                textAlign = TextAlign.End,
                color = Color.White
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
fun CalculatorGrid(viewModel: CalculatorViewModel, columns: Int, buttons: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 8.dp)
    ) {
        items(buttons) {
            CalculatorButton(btn = it) {
                viewModel.onButtonClick(it)
            }
        }
    }
}

@Composable
fun CalculatorButton(btn: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .aspectRatio(1f)
            .fillMaxSize()
    ) {
        FloatingActionButton(
            onClick = onClick,
            shape = CircleShape,
            containerColor = getColor(btn),
            contentColor = getTextColor(btn),
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = btn,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


fun getColor(btn: String): Color {
    return when (btn) {
        "C", "AC", "%", "+/-" -> Color(0xFFB5B5B5)
        "/", "*", "+", "-", "=" -> Color(0xFFFF9500)
        else -> Color(0xFF333333)
    }
}

fun getTextColor(btn: String): Color {
    return when (btn) {
        "C", "AC", "%", "+/-" -> Color.Black
        else -> Color.White
    }
}