package com.hana.radargraphsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hana.radargraphsample.ui.theme.RadarGraphSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RadarGraphSampleTheme {
                MainScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    var sweetness by remember { mutableStateOf("") }
    var aftertaste by remember { mutableStateOf("") }
    var alcohol by remember { mutableStateOf("") }
    var tannin by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var acidity by remember { mutableStateOf("") }

    val data = listOf(
        RadarData("당도", 0f),
        RadarData("여운", 0f),
        RadarData("알코올", 0f),
        RadarData("탄닌", 0f),
        RadarData("바디", 0f),
        RadarData("산도", 0f)
    )

    val dataState = remember { mutableStateOf(data) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = sweetness, onValueChange = { sweetness = it }, label = { Text("당도") } )
            OutlinedTextField(value = aftertaste, onValueChange = { aftertaste = it }, label = { Text("여운") }  )
            OutlinedTextField(value = alcohol, onValueChange = { alcohol = it }, label = { Text("알코올") }  )
            OutlinedTextField(value = tannin, onValueChange = { tannin = it }, label = { Text("탄닌") }  )
            OutlinedTextField(value = body, onValueChange = { body = it }, label = { Text("바디") }  )
            OutlinedTextField(value = acidity, onValueChange = { acidity = it }, label = { Text("산도") }  )

            Button(onClick = {
                dataState.value = listOf(
                    RadarData("당도", sweetness.toFloat()),
                    RadarData("여운", aftertaste.toFloat()),
                    RadarData("알코올", alcohol.toFloat()),
                    RadarData("탄닌", tannin.toFloat()),
                    RadarData("바디", body.toFloat()),
                    RadarData("산도", acidity.toFloat())
                )
            }) {
                Text(text = "반영")
            }

            RadarChart(data = dataState.value)
        }
    }
}