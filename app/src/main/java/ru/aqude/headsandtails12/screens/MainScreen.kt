package ru.aqude.headsandtails12.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainPreview() {
    val money = listOf("Решка", "Орел")
    var moneyState by remember {
        mutableStateOf("")
    }
    Main(money, moneyState) { newMoneyState -> moneyState = newMoneyState }
}

@Composable
fun Main(money: List<String>, moneyState: String, onMoneyStateChanged: (String) -> Unit) {
    var result: String by remember {
        mutableStateOf("")
    }


    LaunchedEffect(moneyState) {
        result = moneyState
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        MainComponent(result)
        BottomComponent(money, onMoneyStateChanged)
    }
}

@Composable
fun MainComponent(result: String) {
    Box(modifier = Modifier.padding(top = 100.dp)) {
        Text(
            text = result,
            fontWeight = FontWeight.Bold,
            fontSize = 50.sp
        )
    }
}

@Composable
fun BottomComponent(money: List<String>, onMoneyStateChanged: (String) -> Unit) {
    var time by remember {
        mutableStateOf(3)
    }
    var isButtonActive by remember {
        mutableStateOf(true)
    }
    val randomMoney = money.random()
    Box(modifier = Modifier.padding(bottom = 40.dp)) {
        ExtendedFloatingActionButton(
            modifier = Modifier
                .width(300.dp)
                .height(100.dp),
            text = {
                Text(
                    text = "Бросить монетку",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
            onClick = {
                if (isButtonActive) {
                    isButtonActive = false
                    CoroutineScope(Dispatchers.Main).launch {
                        while (time != 0) {
                            onMoneyStateChanged(time.toString())
                            delay(1000)
                            time--
                        }
                        onMoneyStateChanged(randomMoney)
                        delay(3000)
                        onMoneyStateChanged("")
                        time = 3
                        isButtonActive = true
                    }
                }
            })
    }
}
