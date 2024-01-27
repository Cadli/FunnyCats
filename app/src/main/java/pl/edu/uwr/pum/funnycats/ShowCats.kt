package pl.edu.uwr.pum.funnycats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ShowCats(
    cats: List<Cat>,
    viewModel: CatsViewModel,
    autoScroll: Boolean
) {

    val listState = rememberLazyListState()

    if(autoScroll){
        DisposableEffect(listState) {
            onDispose {
                viewModel.viewModelScope.launch {
                    viewModel.saveScrollPosition(listState.firstVisibleItemIndex)
                }
            }
        }

        LaunchedEffect(viewModel) {
            val savedScrollPosition = viewModel.loadScrollPosition()
            listState.scrollToItem(savedScrollPosition)
        }
    }



    LazyColumn (state = listState, horizontalAlignment = Alignment.CenterHorizontally){
        items(cats) { cat ->

            var isButtonActivated by remember(cat) { mutableStateOf(viewModel.checkIfExistInLocalDb(cat)) }


            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {

                Box (
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                ){

                    val url = "https://cataas.com/cat/" + cat.id
                    val painter = rememberImagePainter(data = url)


                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                    )


                    if (painter.state is ImagePainter.State.Loading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(300.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        )
                    }


                    FloatingActionButton(
                        onClick = {
                            if (isButtonActivated) {
                                viewModel.delete(cat)
                            } else {
                                viewModel.insert(cat)
                            }
                            isButtonActivated = !isButtonActivated
                        },
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = Color.Red,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .alpha(0.6f),

                    ) {

                        if (isButtonActivated) {
                            Icon(Icons.Default.Favorite,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        } else {
                            Icon(
                                Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }

            if (cats.indexOf(cat) == cats.lastIndex) {
                viewModel.loadMoreCats()
            }
        }


//        item {
//            ExtendedFloatingActionButton(
//                onClick = {
//                          viewModel.loadMoreCats()
//                },
//                icon = { Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(30.dp))},
//                text = { Text(text = "MORE", fontSize = 28.sp) },
//            )
//            Spacer(modifier = Modifier.height(20.dp))
//        }



    }
}