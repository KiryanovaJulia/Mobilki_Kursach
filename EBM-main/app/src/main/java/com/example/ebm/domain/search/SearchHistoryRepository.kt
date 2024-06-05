import com.example.ebm.domain.search.models.Track

interface SearchHistoryRepository {
    fun write(input: Track)
    fun clear()
    fun read(): List<Track>
}