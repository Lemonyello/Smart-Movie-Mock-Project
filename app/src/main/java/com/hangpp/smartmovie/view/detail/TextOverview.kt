package com.hangpp.smartmovie.view.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*

//@Preview
@Composable
fun TextOverview(txt: String, expandState: ExpandableViewState) {

    Column(modifier = Modifier.padding(end = 10.dp)) {
        Text(
            text = txt,
            maxLines = if (expandState.isExpanded) 10 else 3,
            lineHeight = 20.sp
        )
        Text(
            text = "view all",
            modifier = Modifier.clickable { if (!expandState.isExpanded) expandState.toggle() },
            color = MaterialTheme.colorScheme.primary
        )
    }
}

