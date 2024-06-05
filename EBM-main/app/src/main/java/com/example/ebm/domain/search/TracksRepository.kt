import com.example.ebm.domain.search.models.Track

interface TracksRepository {
    fun searchTracks(expression: String): List<Track>
}