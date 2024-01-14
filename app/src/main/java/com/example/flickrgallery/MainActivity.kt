package com.example.flickrgallery

import ViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.flickrgallery.ui.theme.FlickrGalleryTheme
import kotlin.random.Random
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import androidx.compose.runtime.livedata.observeAsState


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[ViewModel::class.java]

        setContent {
            FlickrGalleryTheme {
                val items = viewModel.items.observeAsState(emptyList())
                GalleryScreen(viewModel = viewModel)
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun GalleryScreen(viewModel: ViewModel) {
        val items by viewModel.items.observeAsState(emptyList())
        val staggeredGridState = rememberLazyStaggeredGridState()

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            state = staggeredGridState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp
        ) {
            itemsIndexed(items) { index, item ->
                FlickrImage(item)
                if (index == items.size - 1) {
                    viewModel.fetchImages()
                }
            }
        }


        LaunchedEffect(items.size, staggeredGridState.layoutInfo.visibleItemsInfo.size) {
            if (items.isNotEmpty() && staggeredGridState.layoutInfo.visibleItemsInfo.size < items.size) {
                viewModel.fetchImages()
            }
        }
    }
}