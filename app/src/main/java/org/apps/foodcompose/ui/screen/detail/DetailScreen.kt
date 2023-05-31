package org.apps.foodcompose.ui.screen.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.apps.foodcompose.ViewModelFactory
import org.apps.foodcompose.di.Injection
import org.apps.foodcompose.ui.theme.FoodComposeTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import org.apps.foodcompose.ui.common.UiState
import org.apps.foodcompose.ui.theme.Green


@Composable
fun DetailScreen(
    foodId: Long,
    viewModel: DetailViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateBack: () -> Unit,
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getFoodById(foodId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    name = data.pilihFood.nama,
                    photoUrl = data.pilihFood.photoUrl,
                    desc = data.pilihFood.desc,
                    masak = data.pilihFood.masak,
                    onBackClick = navigateBack,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    name: String,
    photoUrl: String,
    desc: String,
    masak: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box (
                modifier = modifier
                    .height(300.dp)
                    .fillMaxWidth()
            ){
                AsyncImage(
                    model = photoUrl,
                    contentDescription = "Food Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(Green)
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Icon Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = desc,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(Color.LightGray))
                Text(
                    text = masak,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    FoodComposeTheme{
        DetailContent(
            "Soto Betawi",
            "Jaket Hoodie Dicoding",
            "Soto Betawi merupakan soto yang khas dari daerah DKI Jakarta. Seperti halnya Soto Madura dan Soto Sulung, soto Betawi juga menggunakan jeroan. Selain jeroan, sering kali organ-organ lain juga disertakan, seperti mata, terpedo, dan juga hati. Daging sapi juga menjadi bahan campuran dalam soto Betawi.",
            "- Rebus air dan daging hingga daging matang dan empuk.\\n- Potong dadu daging 3x3 cm.\\n- Saring kaldu rebusan daging, didihkan kembali dengan daging, paru, dan babat.\\n- Panaskan minyak, tumis bumbu halus bersama pala, cengkih, kayu manis, serai, lengkuas, daun jeruk, dan daun salam hingga harum.\\n- Tuang tumisan bumbu ke air rebusan daging, aduk rata.\\n- Masak dengan api sedang hingga mendidih.\\n- Tuangkan santan, Bango Kecap Manis, garam, merica bubuk, dan gula pasir.\\n- Masak hingga matang. Susun tomat dalam mangkuk, tuangkan soto, sajikan dengan bahan pelengkap lainnya.",
            onBackClick = {},
        )
    }
}
