package org.apps.foodcompose.ui.screen.home

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.apps.foodcompose.model.ChooseFood
import org.apps.foodcompose.ViewModelFactory
import org.apps.foodcompose.di.Injection
import kotlinx.coroutines.launch
import org.apps.foodcompose.ui.common.UiState
import org.apps.foodcompose.ui.component.FoodListItem
import org.apps.foodcompose.ui.component.ScrollToTopButton
import org.apps.foodcompose.ui.component.SearchBar
import org.apps.foodcompose.ui.theme.Green

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllFood()
            }
            is UiState.Success -> {
                HomeContent(
                    chooseFood = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    chooseFood: List<ChooseFood>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    val groupedFood by viewModel.groupedFood.collectAsState()
    val query by viewModel.query

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val showButton: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    Box(modifier = Modifier) {
        LazyColumn(
            modifier = modifier,
            state = listState,
        ) {

            item {
                SearchBar(
                    query = query,
                    onQueryChange = viewModel::search,
                    modifier = Modifier.background(Green)
                )
            }

            groupedFood.forEach { (initial, foodList) ->
                stickyHeader {
                    CharacterHeader(initial)
                }
                items(foodList) { food ->
                    chooseFood.find { it.pilihFood.id == food.id }?.let { chosenFood ->
                        FoodListItem(
                            name = chosenFood.pilihFood.nama,
                            photoUrl = chosenFood.pilihFood.photoUrl,
                            desc = chosenFood.pilihFood.desc,
                            modifier = Modifier,
                            onClick = { navigateToDetail(chosenFood.pilihFood.id) }
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(end = 16.dp, bottom = 16.dp)
                .align(Alignment.BottomEnd)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
    }
}


@Composable
fun CharacterHeader(
    char: Char,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Text(
            text = char.toString(),
            fontWeight = FontWeight.Black,
            fontSize = 12.sp,
            color = Green,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

