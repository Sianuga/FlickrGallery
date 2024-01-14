import com.example.flickrgallery.FlickrResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {
    @GET("services/feeds/photos_public.gne")
    fun fetchImages(
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("page") page: Int
    ): Call<FlickrResponse>
}