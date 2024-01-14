import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flickrgallery.FlickrResponse
import com.example.flickrgallery.ListItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val _items = MutableLiveData<List<ListItem>>()
    val items: LiveData<List<ListItem>> = _items
    private var currentPage = 1

    private val pageSize = 20

    private var isLoading = false

    private var hasReachedEnd = false


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.flickr.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(FlickrApiService::class.java)

    init {
        fetchImages()
    }

    fun fetchImages() {
        service.fetchImages(page = ++currentPage).enqueue(object : retrofit2.Callback<FlickrResponse> {
            override fun onResponse(call: Call<FlickrResponse>, response: retrofit2.Response<FlickrResponse>) {
                if (response.isSuccessful) {
                    val flickrItems = response.body()?.items ?: emptyList()
                    val listItems = flickrItems.map { flickrItem ->
                        ListItem(
                            height = Random.nextInt(100, 300).dp,

                            color = Color.Gray,

                            imageUrl = flickrItem.media.m
                        )
                    }
                    _items.postValue((_items.value.orEmpty() + listItems))
                } else {


                }
            }

            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {


            }
        })
    }
}
