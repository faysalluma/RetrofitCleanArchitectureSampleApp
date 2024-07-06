package com.groupec.retrofitcleanarchictecturesampleapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.groupec.retrofitcleanarchictecturesampleapp.R
import com.groupec.retrofitcleanarchictecturesampleapp.remote.model.ApiResponse
import com.groupec.retrofitcleanarchictecturesampleapp.ui.theme.RetrofitCleanArchictectureSampleAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.viewmodel.compose.viewModel
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitCleanArchictectureSampleAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent()
                }
            }
        }
    }
}

@Composable
fun AppContent(viewModel: MainViewModel = viewModel()) {
    val randomUiState by viewModel.randomUiState.collectAsState()

    Box {
        when (randomUiState) {
            is RandomDogUiState.Loading -> LoadingScreen()
            is RandomDogUiState.Empty -> EmptyScreen()
            is RandomDogUiState.Success -> DogList((randomUiState as RandomDogUiState.Success).apiResponse)
            is RandomDogUiState.Error -> ErrorScreen((randomUiState as RandomDogUiState.Error).message)
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
fun DogList(dogs: ApiResponse) {
    Column {
        Text(text = dogs.success)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(dogs.message)
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
    }
}

