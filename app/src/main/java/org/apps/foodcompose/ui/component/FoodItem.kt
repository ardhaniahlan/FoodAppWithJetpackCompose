package org.apps.foodcompose.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import org.apps.foodcompose.R

@Composable
fun FoodListItem(
    name: String,
    photoUrl: String,
    desc: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(
                start = 8.dp,
                top = 6.dp,
                end = 8.dp,
                bottom = 2.dp
            )
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = "Food Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = desc,
                    fontWeight = FontWeight.Light,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .heightIn(max = 50.dp)
                )
            }
            Image(
                painter = painterResource(R.drawable.my_arrow),
                contentDescription = "Icon Arrow",
                modifier = Modifier.size(12.dp)
            )
        }
    }
}