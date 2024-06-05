
import com.example.ebm.domain.search.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TracksRepositoryImpl(private val networkClient: NetworkClient): TracksRepository {
    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }
    override fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(ITunesSearchRequest(expression))
        if(response.resultCode == 200){
            return (response as ITunesResponse).results.map {
                Track(
                    it.trackId,
                    it.trackName,
                    it.artistName,
                    dateFormat.format(it.trackTimeMillis),
                    it.artworkUrl100,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName ?: "",
                    it.country,
                    it.previewUrl
                ) }
        } else{
            return emptyList()
        }
    }
}