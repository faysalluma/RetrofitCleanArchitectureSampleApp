package com.groupec.retrofitcleanarchictecturesampleapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.groupec.retrofitcleanarchictecturesampleapp.R
import com.groupec.retrofitcleanarchictecturesampleapp.ui.theme.RetrofitCleanArchictectureSampleAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.viewmodel.compose.viewModel
import com.groupec.retrofitcleanarchictecturesampleapp.core.model.Order
import com.groupec.retrofitcleanarchictecturesampleapp.remote.utils.ConnectivityManagerUtils
import com.groupec.retrofitcleanarchictecturesampleapp.remote.utils.toDateString

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // private val viewModel: MainViewModel by viewModels()
    private val connectivityManagerUtils by lazy { ConnectivityManagerUtils(this, lifecycleScope) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val connectionState by connectivityManagerUtils.connectionAsStateFlow.collectAsStateWithLifecycle()
            RetrofitCleanArchictectureSampleAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (!connectionState) {
                        ErrorScreen(error = "No internet connexion")
                    } else {
                        AppContent()
                    }
                }
            }
        }
    }
}



@Composable
fun AppContent(viewModel: MainViewModel = viewModel()) {

    ComposableLifecycle(
        // onResume = { viewModel.fetchRandomDog() }
    )

    val orderUiState by viewModel.orderUiState.collectAsStateWithLifecycle()

    Box {
        when (orderUiState) {
            is RandomDogUiState.Loading -> LoadingScreen()
            is RandomDogUiState.Empty -> EmptyScreen()
            is RandomDogUiState.Success -> OrderList((orderUiState as RandomDogUiState.Success).orders)
            is RandomDogUiState.Error -> ErrorScreen((orderUiState as RandomDogUiState.Error).message)
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "No data available")
    }
}

@Composable
fun ErrorScreen(error: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Error: $error")
    }
}

@Composable
fun OrderList(orders: List<Order>) {
    Column {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://find.groupec.net/images/groupec-logo-large.png")
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
            // .border(2.dp, Color.Gray, CircleShape),
            // colorFilter = ColorFilter.tint(Color.Blue)
        )

        LazyColumn {
            items(orders) { order ->
                RouteItem(order)
            }
        }
    }
}

@Composable
fun RouteItem(order: Order) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = order.datecreation.toDateString(), style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = order.customerName)
        }
    }
}

