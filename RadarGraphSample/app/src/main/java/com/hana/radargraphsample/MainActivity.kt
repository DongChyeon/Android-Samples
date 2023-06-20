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
    var sweetness1 by remember { mutableStateOf("") }
    var aftertaste1 by remember { mutableStateOf("") }
    var alcohol1 by remember { mutableStateOf("") }
    var tannin1 by remember { mutableStateOf("") }
    var body1 by remember { mutableStateOf("") }
    var acidity1 by remember { mutableStateOf("") }


    var sweetness2 by remember { mutableStateOf("") }
    var aftertaste2 by remember { mutableStateOf("") }
    var alcohol2 by remember { mutableStateOf("") }
    var tannin2 by remember { mutableStateOf("") }
    var body2 by remember { mutableStateOf("") }
    var acidity2 by remember { mutableStateOf("") }

    val data = listOf(
        ObjectData(
            listOf(
                RadarData("당도", 0f),
                RadarData("여운", 0f),
                RadarData("알코올", 0f),
                RadarData("탄닌", 0f),
                RadarData("바디", 0f),
                RadarData("산도", 0f)
            )
        ),
        ObjectData(
            listOf(
                RadarData("당도", 0f),
                RadarData("여운", 0f),
                RadarData("알코올", 0f),
                RadarData("탄닌", 0f),
                RadarData("바디", 0f),
                RadarData("산도", 0f)
            )
        )
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
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(modifier = Modifier.width(150.dp), value = sweetness1, onValueChange = { sweetness1 = it }, label = { Text("당도1") } )
                OutlinedTextField(modifier = Modifier.width(150.dp), value = sweetness2, onValueChange = { sweetness2 = it }, label = { Text("당도2") } )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(modifier = Modifier.width(150.dp), value = aftertaste1, onValueChange = { aftertaste1 = it }, label = { Text("여운1") }  )
                OutlinedTextField(modifier = Modifier.width(150.dp), value = aftertaste2, onValueChange = { aftertaste2 = it }, label = { Text("여운2") }  )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(modifier = Modifier.width(150.dp), value = alcohol1, onValueChange = { alcohol1 = it }, label = { Text("알코올1") }  )
                OutlinedTextField(modifier = Modifier.width(150.dp), value = alcohol2, onValueChange = { alcohol2 = it }, label = { Text("알코올2") }  )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(modifier = Modifier.width(150.dp), value = tannin1, onValueChange = { tannin1 = it }, label = { Text("탄닌1") }  )
                OutlinedTextField(modifier = Modifier.width(150.dp), value = tannin2, onValueChange = { tannin2 = it }, label = { Text("탄닌2") }  )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(modifier = Modifier.width(150.dp), value = body1, onValueChange = { body1 = it }, label = { Text("바디1") }  )
                OutlinedTextField(modifier = Modifier.width(150.dp), value = body2, onValueChange = { body2 = it }, label = { Text("바디2") }  )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(modifier = Modifier.width(150.dp), value = acidity1, onValueChange = { acidity1 = it }, label = { Text("산도1") }  )
                OutlinedTextField(modifier = Modifier.width(150.dp), value = acidity2, onValueChange = { acidity2 = it }, label = { Text("산도2") }  )
            }

            Button(onClick = {
                dataState.value = listOf(
                    ObjectData(
                        listOf(
                            RadarData("당도", sweetness1.toFloat()),
                            RadarData("여운", aftertaste1.toFloat()),
                            RadarData("알코올", alcohol1.toFloat()),
                            RadarData("탄닌", tannin1.toFloat()),
                            RadarData("바디", body1.toFloat()),
                            RadarData("산도", acidity1.toFloat())
                        )
                    ),
                    ObjectData(
                        listOf(
                            RadarData("당도", sweetness2.toFloat()),
                            RadarData("여운", aftertaste2.toFloat()),
                            RadarData("알코올", alcohol2.toFloat()),
                            RadarData("탄닌", tannin2.toFloat()),
                            RadarData("바디", body2.toFloat()),
                            RadarData("산도", acidity2.toFloat())
                        )
                    ),
                )
            }) {
                Text(text = "반영")
            }

            RadarChart(data = dataState.value)
        }
    }
}