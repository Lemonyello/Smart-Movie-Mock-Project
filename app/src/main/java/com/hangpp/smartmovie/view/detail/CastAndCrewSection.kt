package com.hangpp.smartmovie.view.detail

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hangpp.domain.model.*
import com.hangpp.smartmovie.view.common.imgUrl

//@Preview
@Composable
fun Actor(actor: Actor) {
    val posOfCastSection = remember {
        mutableFloatStateOf(0f)
    }
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .height(150.dp)
            .background(color = Color.White /*shape = RoundedCornerShape(8.dp)*/)
            .onGloballyPositioned { coordinates ->
                posOfCastSection.floatValue = coordinates.positionInRoot().y
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .width(120.dp)
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = imgUrl + actor.profilePath,
            placeholder = painterResource(id = R.drawable.ic_menu_report_image),
            contentDescription = null
        )
        Text(
            text = actor.name,
            minLines = 2,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .padding(bottom = 4.dp)
        )
    }
}

//@Preview
@Composable
fun CastAndCrewSection(castList: List<Actor>) {
    LazyHorizontalGrid(
        modifier = Modifier
            .height(420.dp)
            .padding(vertical = 10.dp, horizontal = 2.dp),
        rows = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(castList) {
            Actor(it)
        }
    }
}