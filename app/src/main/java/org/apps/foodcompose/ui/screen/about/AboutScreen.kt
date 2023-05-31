package org.apps.foodcompose.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.apps.foodcompose.R
import org.apps.foodcompose.ui.theme.Green

@Composable
fun AboutScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.profil),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Ardhani Ahlan", style = MaterialTheme.typography.h6)
            Text(text = "ardhan.official16@gmail.com", color = Green)
        }
    }
}
