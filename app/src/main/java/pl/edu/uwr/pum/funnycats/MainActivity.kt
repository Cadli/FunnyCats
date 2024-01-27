package pl.edu.uwr.pum.funnycats

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.edu.uwr.pum.funnycats.ui.theme.FunnyCatsTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunnyCatsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    val viewModel: CatsViewModel = viewModel(
                        LocalViewModelStoreOwner.current!!,
                        "CatsViewModel",
                        CatsViewModelFactory(LocalContext.current.applicationContext as Application)
                    )

                    NavHost(navController = navController, startDestination = "catsList") {
                        composable("catsList") {

                            CatsListScreen(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                        composable("favoritesCats") {
                            FavoritesCatsScreen(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                    }


                }
            }
        }
    }
}

