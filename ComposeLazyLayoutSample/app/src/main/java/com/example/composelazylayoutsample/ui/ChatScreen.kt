package com.example.composelazylayoutsample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.composelazylayoutsample.ui.theme.HexagonShape
import com.example.composelazylayoutsample.utils.toDp

@Composable
fun ChatScreen() {

    var itemHeight by remember { mutableStateOf(IntSize.Zero) }

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(
            -((itemHeight.height.toFloat().toDp()) / 2) + 30.dp
        ),
    ) {
        items(50) {
            Hexagons(
                item = it,
                updateItemSize = { size ->
                    itemHeight = size
                }
            )
        }
    }
}

@Composable
fun Hexagons(
    item: Int,
    updateItemSize: (IntSize) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        if (item % 2 == 0) Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.55f)
                .aspectRatio(1f)
                .onGloballyPositioned { coordinates ->
                    updateItemSize(coordinates.size)
                }
                .clip(HexagonShape())
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Hexagon $item", color = Color.White)
        }
    }
}