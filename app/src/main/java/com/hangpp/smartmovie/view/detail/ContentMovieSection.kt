package com.hangpp.smartmovie.view.detail

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.hangpp.domain.model.Movie
import com.hangpp.smartmovie.view.custom.RatingStar
import com.hangpp.smartmovie.view.common.imgUrl

//@Preview
@Composable
fun ContentMovie(movie: Movie, expandState: ExpandableViewState, currentSectionIsCast: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
            .padding(start = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp),
                model = imgUrl + movie.posterPath,
                placeholder = painterResource(id = R.drawable.ic_menu_report_image),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = movie.title,
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                )
                Text(text = "Action | Drama | Adventure")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingStar(rating = movie.voteAverage / 2, maxRating = 5, onStarClick = {})
                    Text(text = "${String.format("%.2f", movie.voteAverage)}/10")
                }
                Text(text = "Language: English")
                Text(text = "June 22, 2007 (USA) 2h 10m")
            }
        }

        TextOverview(
            txt = movie.overview,
            expandState
        )
        Text(
            text = if(currentSectionIsCast) "Cast" else "Similar Movies",
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Box(
            modifier = Modifier
                .height(4.dp)
                .width(if(currentSectionIsCast) 110.dp else 180.dp)
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}

