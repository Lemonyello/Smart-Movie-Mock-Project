package com.hangpp.smartmovie.view.detail

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hangpp.domain.model.Movie
import com.hangpp.smartmovie.view.custom.RatingStar
import com.hangpp.smartmovie.view.common.imgUrl

//@Preview
@Composable
fun SimilarMovie(movie: Movie) {
    Column(
        modifier = Modifier.clip(RoundedCornerShape(6.dp))
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 10.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            model = imgUrl + movie.posterPath,
            placeholder = painterResource(id = R.drawable.ic_menu_report_image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.wrapContentHeight().width(220.dp)) {
                Text(text = movie.title, fontSize = 18.sp, color = Color.DarkGray)
                Text(text = "Action | Drama | Comedy", color = Color.LightGray)
            }
            RatingStar(rating = movie.voteAverage / 2, maxRating = 5, onStarClick = {})
        }
    }
}

//@Preview
@Composable
fun SimilarMoviesSection(movie: Movie) {
    Column(modifier = Modifier.fillMaxWidth()) {
        (0..1).forEach { item ->
            SimilarMovie(movie)
        }
    }
}