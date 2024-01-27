package pl.edu.uwr.pum.funnycats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScaffold(
    title: String,
    route: String,
    navController: NavHostController,
    content: @Composable (Modifier) -> Unit
) {


    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = title,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle(1)
                    )
                },
                navigationIcon = {  },
                scrollBehavior = scrollBehavior,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(5.dp)
            )
        },

        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                actions = {


                    val colorHomeIcon: Color
                    val colorHeartIcon: Color


                    if (route == "catsList") {
                        colorHomeIcon = MaterialTheme.colorScheme.secondary
                        colorHeartIcon = MaterialTheme.colorScheme.primary
                    } else {
                        colorHomeIcon = MaterialTheme.colorScheme.primary
                        colorHeartIcon = MaterialTheme.colorScheme.secondary
                    }


                    IconButton(
                        onClick = { navController.navigate("catsList") },
                        enabled = route != "catsList",
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = "Home Page",
                            modifier = Modifier.size(40.dp),

                            tint = colorHomeIcon
                        )
                    }


                    IconButton(
                        onClick = { navController.navigate("favoritesCats") },
                        enabled = route != "favoritesCats",
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Favorites Cats",
                            modifier = Modifier.size(40.dp),
                            tint = colorHeartIcon
                        )
                    }
                }
            )
        }

    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            content(Modifier.fillMaxWidth())
        }
    }
}